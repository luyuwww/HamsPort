package cn.com.hwxt.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author DaMo
 */
public class EcidiSimpleEEP implements Serializable {
    private static final long serialVersionUID = 3251943850449831348L;
    private String packageDescription;//封装包格式描述
    private String version;//版本
    private String source;//数据来源
    private Date createTime;//封装包创建时间
    private String serverIP;//服务器 IP
    private String serverMAC;//服务器 MAC
    private String clientIP;//客户端 IP
    private String packageType;//	封装包类型
    private String packageTypeDescription;//	封装包类型描述

    private Map<String, Object> bizEntity;//业务实体块
    private List<EcidiEepOrgPerson> orgPersonList;//机构人员实体块

    private List<EcidiSimpleFile> fileList ;

    public String getPackageDescription() {
        return packageDescription;
    }

    public void setPackageDescription(String packageDescription) {
        this.packageDescription = packageDescription;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getServerIP() {
        return serverIP;
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    public String getServerMAC() {
        return serverMAC;
    }

    public void setServerMAC(String serverMAC) {
        this.serverMAC = serverMAC;
    }

    public String getClientIP() {
        return clientIP;
    }

    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public String getPackageTypeDescription() {
        return packageTypeDescription;
    }

    public void setPackageTypeDescription(String packageTypeDescription) {
        this.packageTypeDescription = packageTypeDescription;
    }

    public Map<String, Object> getBizEntity() {
        return bizEntity;
    }

    public void setBizEntity(Map<String, Object> bizEntity) {
        this.bizEntity = bizEntity;
    }

    public List<EcidiEepOrgPerson> getOrgPersonList() {
        return orgPersonList;
    }

    public void setOrgPersonList(List<EcidiEepOrgPerson> orgPersonList) {
        this.orgPersonList = orgPersonList;
    }

    public List<EcidiSimpleFile> getFileList() {
        return fileList;
    }

    public void setFileList(List<EcidiSimpleFile> fileList) {
        this.fileList = fileList;
    }
}
