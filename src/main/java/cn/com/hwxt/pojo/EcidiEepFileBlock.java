package cn.com.hwxt.pojo;

import java.io.Serializable;

/**
 * @author DaMo
 * 华东院提供的meta_file.xml解释 文件实体和文件实体关系集合
 * @UPDATE 2020/4/2-23:56
 * @since 2020/4/2-23:56
 */
public class EcidiEepFileBlock implements Serializable {
    private static final long serialVersionUID = 6031406175649152360L;
    private EcidiEepFileEntity fileEntity;//实体文件 or 实体文件关系
    private EcidiEepFileEntityRelation fileEntityRelation;
    private Integer order;//顺序 0开始

    public EcidiEepFileEntity getFileEntity() {
        return fileEntity;
    }

    public void setFileEntity(EcidiEepFileEntity fileEntity) {
        this.fileEntity = fileEntity;
    }

    public EcidiEepFileEntityRelation getFileEntityRelation() {
        return fileEntityRelation;
    }

    public void setFileEntityRelation(EcidiEepFileEntityRelation fileEntityRelation) {
        this.fileEntityRelation = fileEntityRelation;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}
