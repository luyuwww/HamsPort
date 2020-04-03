package cn.com.hwxt.junit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.Assert;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;


public class ParseXmlTest {
    public static void main(String[] args) throws Exception {
//		File file = new File("C:/ML_OA_DATA.xml");
//		String xmlStr = FileUtils.readFileToString(file , "UTF-8");
//		Reader reader = new StringReader(xmlStr);
//        Unmarshaller unmarshaller =  JAXBContext.newInstance(MLXmlResult.class).createUnmarshaller();     
//        MLXmlResult result = (MLXmlResult) unmarshaller.unmarshal(reader); 
//        List<cn.com.hwxt.pojo.MLXmlResult.DocInfo.Field> list = result.getDocInfo().getField();
//        List<cn.com.hwxt.pojo.MLXmlResult.Attachments.Attachment> eFileList = result.getAttachments().getAttachment();
//        for (cn.com.hwxt.pojo.MLXmlResult.Attachments.Attachment efile : eFileList) {
//        	System.out.println(efile.getFtpdir());
//		}
////        for (Result.DocInfo.Field field : list) {
////        	System.out.println(field.getColName()+":"+field.getValue());
////		}
//  
//        Marshaller mashaller =  JAXBContext.newInstance(MLXmlResult.class).createMarshaller();
//        MLObjectFactory factory = new MLObjectFactory();
//        MLXmlResult toxmlResult = factory.createResult();
//        //可选 开始
//        cn.com.hwxt.pojo.MLXmlResult.DocInfo.Field theField = factory.createResultDocInfoField();
//        theField.setColName("中文桑德菲杰三闾大夫");
//        cn.com.hwxt.pojo.MLXmlResult.DocInfo dii = factory.createResultDocInfo();
//        dii.getField().add(theField);
//        toxmlResult.setDocInfo(dii);
//        //可选 结束
//        mashaller.marshal(toxmlResult, new File("c:/outPut.xml"));
//		
//		service.sendCommonMessageByUserCode(nSenderPlatID, nSenderUserCode, sReceiverPlatUserIds,
//				strContent, nOnlineOnly, nReserveDays, strFromApp, strAppCode, strFromUserName, strCustomInfo)

//        String a = "null&;'gname'&;'bz'&;";
//        String[] aa = a.split("&;");
//        for (String aaa : aa) {
//            System.out.println(aaa);
//            System.out.println(StringUtils.isBlank(aaa));
//        }
        // 解析xml文件
        // 1、获取InputStream输入流
        File f = new File("D:\\workspace\\idea\\HamsPort\\docu\\yfg\\meta_file.xml");
        // 4、将docBuilder转换为Document
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(f);
        // 5、获取节点并循环输出节点值
        Element element = doc.getDocumentElement();
        List<Element>  eleList = DomUtils.getChildElementsByTagName(element , "xs:element");
        for (Element ele : eleList) {

//            if(ele.getAttribute("name").equals("业务实体")){
//                String[] argss = {"xs:complexType","xs:sequence","xs:element"};
//                Element ywst = DomUtils.getChildElementByTagName(ele , "xs:complexType");
//                Element seq = DomUtils.getChildElementByTagName(ywst, "xs:sequence");
//                List<Element> eeeeList = getLastChildren(ele , argss);
//
//
//                for (Element element1 : eeeeList) {
//                    System.out.println(element1.getAttribute("name")+ ":" + element1.getAttribute("default"));
//                }
//            }
//
//            if(ele.getAttribute("name").equals("机构人员实体块")){
//                String[] argss = {"xs:complexType","xs:sequence","xs:element"};
//                Element ywst = DomUtils.getChildElementByTagName(ele , "xs:complexType");
//                Element seq = DomUtils.getChildElementByTagName(ywst, "xs:sequence");
//                List<Element> eeeeList = getLastChildren(ele , argss);
//
//
//                for (Element element1 : eeeeList) {
//                    System.out.println(element1.getAttribute("name")+ ":" + element1.getAttribute("default"));
//                }
//            }
//
//            if(ele.getAttribute("name").equals("文件实体块")){
//                String[] argss = {"xs:complexType","xs:sequence","xs:element"};
//                Element ywst = DomUtils.getChildElementByTagName(ele , "xs:complexType");
//                Element seq = DomUtils.getChildElementByTagName(ywst, "xs:sequence");
//                List<Element> eeeeList = getLastChildren(ele , argss);
//
//
//                for (Element element1 : eeeeList) {
//                    System.out.println(element1.getAttribute("name")+ ":" + element1.getAttribute("default"));
//                }
//            }

            if(ele.getAttribute("name").equals("业务实体")){
                String[] argss = {"xs:complexType","xs:sequence","xs:element" };
                Element eee = getLastChild(ele , argss);
                System.out.println(eee.getAttribute("default"));


            }

            if(ele.getAttribute("name").equals("机构人员实体块")){
                String[] argss = {"xs:complexType","xs:sequence","xs:element","xs:element"};
                Element eee = getLastChild(ele , argss);
                System.out.println(eee.getAttribute("default"));
            }

//            if(ele.getAttribute("name").equals("文件实体块")){
//                String[] argss = {"xs:complexType","xs:sequence","xs:element"};
//                List<Element> eeeeList = getLastChildren(ele , argss);
//
//
//                for (Element element1 : eeeeList) {
//                    System.out.println(element1.getAttribute("name")+ ":" + element1.getAttribute("default"));
//                }
//            }


        }
        System.out.println(UUID.randomUUID().toString());

    }

    public static List<Element> getLastChildren(Element ele , String[] ars){
        Element currentEle = ele;
        for (int i = 0; i < ars.length-1; i++) {
            String ar = ars[i];
            Assert.notNull(currentEle, "Element must not be null");
            Element ywst = DomUtils.getChildElementByTagName(currentEle ,ar);
            currentEle = ywst;

        }
        return DomUtils.getChildElementsByTagName(currentEle ,  ars[ars.length-1]);
    }

    //遍历元素
    public static Element getLastChild(Element ele , String[] ars){
        Element currentEle = ele;
        for (int i = 0; i < ars.length; i++) {
            String ar = ars[i];
            Element ywst = DomUtils.getChildElementByTagName(currentEle ,ar);
            currentEle = ywst;
            Assert.notNull(ywst, "Element must not be null");

        }
        return currentEle;
    }
}
