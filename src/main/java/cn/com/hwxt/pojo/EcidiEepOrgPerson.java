package cn.com.hwxt.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @author DaMo
 * 人员机构bean
 * @UPDATE 2020/4/2-23:56
 * @since 2020/4/2-23:56
 */
public class EcidiEepOrgPerson implements Serializable {
    private static final long serialVersionUID = 471753703905980096L;
    private String name;
    private String id;
    private String personName;
    private Date orgName;
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

    public Date getOrgName() {
        return orgName;
    }

    public void setOrgName(Date orgName) {
        this.orgName = orgName;
    }
}
