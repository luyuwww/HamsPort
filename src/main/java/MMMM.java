import cn.com.hwxt.util.XmlObjUtil;
import localhost.services.hrmservice.HrmService;
import localhost.services.hrmservice.HrmServicePortType;
import weaver.hrm.jaxb.DepartmentBeanArray;
import weaver.hrm.jaxb.SubCompanyBeanArray;
import weaver.hrm.jaxb.UserBeanArray;
import weaver.hrm.webservice.DepartmentBean;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.Reader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * @author DaMo
 * @UPDATE 2019/8/21-19:26
 * @since 2019/8/21-19:26
 * http://183.64.63.155:83/services/HrmService?wsdl
 * weaver.ip=192.168.2.42
 */
public class MMMM {
    private static String ip= "192.168.2.42";
    public static void main(String[] args) throws MalformedURLException, JAXBException {
        URL url = new URL("http://183.64.63.155:83/services/HrmService?wsdl");
//        URL url = new URL("http://oa.szcgc.com:18088/services/HrmService?wsdl");
        HrmServicePortType hrmServicePortType = new HrmService(url).getHrmServiceHttpPort();

        List<SubCompanyBeanArray.SubCompanyBean> list = oaOrgList(hrmServicePortType);
        for (SubCompanyBeanArray.SubCompanyBean sb : list) {
            System.out.println(sb.getFullname());
        }

//        System.out.println("***********************************************");
//        List<DepartmentBeanArray.DepartmentBean> deptList = oaDeptListByOrgID(hrmServicePortType, "240");
//        for (DepartmentBeanArray.DepartmentBean dddd : deptList) {
//            System.out.println(dddd.getFullname());
//        }
//
//        System.out.println("***********************************************");
//        List<UserBeanArray.UserBean> userList = oaUserListByOrgIDAndDeptID(hrmServicePortType  , "5" , null);
//        for (UserBeanArray.UserBean userBean : userList) {
//
//            System.out.println(userBean.getLastname());
//        }

    }

    /**
     * 得到所有子公司
     * @return
     */
    public static List<SubCompanyBeanArray.SubCompanyBean> oaOrgList( HrmServicePortType hrmServicePortType ){
        String orgXml = hrmServicePortType.getHrmSubcompanyInfoXML(ip);
        SubCompanyBeanArray subCompanyBeanArray = XmlObjUtil.xml2Obj(orgXml, SubCompanyBeanArray.class);
        return subCompanyBeanArray.getSubCompanyBean();
    }

    /**
     * 得到所子公司下所有部门
     * @return
     */
    public static List<DepartmentBeanArray.DepartmentBean> oaDeptListByOrgID(HrmServicePortType hrmServicePortType  ,String orgID){
        String deptXml = hrmServicePortType.getHrmDepartmentInfoXML(ip , orgID);
        DepartmentBeanArray departmentBeanArray =  XmlObjUtil.xml2Obj(deptXml, DepartmentBeanArray.class);
        return departmentBeanArray.getDepartmentBean();
    }

    /**
     * 得到子公司某部门下所有用户 如果depID为空就会得到该公司下所有用户
     * @return
     */
    public static List<UserBeanArray.UserBean> oaUserListByOrgIDAndDeptID(HrmServicePortType hrmServicePortType  , String orgID , String deptID){
        String userXML = hrmServicePortType.getHrmUserInfoXML(ip , null, orgID , deptID, null, null);
        UserBeanArray ua = XmlObjUtil.xml2Obj(userXML, UserBeanArray.class);
        return ua.getUserBean();
    }
}
