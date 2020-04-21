package cn.com.hwxt.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author DaMo
 * 华东院提供的meta_file.xml解释 文件对象
 */
public class EcidiSimpleFile implements Serializable {
    private static final long serialVersionUID = -2782494731465794976L;
    private String title;//标题
    private String size;//大小
    private String fileBizName;//名称(文件名),包含扩展名
    private String  ext;
    private String md5;//数字摘要值
    private Date createTime;//创建时间
    private String relationType;//关系类型
    private String relateionDescription;//关系描述
    private Boolean isAttachemnt;//是否是附件
    private Integer orderNum;

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getFileBizName() {
        return fileBizName;
    }

    public void setFileBizName(String fileBizName) {
        this.fileBizName = fileBizName;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRelationType() {
        return relationType;
    }

    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }

    public String getRelateionDescription() {
        return relateionDescription;
    }

    public void setRelateionDescription(String relateionDescription) {
        this.relateionDescription = relateionDescription;
    }

    public Boolean getAttachemnt() {
        return isAttachemnt;
    }

    public void setAttachemnt(Boolean attachemnt) {
        isAttachemnt = attachemnt;
    }
}