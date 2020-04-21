package cn.com.hwxt.util;

import cn.com.hwxt.pojo.EcidiEepOrgPerson;
import cn.com.hwxt.pojo.EcidiSimpleEEP;
import cn.com.hwxt.pojo.EcidiSimpleFile;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ParseBimXmlUtil {
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
        String basePht = "D:\\temp\\yfg\\yfg-15393e9fe0a4828982a1dce82d29e581\\";
        EcidiSimpleEEP ecidiEEP = parseBimEEP(f);
        for (EcidiSimpleFile ecidiSimpleFile : ecidiEEP.getFileList()) {
            File att = new File(basePht+ecidiSimpleFile.getFileBizName());
            String md5 = MD5.getFileMD5(att);
            if(!md5.equals(ecidiSimpleFile.getMd5())){
                throw new RuntimeException("数字摘要值验证错误:"+ecidiSimpleFile.getFileBizName());
            }


        }
        System.out.println();

//        // 4、将docBuilder转换为Document
//        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(f);
//        // 5、获取节点并循环输出节点值
//        Element element = doc.getDocumentElement();
//        List<Element>  eleList = DomUtils.getChildElementsByTagName(element , "xs:element");
//
//        String[] argss = {"xs:complexType","xs:sequence","xs:element"};

//        for (Element ele : eleList) {
//
//            if(ele.getAttribute("name").equals("业务实体")){
//
//                List<Element> eeeeList = getLastChildren(ele , argss);
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

//            System.out.println("==============================");
////            String[] argss = {"xs:complexType","xs:sequence","xs:element" };
////            if(ele.getAttribute("name").equals("业务实体")){
////
////                List<Element> eeeeList = getLastChildren(ele , argss);
////                for (Element eee : eeeeList) {
////                    System.out.println(eee.getAttribute("name")+":"+eee.getAttribute("default"));
////
////                }
////
////
////            }
////            System.out.println("==============================");
////
////            if(ele.getAttribute("name").equals("机构人员实体块")){
////                String[] innerargs = {"xs:element" };
////                List<Element> eeeeList = getLastChildren(ele , argss);
////                for (Element eee : eeeeList) {
////                    //
////                    List<Element> innerList = getLastChildren(eee , innerargs);
////                    for (Element element1 : innerList) {
////                        System.out.println(eee.getAttribute("name")+":"+element1.getAttribute("name")+":"+element1.getAttribute("default"));
////                    }
////
////
////                }
////            }
////            System.out.println("==============================");
////            if(ele.getAttribute("name").equals("文件实体块")){
////                List<Element> eeeeList = getLastChildren(ele , argss);
////                System.out.println(eeeeList.size());
////                for (int i = 0; i < eeeeList.size(); i=i+2) {
////                    Element fileBean = eeeeList.get(i);
////                    Element fileRelation = eeeeList.get(i+1);
////                    System.out.print(fileBean.getAttribute("name")+":"+fileBean.getAttribute("default"));
////                    System.out.println(fileRelation.getAttribute("name")+":"+fileRelation.getAttribute("default"));
////                    System.out.println("--文件实体---");
////                    List<Element> fileInfo =  getLastChildren(fileBean , argss);
////                    for (Element aaa : fileInfo) {
////                        System.out.println(aaa.getAttribute("name")+":"+aaa.getAttribute("default"));
////                    }
////                    System.out.println();
////                }
////
////            }
////
////
//        }

    }

    public static EcidiSimpleEEP parseBimEEP(File f) throws Exception {
        //固定复杂结构
        String[] innerargs = {"xs:element"};
        String[] argss = {"xs:complexType", "xs:sequence", "xs:element"};
        EcidiSimpleEEP ecidiEEP = new EcidiSimpleEEP();
        try {
            // 4、将docBuilder转换为Document
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(f);
            // 5、获取节点并循环输出节点值
            Element element = doc.getDocumentElement();
            List<Element> eleList = DomUtils.getChildElementsByTagName(element, "xs:element");
            for (Element ele : eleList) {
                if (ele.getAttribute("name").equals("封装包格式描述")) {
                    ecidiEEP.setPackageDescription(ele.getAttribute("default"));
                } else if (ele.getAttribute("name").equals("版本")) {
                    ecidiEEP.setVersion(ele.getAttribute("fixed"));
                } else if (ele.getAttribute("name").equals("数据来源")) {
                    ecidiEEP.setSource(ele.getAttribute("default"));
                } else if (ele.getAttribute("name").equals("封装包创建时间")) {
                    String cDate = ele.getAttribute("default");
                    if (StringUtils.isNotBlank(cDate)) {
                        ecidiEEP.setCreateTime(DateUtil.strToDate(cDate, "yyyy-MM-dd hh:mm:ss"));
                    }
                } else if (ele.getAttribute("name").equals("服务器 IP")) {
                    ecidiEEP.setServerIP(ele.getAttribute("default"));
                } else if (ele.getAttribute("name").equals("服务器 MAC")) {
                    ecidiEEP.setServerMAC(ele.getAttribute("default"));
                } else if (ele.getAttribute("name").equals("客户端 IP")) {
                    ecidiEEP.setClientIP(ele.getAttribute("default"));
                } else if (ele.getAttribute("name").equals("封装包类型")) {
                    ecidiEEP.setPackageType(ele.getAttribute("default"));
                } else if (ele.getAttribute("name").equals("封装包类型描述")) {
                    ecidiEEP.setPackageTypeDescription(ele.getAttribute("default"));
                } else if (ele.getAttribute("name").equals("业务实体")) {
                    List<Element> bizEntityList = getLastChildren(ele, argss);
                    if (bizEntityList.size() > 0) {
                        Map<String, Object> bizEntity = new HashMap<>();
                        for (Element item : bizEntityList) {
                            bizEntity.put(item.getAttribute("name"), item.getAttribute("default"));
                        }
                        ecidiEEP.setBizEntity(bizEntity);
                    }

                } else if (ele.getAttribute("name").equals("机构人员实体块")) {
                    List<Element> orgPersonList = getLastChildren(ele, argss);
                    if (orgPersonList.size() > 0) {
                        Integer orgOrder = 0;
                        List<EcidiEepOrgPerson> eeopList = new ArrayList<>();
                        for (Element orgPerson : orgPersonList) {
                            List<Element> innerList = getLastChildren(orgPerson, innerargs);
                            EcidiEepOrgPerson eop = new EcidiEepOrgPerson();
                            eop.setOpName(orgPerson.getAttribute("name"));
                            eop.setOrderNum(orgOrder++);
                            eop.setId(innerList.get(0).getAttribute("default"));
                            eop.setName(innerList.get(1).getAttribute("default"));
                            eop.setOrgName(innerList.get(2).getAttribute("default"));
                            eeopList.add(eop);
                        }
                        ecidiEEP.setOrgPersonList(eeopList);
                    }

                } else if (ele.getAttribute("name").equals("文件实体块")) {
                    List<Element> fileRelationList = getLastChildren(ele, argss);
                    if (fileRelationList.size() > 0) {
                        Integer frOrder = 0;
                        List<EcidiSimpleFile> fileList = new ArrayList<>();
                        for (int i = 0; i < fileRelationList.size(); i = i + 2) {
                            EcidiSimpleFile theFile = new EcidiSimpleFile();
                            theFile.setOrderNum(frOrder++);
                            Element fileBean = fileRelationList.get(i);
                            Element fileRelation = fileRelationList.get(i + 1);
                            List<Element> fileInfoList = getLastChildren(fileBean, argss);
                            for (Element fileInfo : fileInfoList) {
                                if (fileInfo.getAttribute("name").equals("标题")) {
                                    theFile.setTitle(fileInfo.getAttribute("default"));
                                } else if (fileInfo.getAttribute("name").equals("名称(文件名),包含扩展名")) {
                                    theFile.setFileBizName(fileInfo.getAttribute("default"));
                                } else if (fileInfo.getAttribute("name").equals("类型")) {
                                    theFile.setExt(fileInfo.getAttribute("default"));
                                } else if (fileInfo.getAttribute("name").equals("数字摘要值")) {
                                    theFile.setMd5(fileInfo.getAttribute("default"));
                                } else if (fileInfo.getAttribute("name").equals("创建时间")) {
                                    String cDate = fileInfo.getAttribute("default");
                                    if (StringUtils.isNotBlank(cDate)) {
                                        theFile.setCreateTime(DateUtil.strToDate(cDate, "yyyy-MM-dd hh:mm:ss"));
                                    }
                                } else if (fileInfo.getAttribute("name").equals("大小")) {
                                    theFile.setSize(fileInfo.getAttribute("default"));
                                }
                            }
                            List<Element> relationList = getLastChildren(fileRelation, argss);
                            for (Element rsInfo : relationList) {
                                if (rsInfo.getAttribute("name").equals("关系类型")) {
                                    theFile.setRelationType(rsInfo.getAttribute("default"));
                                } else if (rsInfo.getAttribute("name").equals("关系描述")) {
                                    theFile.setRelateionDescription(rsInfo.getAttribute("default"));
                                }
                            }
                            //先加朱文件再加附件
                            fileList.add(theFile);
                            for (Element fileInfo : fileInfoList) {
                                if (fileInfo.getAttribute("name").equals("表单信息")) {
                                    List<Element> formList = getLastChildren(fileInfo, argss);
                                    for (Element formItem : formList) {
                                        if (formItem.getAttribute("name").equals("表单附件信息")) {
                                            List<Element> bdfj = getLastChildren(formItem, innerargs);
                                            if (bdfj.size() > 0) {
                                                for (Element aAttEle : bdfj) {
                                                    EcidiSimpleFile attachment = new EcidiSimpleFile();
                                                    attachment.setOrderNum(frOrder++);
                                                    attachment.setAttachemnt(true);
                                                    List<Element> attElements = getLastChildren(aAttEle, innerargs);
                                                    for (Element attElement : attElements) {
                                                        if (attElement.getAttribute("name").equals("标题")) {
                                                            attachment.setTitle(attElement.getAttribute("default"));
                                                        } else if (attElement.getAttribute("name").equals("名称(文件名),包含扩展名")) {
                                                            attachment.setFileBizName(attElement.getAttribute("default"));
                                                        } else if (attElement.getAttribute("name").equals("类型")) {
                                                            attachment.setExt(attElement.getAttribute("default"));
                                                        } else if (attElement.getAttribute("name").equals("数字摘要值")) {
                                                            attachment.setMd5(attElement.getAttribute("default"));
                                                        } else if (attElement.getAttribute("name").equals("创建时间")) {
                                                            String cDate = attElement.getAttribute("default");
                                                            if (StringUtils.isNotBlank(cDate)) {
                                                                attachment.setCreateTime(DateUtil.strToDate(cDate, "yyyy-MM-dd hh:mm:ss"));
                                                            }
                                                        } else if (attElement.getAttribute("name").equals("大小")) {
                                                            attachment.setSize(attElement.getAttribute("default"));
                                                        }
                                                    }
                                                    fileList.add(attachment);
                                                }
                                            }

                                        }

                                    }

                                }
                            }

                        }
                        ecidiEEP.setFileList(fileList);
                    }
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return ecidiEEP;
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
