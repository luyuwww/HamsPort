package com.seeyou.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @author DaMo
 * @UPDATE 2020/4/4-18:07
 * @since 2020/4/4-18:07
 */
public class Member implements Serializable {
    private static final long serialVersionUID = 7419315988584703175L;
    private List<String> departmentName;//部门名称 可能是个列表
    private String pName;//直接父部门名称
    private String trueName;//用户名称
    private String birthday;//
    private String secondOcupationName;// 副岗
    private String sex;//
    private String staffNumber;//员工号
    private String otypeName;//职务级别
    private String familyPhone;//
    private String familyAddress;//
    private String accountId;//单位ID
    private String discursion;//描述
    private String perSort;//
    private String officePhone;//
    private String mobilePhone;//
    private String identity;//身份证
    private String loginName;//登录名
    private String ocupationName;//岗位
    private String id;// 人员ID
    private String email;//

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List<String> getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(List<String> departmentName) {
        this.departmentName = departmentName;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSecondOcupationName() {
        return secondOcupationName;
    }

    public void setSecondOcupationName(String secondOcupationName) {
        this.secondOcupationName = secondOcupationName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getStaffNumber() {
        return staffNumber;
    }

    public void setStaffNumber(String staffNumber) {
        this.staffNumber = staffNumber;
    }

    public String getOtypeName() {
        return otypeName;
    }

    public void setOtypeName(String otypeName) {
        this.otypeName = otypeName;
    }

    public String getFamilyPhone() {
        return familyPhone;
    }

    public void setFamilyPhone(String familyPhone) {
        this.familyPhone = familyPhone;
    }

    public String getFamilyAddress() {
        return familyAddress;
    }

    public void setFamilyAddress(String familyAddress) {
        this.familyAddress = familyAddress;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getDiscursion() {
        return discursion;
    }

    public void setDiscursion(String discursion) {
        this.discursion = discursion;
    }

    public String getPerSort() {
        return perSort;
    }

    public void setPerSort(String perSort) {
        this.perSort = perSort;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getOcupationName() {
        return ocupationName;
    }

    public void setOcupationName(String ocupationName) {
        this.ocupationName = ocupationName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
