package cn.com.hwxt.pojo;
// default package

import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.Date;
import java.util.UUID;

/**
 * 待插入eep处理队列的
 */
public class SBizEepQueue implements java.io.Serializable {

    public SBizEepQueue(String appName, String appPk , String packagePath , String  md5 ,String levelStr,  Integer libcode , String pzm) {
        this.appPk = appPk;
        this.uuid = UUID.randomUUID().toString();
        this.appName = appName;
        this.levelStr = levelStr;
        this.libcode = libcode;
        this.md5 = md5;
        this.packagePath = packagePath;
        this.createTime = new Date();
        this.updateTime = new Date();
        this.pzm = pzm;
        this.status = 0;
    }

    public SBizEepQueue() {    }

    private static final long serialVersionUID = 6388598100603903422L;

    private String uuid;
    private String appName;
    private String appPk;//对方的主键
    private Date createTime;
    private Date updateTime;
    private Integer status;//0 null未处理, 1已经处理,2失败了
    private String memo;//如果失败记录失败原因
    private String levelStr;//d_vol  d_file
    private String md5;//d_vol  d_file
    private String packagePath;//d_vol  d_file
    private String pzm;//d_vol  d_file
    private Integer libcode;

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getAppPk() {
        return appPk;
    }

    public void setAppPk(String appPk) {
        this.appPk = appPk;
    }

    public String getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getLevelStr() {
        return levelStr;
    }

    public void setLevelStr(String levelStr) {
        this.levelStr = levelStr;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getLibcode() {
        return libcode;
    }

    public void setLibcode(Integer libcode) {
        this.libcode = libcode;
    }
}