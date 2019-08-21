package cn.com.hwxt.pojo;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementRef;

/**
 * @author DaMo
 * @UPDATE 2019/8/21-10:52
 * @since 2019/8/21-10:52
 */
public class OaCompany {
    private String canceled;
    private String code;
    private String fullname;
    private String shortname;
    private String showorder;
    private String subcompanyid;
    private String supsubcompanyid;
    private String website;
    private String action;
    private String lastChangdate;

    public String getCanceled() {
        return canceled;
    }

    public void setCanceled(String canceled) {
        this.canceled = canceled;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public String getShoworder() {
        return showorder;
    }

    public void setShoworder(String showorder) {
        this.showorder = showorder;
    }

    public String getSubcompanyid() {
        return subcompanyid;
    }

    public void setSubcompanyid(String subcompanyid) {
        this.subcompanyid = subcompanyid;
    }

    public String getSupsubcompanyid() {
        return supsubcompanyid;
    }

    public void setSupsubcompanyid(String supsubcompanyid) {
        this.supsubcompanyid = supsubcompanyid;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getLastChangdate() {
        return lastChangdate;
    }

    public void setLastChangdate(String lastChangdate) {
        this.lastChangdate = lastChangdate;
    }
}
