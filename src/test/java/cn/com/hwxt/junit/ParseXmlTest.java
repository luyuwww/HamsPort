package cn.com.hwxt.junit;

import com.seeyou.pojo.DataPojo;
import com.sun.corba.se.impl.resolver.ORBDefaultInitRefResolverImpl;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;


public class ParseXmlTest {
    public static void main(String[] args) throws Exception {
        File file = new File("D:\\workspace\\idea\\HamsPort\\docu\\seeyou\\dep_person\\departments.xml");
        String xmlStr = FileUtils.readFileToString(file, "UTF-8");
//        System.out.println(xmlStr);
        Reader reader = new StringReader(xmlStr);
        Unmarshaller unmarshaller = JAXBContext.newInstance(DataPojo.class).createUnmarshaller();
        DataPojo result = (DataPojo) unmarshaller.unmarshal(reader);
        List<DataPojo.DataProperty.DataPojo1> array = result.getDataProperty().getDataPojo();
        System.out.println(array.size());
        for (DataPojo.DataProperty.DataPojo1 dataPojo1 : array) {
            List<DataPojo.DataProperty.DataPojo1.DataProperty1> oneObject = dataPojo1.getDataProperty();
//            System.out.println(getValue(oneObject , "accountId"));
            for (DataPojo.DataProperty.DataPojo1.DataProperty1 dp : oneObject) {
                List<String> nameList = new ArrayList<>();
                if (dp.getPropertyname().equals("departmentName") && dp.getContent().size() > 0) {
                    List<Serializable> depNames = dp.getContent();
                    for (Serializable depName : depNames) {
                        if (depName instanceof JAXBElement) {

                            JAXBElement ojbect = (JAXBElement) depName;
                            String value = ((com.seeyou.pojo.DataPojo.DataProperty.DataPojo1.DataProperty1.DataValue) ojbect.getValue()).getValue();
                            if (StringUtils.isNotBlank(value)) {
                                nameList.add(value);
                            }

                        }
                    }
                    if (depNames.size() > 0) {

                        System.out.println(nameList.get(nameList.size() - 1));
                    } else {
                        System.out.println(depNames.size());
                    }

                }
            }
        }
    }

//    public static void main(String[] args) throws Exception {
//        File file = new File("D:\\workspace\\idea\\HamsPort\\docu\\seeyou\\dep_person\\members.xml");
//        String xmlStr = FileUtils.readFileToString(file , "UTF-8");
//        System.out.println(xmlStr);
//        Reader reader = new StringReader(xmlStr);
//        Unmarshaller unmarshaller =  JAXBContext.newInstance(DataPojo.class).createUnmarshaller();
//        DataPojo result = (DataPojo) unmarshaller.unmarshal(reader);
//        List<DataPojo.DataProperty.DataPojo1> array = result.getDataProperty().getDataPojo();
//        System.out.println(array.size());
//        for (DataPojo.DataProperty.DataPojo1 dataPojo1 : array) {
//            List< DataPojo.DataProperty.DataPojo1.DataProperty1> oneObject =  dataPojo1.getDataProperty();
//            for (DataPojo.DataProperty.DataPojo1.DataProperty1 dp : oneObject) {
//                if(dp.getPropertyname().equals("departmentName") && dp.getContent().size()>0){
//                    List<Serializable> depNames = dp.getContent();
//                    for (Serializable depName : depNames) {
//                        if(depName instanceof JAXBElement){
//
//                            JAXBElement ojbect = (JAXBElement) depName;
//                            System.out.print(((com.seeyou.pojo.DataPojo.DataProperty.DataPojo1.DataProperty1.DataValue)ojbect.getValue()).getValue()+ "\t");
//                        }
//
//
//                    }
//                    System.out.println();
//                }
//
//
//            }
//
//        }

    private static String getValue(List<DataPojo.DataProperty.DataPojo1.DataProperty1> dataProperty, String key) {
        String value = "";
        for (DataPojo.DataProperty.DataPojo1.DataProperty1 prop : dataProperty) {
            if (prop.getPropertyname().equals(key)) {
                value = prop.getValue();
                if (StringUtils.isBlank(value) && prop.getContent().size() > 0) {
                    value = prop.getContent().get(0).toString();
                }
                break;
            }

        }
        return value;
    }

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

//        D:\workspace\idea\HamsPort\docu\seeyou\dep_person
//    }
}
