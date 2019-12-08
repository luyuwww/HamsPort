
package weaver.hrm;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ArrayOfUserBean complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="ArrayOfUserBean">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="UserBean" type="{http://webservice.hrm.weaver}UserBean" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfUserBean", namespace = "http://webservice.hrm.weaver", propOrder = {
    "userBean"
})
public class ArrayOfUserBean {

    @XmlElement(name = "UserBean", nillable = true)
    protected List<UserBean> userBean;

    /**
     * Gets the value of the userBean property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the userBean property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUserBean().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UserBean }
     * 
     * 
     */
    public List<UserBean> getUserBean() {
        if (userBean == null) {
            userBean = new ArrayList<UserBean>();
        }
        return this.userBean;
    }

}