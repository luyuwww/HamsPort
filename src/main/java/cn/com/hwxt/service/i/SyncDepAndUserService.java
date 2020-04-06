package cn.com.hwxt.service.i;

import com.seeyou.pojo.Department;

import java.lang.reflect.Member;
import java.util.List;

public interface SyncDepAndUserService {

    public void sync();

    /**
     * 同步全量部门信息,循环全宗列表一个个单位同步
     * cancel是1的是删除的部门 我们不同步。但是一旦同步过来的部门不用接口删除。可以在档案系统手动删除。
     */
    public void syncAllDept();
    /**
     * 同步所有全宗
     */
    public void syncAllUser();

    /**
     * 调用oa接口判断用户是否存在
     * @param usercode
     * @param password
     * @return
     */
    public Boolean checkUser(String usercode, String password);
    public String judgeSsoFromSeeyou(String token);

}
