
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**

 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "submitDoucmentResultResult"
})
@XmlRootElement(name = "SubmitDoucmentResultResponse")
public class SubmitDoucmentResultResponse {

    @XmlElement(name = "SubmitDoucmentResultResult")
    protected boolean submitDoucmentResultResult;

    /**
     *
     * 
     */
    public boolean isSubmitDoucmentResultResult() {
        return submitDoucmentResultResult;
    }

    /**
     *      *
     */
    public void setSubmitDoucmentResultResult(boolean value) {
        this.submitDoucmentResultResult = value;
    }

}
