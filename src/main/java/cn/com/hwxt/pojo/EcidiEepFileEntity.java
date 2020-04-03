package cn.com.hwxt.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author DaMo
 * 华东院提供的meta_file.xml解释成poj哦对象
 * @UPDATE 2020/4/2-23:56
 * @since 2020/4/2-23:56
 */
public class EcidiEepFileEntity implements Serializable {
    private static final long serialVersionUID = 7413660854382138473L;
    private EcidiEepFile mainFile;//主文件
    private List<EcidiEepFile> attachement;//表单附件信息
    private Date createTime;
    private String formID;//表单ID
    private String foramtJson;//格式化后的JSON
    private List<EcidiEepFileEsignature> esignatures; //电子签章信息list

    public EcidiEepFile getMainFile() {
        return mainFile;
    }

    public void setMainFile(EcidiEepFile mainFile) {
        this.mainFile = mainFile;
    }

    public List<EcidiEepFile> getAttachement() {
        return attachement;
    }

    public void setAttachement(List<EcidiEepFile> attachement) {
        this.attachement = attachement;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getFormID() {
        return formID;
    }

    public void setFormID(String formID) {
        this.formID = formID;
    }

    public String getForamtJson() {
        return foramtJson;
    }

    public void setForamtJson(String foramtJson) {
        this.foramtJson = foramtJson;
    }

    public List<EcidiEepFileEsignature> getEsignatures() {
        return esignatures;
    }

    public void setEsignatures(List<EcidiEepFileEsignature> esignatures) {
        this.esignatures = esignatures;
    }
}