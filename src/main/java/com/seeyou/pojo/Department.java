package com.seeyou.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @author DaMo
 * @UPDATE 2020/4/4-18:07
 * @since 2020/4/4-18:07
 */
public class Department implements Serializable {
    private List<String> departmentName;//部门名称 可能是个列表
    private String name;//部门名称
    private String parentName;//父级部门名称
    private String accountId;//单位ID
    private String discursion;//描述
    private String depSort;//排序号
    private String departmentNumber;//部门代码

    public String getDepartmentNameStr(){
        String rslt = "";
        if(null == departmentName || departmentName.size()==0){
            rslt = "";
        }else{
            for (String s : departmentName) {
                rslt = rslt+"_"+s;
            }
        }
        return rslt;
    }
    public List<String> getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(List<String> departmentName) {
        this.departmentName = departmentName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getDepSort() {
        return depSort;
    }

    public void setDepSort(String depSort) {
        this.depSort = depSort;
    }

    public String getDepartmentNumber() {
        return departmentNumber;
    }

    public void setDepartmentNumber(String departmentNumber) {
        this.departmentNumber = departmentNumber;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    private static final long serialVersionUID = -469410068191094470L;
}
