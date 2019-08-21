package cn.com.hwxt.service.i;

import cn.com.hwxt.pojo.SBacklog;
import weaver.hrm.webservice.SubCompanyBean;

import java.util.List;

public interface SyncDepAndUserService {

    public void sync();

    /**
     * 同步全量部门信息,循环全宗列表一个个单位同步
     * cancel是1的是删除的部门 我们不同步。但是一旦同步过来的部门不用接口删除。可以在档案系统手动删除。
     */
    public void syncAllDept();

    /**
     * 同步全量部门信息 循环全宗列表一个个单位同步 只同步 status=1的 遇到不为1的 设置成禁用  accounttype只管主的
     * status  0：试用  1：正式  2：临时  3：试用延期  4：解聘  5：离职  6：退休  7：无效
     * accounttype Null:主账号,1:次账号
     */
    public void syncAllUser();

    public void syncDept(String orgId);

    public void syncUser(String orgId);
    public List<SubCompanyBean> oaOrgList();

}
