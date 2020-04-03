package cn.com.hwxt.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author DaMo
 * 华东院提供的meta_file.xml解释成 文件实体关系
 * @UPDATE 2020/4/2-23:56
 * @since 2020/4/2-23:56
 */
public class EcidiEepFileEntityRelation implements Serializable {
    private static final long serialVersionUID = 8627029199034517439L;
    private String title;//文件标识符
    private String relationID;//被关联文件标识符
    private String rType;//关系类型
    private Date rDescription;//关系描述

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelationID() {
        return relationID;
    }

    public void setRelationID(String relationID) {
        this.relationID = relationID;
    }

    public String getrType() {
        return rType;
    }

    public void setrType(String rType) {
        this.rType = rType;
    }

    public Date getrDescription() {
        return rDescription;
    }

    public void setrDescription(Date rDescription) {
        this.rDescription = rDescription;
    }
}
