package cn.com.hwxt.service.impl;

import ch.qos.logback.classic.Logger;
import cn.com.hwxt.dao.JdbcDao;
import cn.com.hwxt.dao.i.SUserMapper;
import cn.com.hwxt.pojo.*;
import cn.com.hwxt.pojo.jaxb.Field;
import cn.com.hwxt.service.BaseService;
import cn.com.hwxt.service.i.ScheduleService;
import cn.com.hwxt.util.DateUtil;
import cn.com.hwxt.util.MD5;
import cn.com.hwxt.util.ParseBimXmlUtil;
import cn.com.hwxt.util.ZipTool;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ser.StdSerializers;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.*;

/**
 * @author DaMo
 */
@Service("scheduleService")
public class ScheduleServiceImpl extends BaseService implements ScheduleService {

    /**
     * 开始解析
     */
    @Transactional
    public String parseEepQueue() {
        String msg = "处理了一次.";
        if(working){
            msg = "正在处理,稍后再试";
            return msg;
        }
        working = Boolean.TRUE;
        try {
            List<SBizEepQueue>  queueList = sUserMapper.getSBizEepQueues("STATUS IS NULL OR STATUS=0");
            for (SBizEepQueue queue : queueList) {
                String rslt = "OK";
                EcidiSimpleEEP ecidiEEP = null;

                        //解析包
                SFwqpz fwqpz = sUserMapper.getFwqpzByPzm(queue.getPzm());
                String relativePath = FilenameUtils.normalize("/BIMEXT/" + queue.getPackagePath().substring(0, queue.getPackagePath().length()-4)
                        +"/"+ queue.getUuid()+"/");//解压相对路径
                String targetPath = fwqpz.getSavedbname() + relativePath;//解压j绝对路径

                File zipFfile = new File(fwqpz.getSavedbname()+queue.getPackagePath());
                if(!zipFfile.exists()){
                    return "zip文件没有找到";
                }
                String metaFileXmlPath = FilenameUtils.normalize(targetPath+"meta_file.xml");
                String metaFileMd5Path = FilenameUtils.normalize(targetPath+"meta_file.md5");
                System.out.println(targetPath);
                try {
                    ZipTool.unZipFiles(zipFfile,  targetPath , "UTF-8");
                    //验证meta_file.xml的MD5
                    File metaFileXml = new File(metaFileXmlPath);
                    File metaFileMd5 = new File(metaFileMd5Path);
                    String metaFileMd5Str = FileUtils.readFileToString(metaFileMd5);
                    if(!MD5.getFileMD5(metaFileXml).equals(metaFileMd5Str)){
                        throw new RuntimeException("meta_file.xml的md5验证失败");
                    }
                    //解析meta_file.xml
                    ecidiEEP = ParseBimXmlUtil.parseBimEEP(metaFileXml);
                    for (EcidiSimpleFile ecidiSimpleFile : ecidiEEP.getFileList()) {
                        File att = new File(targetPath+ecidiSimpleFile.getFileBizName());
                        String md5 = MD5.getFileMD5(att);
                        if(!md5.equals(ecidiSimpleFile.getMd5())){
                            throw new RuntimeException("数字摘要值验证错误:"+ecidiSimpleFile.getFileBizName());
                        }
                    }

                    if(ecidiEEP == null){
                        throw new RuntimeException("meta_file解析错误,获取eep对象为空");
                    }
                    //dual 业务实体 dual 文件实体
                    Integer dFileDid = -1;

                    String bmid = queue.getQzh();
                    String SQL = "";
                    String eTablename = "E_FILE"+queue.getLibcode();


                    String dTableName = queue.getLevelStr()+queue.getLibcode();
                    String gdrCode = MapUtils.getString(ecidiEEP.getBizEntity() , "creator_username");
                    StringBuffer fields = new StringBuffer();
                    StringBuffer values = new StringBuffer();
                    Map<String, Object> bizEntity = ecidiEEP.getBizEntity();
                    Map<String ,String> fieldMapping = jdbcDao.quert2Colum4Map("SELECT * FROM "+ptableName+" WHERE  F5 IS NOT NULL AND F2='"+queue.getAppName()+"'" , "F5" ,"F6");
                    List<FDTable>  fieldList = sUserMapper.getFtableList("F_"+dTableName);
                    Set<String> daFieldSet = fieldMapping.keySet();

                    try {
                        for (String fieldStr : daFieldSet) {
                            String bimFiled = fieldMapping.get(fieldStr);
                            FDTable field = getFDTable(fieldList , fieldStr);
                            if(field != null){
                                String theValue = (bizEntity.get(bimFiled) == null ? "" : bizEntity.get(bimFiled).toString());
                                theValue = theValue.contains("'") ? theValue.replace("'", "''") : theValue;
                                fields.append(field.getFieldname()).append(",");
                                switch (field.getFieldtype()) {
                                    case 11:
                                        if (theValue.equals("")) {
                                            values.append(getSysdate() + ",");
                                        } else {
                                            values.append(generateTimeToSQLDate(theValue)).append(",");
                                        }
                                        break;
                                    case 1:
                                        values.append("'").append(theValue).append("',");
                                        break;
                                    case 3:
                                        if (StringUtils.isBlank(theValue)) {
                                            values.append("null ,");
                                        } else {
                                            values.append(Integer.parseInt(theValue)).append(",");
                                        }
                                        break;
                                    default:
                                        values.append("'").append(theValue).append("',");
                                        break;
                                }
                            }
                        }
                        //先不用 获取部门id
                        //            if(StringUtils.isNotBlank(gdrCode)){
                        //                bmid = getBmidByuserCode(gdrCode);
                        //            }else{
                        //                bmid = queue.getQzh();
                        //            }
                        dFileDid = getMaxDid(dTableName);
                        fields.append("attached,status, attr,attrex,qzh,bmid,creator,pid,did ");
                        values.append(ecidiEEP.getFileList().size() > 0 ? 1 : 0 ).append(",").append(status).append(",");
                        values.append(attr).append(",").append(attrex).append(",'").append(queue.getQzh()).append("','");
                        values.append(bmid).append("','").append(gdrCode).append("',-1,").append(dFileDid);
                        SQL = "insert into " + dTableName + " (" + fields.toString() + ") values ( " + values.toString() + " )";
                        execSql(SQL);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        dFileDid = -1;
                        throw new RuntimeException(e);
                    }

                    if(dFileDid>0){
                        List<EcidiSimpleFile> fileList = ecidiEEP.getFileList();
                        for (EcidiSimpleFile sFile : fileList) {
                            File realFile = new File(targetPath+ sFile.getFileBizName());
                            if(realFile.exists() &&  realFile.isFile()){
                                try {
                                    EFile eFile = new EFile();
                                    //DID,PID,EFILENAME,TITLE,EXT,PZM,PATHNAME,STATUS,ATTR,ATTREX,CREATOR,CREATETIME,FILESIZE,MD5,CONVERTSTATUS
                                    eFile.setDid(getMaxDid(eTablename));
                                    eFile.setPid(dFileDid);
                                    eFile.setEfilename(sFile.getFileBizName());
                                    String theTile = "";
                                    if(StringUtils.isNotBlank(sFile.getTitle())){
                                        theTile = sFile.getTitle();
                                    }else{
                                        theTile = FilenameUtils.getBaseName(sFile.getFileBizName());
                                    }
                                    if(null != sFile.getAttachemnt() && sFile.getAttachemnt()){
                                        eFile.setTitle("【附件】"+theTile);
                                        eFile.setBbh("附件");
                                    }else{
                                        eFile.setTitle(theTile);
                                        eFile.setBbh(sFile.getRelateionDescription());
                                    }
                                    eFile.setExt(sFile.getExt().replace(".",""));
                                    eFile.setPzm(fwqpz.getPzname());
                                    eFile.setPathname(relativePath);
                                    eFile.setStatus(status);
                                    eFile.setAttr(attr);
                                    eFile.setAttrex(attrex);
                                    eFile.setEsort(sFile.getOrderNum());
                                    eFile.setBz("BIM");
                                    eFile.setCreator("BIM");
                                    eFile.setXlh(sFile.getRelationType());

                                    if(null == sFile.getCreateTime()){
                                        eFile.setCreatetime(queue.getUpdateTime());
                                    }else{
                                        eFile.setCreatetime(sFile.getCreateTime());
                                    }
                                    if(StringUtils.isNotBlank(sFile.getSize())){
                                        eFile.setFilesize(Integer.parseInt(sFile.getSize()));
                                    }else{
                                        eFile.setFilesize(new Long(realFile.length()).intValue() / 1000);
                                    }
                                    eFile.setMd5(sFile.getMd5());
                                    insertEfile(eTablename, eFile);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    throw new RuntimeException("插入文件错误:"+e.getMessage() , e);
                                }
                            }
                        }
                    }else {
                        throw new RuntimeException("档案系统插入文件错误" + queue);
                    }
                } catch (Exception e) {
                    log.error(e.getMessage() ,e);
                    rslt = e.getMessage();
                }
                //针对结果去处理队列,回调bim系统服务
                try {
                    if(rslt.equalsIgnoreCase("OK")){//0 null未处理, 1已经处理,2失败了
                        queue.setStatus(1);
                        queue.setMemo("");
                    }else{
                        queue.setStatus(2);
                        queue.setMemo(rslt);
                    }
                    queue.setUpdateTime(new Date());
                    sUserMapper.updateSBizEepQueueById(queue);
                    //调用bim系统服务
                    callBimServcie(queue);
                } catch (Exception e) {
                    log.error(e.getMessage() ,e );
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage() , e);
            e.printStackTrace();
        }finally {
            working = Boolean.FALSE;
        }
        return msg;
    }


    /**
     * 调用BIM系统服务
     * @param queue
     */
    //TODO: call
    public void callBimServcie(SBizEepQueue queue ){
        if(queue.getStatus().equals(0)){
            //成功
        }else {
            String rsult = queue.getMemo();
            //失败
        }
    }

    private FDTable getFDTable(List<FDTable> list , String fieldEnName){
        FDTable obj = null;
        for (FDTable fdTable : list) {
            if(fdTable.getFieldname().equals(fieldEnName)){
                obj = fdTable;
                break;
            }
        }
        return obj;
    }

    @Autowired
    private SUserMapper sUserMapper;
    @Autowired
    protected JdbcDao jdbcDao;

    @Autowired
    @Value("${FILED.MAPPING.P_TABLE_NAME}")
    private String ptableName;
    private static Boolean working = Boolean.FALSE;

    @Autowired
    @Value("${lams.dfile.attrex}")
    protected Integer attrex;// 移交接收状态
    @Autowired
    @Value("${lams.dfile.attr}")
    protected Integer attr;// 归档前后

    @Autowired
    @Value("${lams.dfile.status}")
    protected Integer status;//状态


    private Logger log = (Logger) LoggerFactory.getLogger(this.getClass());
}
