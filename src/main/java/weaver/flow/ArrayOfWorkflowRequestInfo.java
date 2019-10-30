
package weaver.flow;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfWorkflowRequestInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfWorkflowRequestInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="WorkflowRequestInfo" type="{http://webservices.workflow.weaver}WorkflowRequestInfo" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfWorkflowRequestInfo", namespace = "http://webservices.workflow.weaver", propOrder = {
    "workflowRequestInfo"
})
public class ArrayOfWorkflowRequestInfo {

    @XmlElement(name = "WorkflowRequestInfo", nillable = true)
    protected List<WorkflowRequestInfo> workflowRequestInfo;

    /**
     * Gets the value of the workflowRequestInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the workflowRequestInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getWorkflowRequestInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WorkflowRequestInfo }
     * 
     * 
     */
    public List<WorkflowRequestInfo> getWorkflowRequestInfo() {
        if (workflowRequestInfo == null) {
            workflowRequestInfo = new ArrayList<WorkflowRequestInfo>();
        }
        return this.workflowRequestInfo;
    }

}
