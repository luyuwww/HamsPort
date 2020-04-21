package cn.com.hwxt.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @author DaMo
 * 人员机构bean
 */
public class EcidiEepOrgPerson implements Serializable {
    private static final long serialVersionUID = 471753703905980096L;
    private String name;
    private String id;
    private String personName;
    private String orgName;
    private String opName;//操作名
    private Integer orderNum;//顺序 0开始

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOpName() {
        return opName;
    }

    public void setOpName(String opName) {
        this.opName = opName;
    }
}
