package cn.com.hwxt.service.impl;

import ch.qos.logback.classic.Logger;
import cn.com.hwxt.dao.JdbcDao;
import cn.com.hwxt.dao.i.SGroupMapper;
import cn.com.hwxt.dao.i.SUserMapper;
import cn.com.hwxt.dao.i.SUserroleMapper;
import cn.com.hwxt.pojo.*;
import cn.com.hwxt.service.BaseService;
import cn.com.hwxt.service.i.SyncDepAndUserService;
import cn.com.hwxt.util.CommonUtil;
import cn.com.hwxt.util.DateUtil;
import cn.com.hwxt.util.XmlObjUtil;
import com.sun.xml.xsom.impl.EmptyImpl;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import weaver.hrm.HrmService;
import weaver.hrm.HrmServicePortType;
import weaver.hrm.jaxb.DepartmentBeanArray;
import weaver.hrm.jaxb.SubCompanyBeanArray;
import weaver.hrm.jaxb.UserBeanArray;
import weaver.hrm.webservice.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * @author DaMo
 * @UPDATE 2019/8/20-23:52
 * @since 2019/8/20-23:52
 */
@Service("syncDepAndUserService")
public class SyncDepAndUserServiceImpl extends BaseService implements SyncDepAndUserService {
    @Override
    public void sync() {
        syncAllDept();
        syncAllUser();
        execSql("DELETE FROM S_USERROLE WHERE YHID NOT IN (SELECT DID FROM S_USER)");
        CommonUtil.syncActivitUser(lamsIP);
    }

    /**
     * 同步全量部门信息,循环全宗列表一个个单位同步
     * cancel是1的是删除的部门 我们不同步。但是一旦同步过来的部门不用接口删除。可以在档案系统手动删除。
     */
    public synchronized void syncAllDept(){
        List<SQzh> qzhList = sQzhMapper.listAllSqzh();
        for (SQzh sQzh : qzhList) {
            if (StringUtils.isBlank(sQzh.getBz())) {
                continue;
            }else {
                List<DepartmentBeanArray.DepartmentBean>  deptList = oaDeptListByOrgID(sQzh.getBz());
                if (deptList != null && deptList.size() > 0) {
                    for (DepartmentBeanArray.DepartmentBean dept : deptList) {
                        if(!dept.getCanceled().equals("1")){
                            dualOneDept(dept , sQzh.getQzh());
                        }
                    }
                }
            }
        }
    }
    /**
     * 同步全量部门信息 循环全宗列表一个个单位同步 只同步 status=1的 遇到不为1的 设置成禁用  accounttype只管主的
     * status  0：试用  1：正式  2：临时  3：试用延期  4：解聘  5：离职  6：退休  7：无效
     * accounttype Null:主账号,1:次账号
     */
    public synchronized void syncAllUser(){
        List<SQzh> qzhList = sQzhMapper.listAllSqzh();
        for (SQzh sQzh : qzhList) {
            if (StringUtils.isBlank(sQzh.getBz())) {
                continue;
            }else {
                List<SGroup> groupList = sGroupMapper.listGroupByQzh(sQzh.getQzh());
                for (SGroup sGroup : groupList) {
                    List<UserBeanArray.UserBean> userList = oaUserListByOrgIDAndDeptID(sQzh.getBz() , sGroup.getDepid());
                    if (userList != null && userList.size() > 0) {
                        for (UserBeanArray.UserBean ub : userList) {
                            //accounttype Null:主账号,1:次账号  只统统不主的
                            if(ub.getAccounttype().intValue() == 1 || StringUtils.isBlank(ub.getLoginid())){
                                continue;
                            }else{
                                dualOnOrgUser(ub);
                            }
                        }
                    }
                }

            }
        }
    }
    /**
     * 得到所有子公司
     * @return
     */
    public List<SubCompanyBeanArray.SubCompanyBean> oaOrgList(){
        String orgXml = getHrmServicePortType().getHrmSubcompanyInfoXML(ip);
        SubCompanyBeanArray subCompanyBeanArray = XmlObjUtil.xml2Obj(orgXml, SubCompanyBeanArray.class);
        return subCompanyBeanArray.getSubCompanyBean();
    }

    /**
     * 得到所子公司下所有部门
     * @return
     */
    public List<DepartmentBeanArray.DepartmentBean> oaDeptListByOrgID(String orgID){
        String deptXml = getHrmServicePortType().getHrmDepartmentInfoXML(ip , orgID);
        DepartmentBeanArray departmentBeanArray =  XmlObjUtil.xml2Obj(deptXml, DepartmentBeanArray.class);
        return departmentBeanArray.getDepartmentBean();
    }

    /**
     * 得到子公司某部门下所有用户 如果depID为空就会得到该公司下所有用户
     * @return
     */
    public  List<UserBeanArray.UserBean> oaUserListByOrgIDAndDeptID(String orgID , String deptID){
        String userXML = getHrmServicePortType().getHrmUserInfoXML(ip , null, orgID , deptID, null, null);
        UserBeanArray ua = XmlObjUtil.xml2Obj(userXML, UserBeanArray.class);
        return ua.getUserBean();
    }

    /**
     * 得到服务客户端申明
     * @return
     */
    private HrmServicePortType getHrmServicePortType(){
        if(null == hrmServicePortType){
            try {
                URL url = new URL(wsdl);
                hrmServicePortType = new HrmService(url).getHrmServiceHttpPort();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return hrmServicePortType;
    }

    private void dualOneDept(DepartmentBeanArray.DepartmentBean dept , String qzh){
        //<result column="DEPCODE" property="depcode" jdbcType="VARCHAR"/>code
        //<result column="DEPID" property="depid" jdbcType="VARCHAR"/> departmentid
        //<result column="GFZJ" property="gfzj" jdbcType="VARCHAR"/> getShoworder 排序字段
        //<result column="BH" property="bh" jdbcType="VARCHAR"/>    getSupdepartmentid
        Integer pid = -1;
        Integer did = -1;
        try {
            String gname = dept.getShortname();
            String bh = null != dept.getSupdepartmentid() ? dept.getSupdepartmentid().toString():"";
            //作为排序字段
            String gfzj = null != dept.getShoworder() ? dept.getShoworder().toString() : "";
            String depid = null != dept.getDepartmentid()? dept.getDepartmentid().toString() : "";
            String deptcode = dept.getCode();
            SGroup parentGroup = sGroupMapper.getGroupByDepID(bh!=null? bh.toString() : "");
            if(null != parentGroup){
                pid = parentGroup.getDid();
            }else{
                pid = 0;
            }
            SGroup group = sGroupMapper.getGroupByDepID(depid.toString());
            if(group == null){
                group = new SGroup();
                group.setDid(getMaxDid("S_GROUP"));
                group.setPid(pid);
                group.setQzh(qzh);
                group.setGid(0);
                group.setGname(gname);
                group.setDepcode(deptcode);
                group.setDepid(depid.toString());
                group.setBh(bh.toString());//oa中的superdeptid
                group.setBz(bz);
                group.setGfzj(gfzj.toString());
                sGroupMapper.insert(group);
                log.error("add group:" + group.toString());
            }else{
                if(!(pid.equals(group.getPid()) && gname.equals(group.getGname())
                        && depid.equals(group.getDepid())
                        && gfzj.equals(group.getGfzj()) && bh.equals(group.getBh()))){
                    group.setPid(pid);
                    group.setQzh(qzh);
                    group.setGid(0);
                    group.setGname(gname);
                    group.setDepcode(deptcode);
                    group.setDepid(depid.toString());
                    group.setBh(bh.toString());//oa中的superdeptid
                    group.setBz(bz);
                    group.setGfzj(gfzj.toString());
                    sGroupMapper.updateByPrimaryKey(group);
                    log.error("update group:" + group.toString());
                }else{
                    //nothing todo
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
    }
    private void dualOnOrgUser(UserBeanArray.UserBean ub){
        //<result column="ESBID" property="esbid" jdbcType="VARCHAR"/>  userid
        //<result column="ESBCODE" property="esbcode" jdbcType="VARCHAR"/> departmentid
        //status  0：试用  1：正式  2：临时  3：试用延期  4：解聘  5：离职  6：退休  7：无效
        Integer pid = -1;
        Integer did = -1;
        try {
            String username = ub.getLastname();
            String usercode = ub.getLoginid();
            String email = ub.getEmail();
            String esbID = null != ub.getUserid() ? ub.getUserid().toString() : "";
            String esbcode = null != ub.getDepartmentid() ? ub.getDepartmentid().toString() : "";
            Integer islsyh = getLamsStatus(ub.getStatus());

            SGroup group = sGroupMapper.getGroupByDepID(esbcode);
            if(null != group){
                pid = group.getDid();
            }else{
                pid = -1;
            }
            SUser user = sUserMapper.getUserByEsbid(esbID);
            if(user == null){
                SUserWithBLOBs userBlob = new SUserWithBLOBs();
                userBlob.setDid(getMaxDid("S_USER"));
                userBlob.setPid(pid);
                userBlob.setUsername(username);
                userBlob.setUsercode(usercode);
                userBlob.setPasswd(defaultPassword);
                userBlob.setEmail(email);
                userBlob.setIslsyh(islsyh);
                userBlob.setEsbid(esbID);
                userBlob.setEsbcode(esbcode);
                userBlob.setBz("新增:"+ DateUtil.getCurrentTimeStr());
                sUserMapper.insert(userBlob);
                SUserrole userrole = new SUserrole();
                userrole.setYhid(userBlob.getDid());
                userrole.setJsid(jsid);
                userrole.setDid( getMaxDid("S_USERROLE"));
                sUserroleMapper.insert(userrole);
                log.error("add user:" + userBlob.toString());
            }else{
                if(!(pid.equals(user.getPid()) && islsyh.equals(user.getIslsyh())
                        && username.equals(user.getUsername()))){
                    user.setPid(pid);
                    user.setUsername(username);
                    user.setEmail(email);
                    sUserMapper.updateByPrimaryKey(user);
                    log.error("update user:" + group.toString());
                }else{
                    //nothing todo
                }
            }

        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
    }

    //judge user is lock  status  0：试用  1：正式  2：临时  3：试用延期  4：解聘  5：离职  6：退休  7：无效
    //档案系统 0可用   -1锁定
    private int getLamsStatus(Integer status){
        if(null != status && status.equals(1)){
            return 0;
        }else{
            return -1;
        }
    }

    @Autowired
    @Value("${weaver.ip}")
    protected String ip;

    @Autowired
    @Value("${weaver.wsdl}")
    protected String wsdl;


    @Autowired
    @Value("${weaver.user.default.password}")
    protected String defaultPassword;

    private static String bz = "fromoa";
    private HrmServicePortType hrmServicePortType = null;
    private Logger log = (Logger) LoggerFactory.getLogger(this.getClass());
}
