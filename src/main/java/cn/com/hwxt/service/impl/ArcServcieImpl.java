package cn.com.hwxt.service.impl;

import ch.qos.logback.classic.Logger;
import cn.com.hwxt.dao.JdbcDao;
import cn.com.hwxt.dao.i.SGroupMapper;
import cn.com.hwxt.dao.i.SUserMapper;
import cn.com.hwxt.dao.i.SUserroleMapper;
import cn.com.hwxt.pojo.DClassifyZjk;
import cn.com.hwxt.pojo.SUser;
import cn.com.hwxt.service.BaseService;
import cn.com.hwxt.service.i.ArcService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.WebService;
import java.util.List;

@Service("arcServcieImpl")
@WebService(name = "ArcDataWs", targetNamespace = "http://service.hwxt.com.cn/")
public class ArcServcieImpl extends BaseService implements ArcService {
    @Transactional("txManager")
    public String fileRecive(String xml) {
        String resultStr = "";
        if (StringUtils.isNotEmpty(xml)) {
            log.error("====================================================");//临时记录
            log.error(xml);//临时记录
            resultStr = this.add2Archive(xml);
            log.error("====================================================");//临时记录
            //throw new RuntimeException("test TX");
        } else {
            resultStr = "OA发送xml为空.";
            log.error(resultStr);
        }
        return resultStr;
    }

    /**
     * @param xml 将xml解析并且添加到档案
     */
    private String add2Archive(String xml) {
        String resultStr = "1";
        return resultStr;
    }

    @Transactional("txManager")
    public List<SUser> listAllUser() {
        return sUserMapper.getAllUserList();
    }

    public String getBmidStrByDepCode(String depCode) {
        return super.getBmidByDepCode(depCode);
    }

    public String getBmidStrByUserCode(String userCode) {
        return super.getBmidByuserCode(userCode);
    }

    public String getLamsIP() {
        return super.getLamsIP();
    }
    public String syncDclassfy(Integer libcode){
        String msg = "";
        Integer num = 0;
        List<DClassifyZjk> flhList = sGroupMapper.listFlh(fhlZjb , libcode);

        if(null != flhList && flhList.size()>0){
            jdbcDao.excute("DELETE FROM D_CLASSIFY"+libcode);
            for (DClassifyZjk classy : flhList) {
                sUserMapper.insertClassify(classy , "D_CLASSIFY"+libcode);
                log.error("插入一条分类表 数据成功.syncDclassfy: "
                        + classy.getDid()+" : " + classy.getFlcode()+" : " + classy.getFlmc());
                num++;
            }
            msg = "同步成功:同步"+num+"条数据";
        }else{
            msg = "同步事变:中间库中该类型的分类为空";
        }
        return msg;
    }

    @Autowired
    private SGroupMapper sGroupMapper;
    @Autowired
    private SUserMapper sUserMapper;
    @Autowired
    private SUserroleMapper sUserroleMapper;
    @Autowired
    private JdbcDao jdbcDao;

    /**
     * 分类号中间表的表名
     */
    @Autowired
    @Value("${dclassfy.tablename}")
    protected String fhlZjb;
    private Logger log = (Logger) LoggerFactory.getLogger(this.getClass());
}
