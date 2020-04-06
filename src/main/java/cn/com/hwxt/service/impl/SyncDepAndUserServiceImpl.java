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
import cn.com.hwxt.util.SeeYouUtil;
import cn.com.hwxt.util.XmlObjUtil;
import com.seeyou.pojo.Department;
import com.seeyou.pojo.Member;
import com.sun.xml.xsom.impl.EmptyImpl;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

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
        syncAllDept();//同步部门
        syncAllUser();//同步用户;
        //清除无用的用户关系
        execSql("DELETE FROM S_USERROLE WHERE YHID NOT IN (SELECT DID FROM S_USER)");
        //通知lams同步用户
        CommonUtil.syncActivitUser(lamsIP);
    }

    /**
     * 同步全量部门信息,循环全宗列表一个个单位同步
     * public static String getDeptXml(String ip , String unitName , String appKey , String appPassword){
     */
    public synchronized void syncAllDept() {
        List<SQzh> qzhList = sQzhMapper.listAllSqzh();
        for (SQzh sQzh : qzhList) {
            if (StringUtils.isBlank(sQzh.getBz())) {
                continue;
            } else {
                syncAllDeptAtOneUnit(sQzh.getBz(), sQzh.getQzh());
            }
        }
    }

    /**
     * 同步全量部门信息,循环全宗列表一个个单位同步
     */
    private void syncAllDeptAtOneUnit(String unitName, String qzh) {
        String xml = "";
        try {
            xml = SeeYouUtil.getDeptXml(ip, unitName, appKey, appValue);
            List<Department> deptList = SeeYouUtil.convert2Dept(xml);
            if (deptList != null && deptList.size() > 0) {
                for (Department dept : deptList) {
                    dualOneDept(dept, qzh);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 同步全量用户
     */
    public synchronized void syncAllUser() {
        List<SQzh> qzhList = sQzhMapper.listAllSqzh();
        for (SQzh sQzh : qzhList) {
            if (StringUtils.isBlank(sQzh.getBz())) {
                continue;
            } else {
                syncMemberAtOneUnit(sQzh.getBz(), sQzh.getQzh());
            }
        }
    }

    /**
     * 同步全量部门信息,循环全宗列表一个个单位同步
     */
    private void syncMemberAtOneUnit(String unitName, String qzh) {
        String xml = "";
        try {
            xml = SeeYouUtil.getMemberXml(ip, unitName, appKey, appValue);
            List<Member> memberList = SeeYouUtil.convert2User(xml);
            if (memberList != null && memberList.size() > 0) {
                for (Member member : memberList) {
                    dualOnOrgUser(member, qzh);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Boolean checkUser(String usercode, String password) {
        return false;
    }

    private void dualOneDept(Department dept, String qzh) {
        Integer pid = -1;
        Integer did = -1;
        try {
            String gname = dept.getName();
            String depCode = (null != dept.getDepartmentNumber() ? dept.getDepartmentNumber() : "");
            //作为排序字段
            Integer gid = (null != dept.getDepSort() ? Integer.parseInt(dept.getDepSort()) : -1);
            String gfzj = dept.getDepartmentNameStr();
            String discursion = (null != dept.getDiscursion() ? dept.getDiscursion() : "");
            SGroup parentGroup = null;
            //
            if (StringUtils.isNotBlank(dept.getParentName())) {
                String compareKey = gfzj.replace("_"+dept.getName() , "");
                parentGroup = sGroupMapper.getGroupByGfzj4Seeyou(compareKey, qzh);
            }
            if (null != parentGroup) {
                pid = parentGroup.getDid();
            } else {
                pid = 0;
            }
            SGroup group = sGroupMapper.getGroupByDepCode(depCode);
            if (group == null) {
                group = new SGroup();
                group.setDid(getMaxDid("S_GROUP"));
                group.setPid(pid);
                group.setQzh(qzh);
                group.setGid(gid);
                group.setGname(gname);
                group.setDepcode(depCode);
                group.setGfzj(gfzj);
                group.setBz(bz + ":" + discursion);
                sGroupMapper.insert(group);
                log.error("add group:" + group.toString());
            } else {
                if (!(pid.equals(group.getPid()) && gname.equals(group.getGname())
                        && gfzj.equals(group.getGfzj()))) {
                    group.setPid(pid);
                    group.setQzh(qzh);
                    group.setGid(0);
                    group.setGname(gname);
                    group.setDepcode(depCode);
                    group.setGfzj(gfzj);
                    group.setBz(bz + ":" + discursion);
                    sGroupMapper.updateByPrimaryKey(group);
                    log.error("update group:" + group.toString());
                } else {
                    //nothing todo
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 处理一个部门的员工
     * @param ub
     */
    private void dualOnOrgUser(Member ub, String qzh) {
        //<result column="ESBID" property="esbid" jdbcType="VARCHAR"/>  userid
        //<result column="ESBCODE" property="esbcode" jdbcType="VARCHAR"/> departmentid
        //status  0：试用  1：正式  2：临时  3：试用延期  4：解聘  5：离职  6：退休  7：无效
        Integer pid = -1;
        Integer did = -1;
        try {
            String username = ub.getTrueName();
            String usercode = ub.getLoginName();
            String email = ub.getEmail();
            String esbID = null != ub.getStaffNumber() ? ub.getStaffNumber() : "";
            String esbcode = ub.getDepartmentNameStr();
            String parentName = null != ub.getPname() ? ub.getPname() : "";
            String discursion = (null != ub.getDiscursion() ? ub.getDiscursion() : "");
            Integer islsyh = getLamsStatus(0);

            SGroup group = null;
            if(StringUtils.isNotBlank(esbcode)){
                System.out.println(esbcode);
                group = sGroupMapper.getGroupByGfzj4Seeyou(esbcode , qzh);
            }
            if (null != group) {
                pid = group.getDid();
            } else {
                pid = -1;
            }
            SUser user = sUserMapper.getUserByEsbid(esbID);
            //排除有重复的问题，如果oa主键找不到，再用我们主键找一次
            if (null == user) {
                user = sUserMapper.getUserByUsercode(usercode);
                if (user != null) {
                    user.setEsbid(esbID);
                }
            }

            if (user == null) {
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
                userBlob.setBz("新增:" +discursion+ DateUtil.getCurrentTimeStr());
                sUserMapper.insert(userBlob);
                SUserrole userrole = new SUserrole();
                userrole.setYhid(userBlob.getDid());
                userrole.setJsid(jsid);
                userrole.setDid(getMaxDid("S_USERROLE"));
                sUserroleMapper.insert(userrole);
                log.error("add user:" + userBlob.toString());
            } else {
                if (!(pid.equals(user.getPid()) && islsyh.equals(user.getIslsyh())
                        && username.equals(user.getUsername()))) {
                    user.setPid(pid);
                    user.setUsername(username);
                    user.setEmail(email);
                    user.setIslsyh(islsyh);
                    sUserMapper.updateByPrimaryKey(user);
                    log.error("update user:"+discursion + user.toString());
                } else {
                    //nothing todo
                }
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public String judgeSsoFromSeeyou(String token){
        return SeeYouUtil.jdugeSSO(ip , token);
    }

    //档案系统 0可用   -1锁定
    private int getLamsStatus(Integer status) {
        return 0;
    }

    @Autowired
    @Value("${seeyou.ip}")
    protected String ip;

    @Autowired
    @Value("${seeyou.app.name}")
    protected String appKey;
    @Autowired
    @Value("${seeyou.app.password}")
    protected String appValue;

    @Autowired
    @Value("${user.default.password}")
    protected String defaultPassword;

    private static String bz = "fromoa";
    private Logger log = (Logger) LoggerFactory.getLogger(this.getClass());
}
