package cn.com.hwxt.util;

import ch.qos.logback.classic.Logger;
import cn.com.hwxt.pojo.FDTable;
import cn.com.hwxt.pojo.SUser;
import org.apache.commons.io.FileUtils;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.UUID;

public class CommonUtil {

    /**
     * 得到登录Lams并且进入相应模块的URL
     *
     * @param user     用户
     * @param modName  模块中文名称
     * @param modOwner 模块所属者 user,  "任务列表", "izerui"
     */
    public static String generatUrl(SUser user, String modName, String modOwner) {
        StringBuffer sb = new StringBuffer();
        sb.append("Lams/autoLogin?card=").append(SeriKeyOper.encrypt(user.getUsercode()));
        sb.append("&serikey=").append(SeriKeyOper.encrypt(user.getPasswd())).append("&moduleName=");
        sb.append(SeriKeyOper.encrypt(modName)).append("&moduleOwner=").append(SeriKeyOper.encrypt(modOwner));
        sb.append("&random=").append(Math.random());
        return sb.toString();
    }

    /**
     * 得到登录Lams的应用端并且进入待办列表
     * @param user 用户
     */
    public static String generat2WebUrl(SUser user , String taskid){
        StringBuffer sb = new StringBuffer();
        sb.append("Lams/autoLogin4Web?card=").append(SeriKeyOper.encrypt(user.getUsercode()));
        sb.append("&serikey=").append(SeriKeyOper.encrypt(user.getPasswd())).append("&_url=/web/todoList&taskid="+taskid);
        sb.append("&random=").append(Math.random());
        return sb.toString();
    }

    /**
     * 得到登录Lams的app页面，这只是简单的审批界面，不是app并且进入待办列表
     * @param user 用户
     */
    public static String generat2AppUrl(SUser user , String taskid){
        StringBuffer sb = new StringBuffer();
        sb.append("Lams/appLogin4ShenPi?card=").append(SeriKeyOper.encrypt(user.getUsercode()));
        sb.append("&serikey=").append(SeriKeyOper.encrypt(user.getPasswd())).append("&_url=/app/todoListApp&taskid="+taskid);
        sb.append("&random=").append(Math.random());
        return sb.toString();
    }

    public static void main(String[] args) {
        StringBuffer sb = new StringBuffer();
        sb.append("Lams/appLogin4ShenPi?card=").append(SeriKeyOper.encrypt("100014"));
        sb.append("&serikey=").append(SeriKeyOper.encrypt("ams2000")).append("&_url=/app/todoListApp&taskid=9a2a9589-1e45-11ea-8e8f-005056c00001");
        sb.append("&random=").append(Math.random());
        System.out.println(sb.toString());



        StringBuffer sb1 = new StringBuffer();
        sb1.append("Lams/autoLogin4Web?card=").append(SeriKeyOper.encrypt("004820"));
        sb1.append("&serikey=").append(SeriKeyOper.encrypt("ams2000")).append("&_url=/web/myApplication");
        sb1.append("&random=").append(Math.random());
        System.out.println(sb1.toString());

        StringBuffer sb2 = new StringBuffer();
        sb2.append("Lams/autoLogin4Web?card=").append(SeriKeyOper.encrypt("004820"));
        sb2.append("&serikey=").append(SeriKeyOper.encrypt("ams2000")).append("&_url=/web/todoList&taskid=taskid=2b1d8434-1e21-11ea-8315-005056c00001");
        sb2.append("&random=").append(Math.random());
        System.out.println(sb2.toString());

    }

    /**
     * 得到登录Lams的应用端并且进入我的申请
     * @param user 用户
     */
    public static String generat2WebUrlMyApplication(SUser user , String taskid){
        StringBuffer sb = new StringBuffer();
        sb.append("Lams/autoLogin4Web?card=").append(SeriKeyOper.encrypt(user.getUsercode()));
        sb.append("&serikey=").append(SeriKeyOper.encrypt(user.getPasswd())).append("&_url=/web/myApplication");
        sb.append("&random=").append(Math.random());
        return sb.toString();
    }

    /**
     * 发送get请求不需要返回参数
     */
    public static void doHttpGet(String urlString) {
        try {
            URL url = new URL(urlString);
            url.openStream().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * <p>Title: 每次esb发布消息,记录一个整的的消息</p>
     * <p>Description: </p>
     *
     * @param xmlDate  要同步的XML数据
     * @param basePath 文件基础路径 不带文件名
     */
    public void writeEsbLog(String xmlDate, String basePath) {
        try {
            FileUtils.writeStringToFile(new File(basePath), xmlDate);
        } catch (IOException e) {
            log.error("insert Esb log error", e);
        }
    }

    /**
     * <p>Title:  跟据字段中文名得到fdtable</p>
     *
     * @param fieldChname 字段中文名称得到fdtable
     * @date 2014年5月8日
     */
    public static FDTable getFDtable(List<FDTable> list, String fieldname) {
        FDTable fDtable = null;
        for (FDTable ele : list) {
            if (ele.getFieldname().equals(fieldname)) {
                fDtable = ele;
                break;
            }
        }
        return fDtable;
    }

    /**
     * 生成guid
     */
    public static String getGuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static void syncActivitUser(String lamsIP) {
        String urlStr = "http://" + lamsIP + "/Lams/activiti/syn";
        CommonUtil.doHttpGet(urlStr);
    }

    private Logger log = (Logger) LoggerFactory.getLogger(this.getClass());
}
