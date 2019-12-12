
package weaver.hrm.sso;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "ZKHrmServicePortType", targetNamespace = "webservices.hrm.alex.com.cn")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface ZKHrmServicePortType {


    /**
     * 
     * @param in0
     * @param in2
     * @param in1
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(name = "out", targetNamespace = "webservices.hrm.alex.com.cn")
    @RequestWrapper(localName = "checkUser", targetNamespace = "webservices.hrm.alex.com.cn", className = "waear.hrm.sso.CheckUser")
    @ResponseWrapper(localName = "checkUserResponse", targetNamespace = "webservices.hrm.alex.com.cn", className = "waear.hrm.sso.CheckUserResponse")
    public boolean checkUser(
            @WebParam(name = "in0", targetNamespace = "webservices.hrm.alex.com.cn")
                    String in0,
            @WebParam(name = "in1", targetNamespace = "webservices.hrm.alex.com.cn")
                    String in1,
            @WebParam(name = "in2", targetNamespace = "webservices.hrm.alex.com.cn")
                    String in2);

    /**
     * 
     * @param in5
     * @param in0
     * @param in2
     * @param in1
     * @param in4
     * @param in3
     * @return
     *     returns waear.hrm.sso.ArrayOfUserBean
     */
    @WebMethod
    @WebResult(name = "out", targetNamespace = "webservices.hrm.alex.com.cn")
    @RequestWrapper(localName = "getHrmUserInfo", targetNamespace = "webservices.hrm.alex.com.cn", className = "waear.hrm.sso.GetHrmUserInfo")
    @ResponseWrapper(localName = "getHrmUserInfoResponse", targetNamespace = "webservices.hrm.alex.com.cn", className = "waear.hrm.sso.GetHrmUserInfoResponse")
    public ArrayOfUserBean getHrmUserInfo(
            @WebParam(name = "in0", targetNamespace = "webservices.hrm.alex.com.cn")
                    String in0,
            @WebParam(name = "in1", targetNamespace = "webservices.hrm.alex.com.cn")
                    String in1,
            @WebParam(name = "in2", targetNamespace = "webservices.hrm.alex.com.cn")
                    String in2,
            @WebParam(name = "in3", targetNamespace = "webservices.hrm.alex.com.cn")
                    String in3,
            @WebParam(name = "in4", targetNamespace = "webservices.hrm.alex.com.cn")
                    String in4,
            @WebParam(name = "in5", targetNamespace = "webservices.hrm.alex.com.cn")
                    String in5);

    /**
     * 
     * @param in5
     * @param in0
     * @param in2
     * @param in1
     * @param in4
     * @param in3
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(name = "out", targetNamespace = "webservices.hrm.alex.com.cn")
    @RequestWrapper(localName = "getHrmUserInfoXML", targetNamespace = "webservices.hrm.alex.com.cn", className = "waear.hrm.sso.GetHrmUserInfoXML")
    @ResponseWrapper(localName = "getHrmUserInfoXMLResponse", targetNamespace = "webservices.hrm.alex.com.cn", className = "waear.hrm.sso.GetHrmUserInfoXMLResponse")
    public String getHrmUserInfoXML(
            @WebParam(name = "in0", targetNamespace = "webservices.hrm.alex.com.cn")
                    String in0,
            @WebParam(name = "in1", targetNamespace = "webservices.hrm.alex.com.cn")
                    String in1,
            @WebParam(name = "in2", targetNamespace = "webservices.hrm.alex.com.cn")
                    String in2,
            @WebParam(name = "in3", targetNamespace = "webservices.hrm.alex.com.cn")
                    String in3,
            @WebParam(name = "in4", targetNamespace = "webservices.hrm.alex.com.cn")
                    String in4,
            @WebParam(name = "in5", targetNamespace = "webservices.hrm.alex.com.cn")
                    String in5);

}
