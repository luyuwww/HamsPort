package cn.com.hwxt.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author DaMo
 * 华东院提供的meta_file.xml 电子签章
 * @UPDATE 2020/4/2-23:56
 * @since 2020/4/2-23:56
 */
public class EcidiEepFileEsignature implements Serializable {
    private static final long serialVersionUID = -4227678053683697030L;
    private String signID;//电子签章id
    private Integer order;//顺序 0开始
    private String signValue;//电子签章值

    public String getSignID() {
        return signID;
    }

    public void setSignID(String signID) {
        this.signID = signID;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getSignValue() {
        return signValue;
    }

    public void setSignValue(String signValue) {
        this.signValue = signValue;
    }
}