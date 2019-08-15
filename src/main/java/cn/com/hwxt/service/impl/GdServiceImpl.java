package cn.com.hwxt.service.impl;

import ch.qos.logback.classic.Logger;
import cn.com.hwxt.dao.i.SUserMapper;
import cn.com.hwxt.pojo.jaxb.Field;
import cn.com.hwxt.pojo.jaxb.Table;
import cn.com.hwxt.service.BaseService;
import cn.com.hwxt.service.i.GdService;
import cn.com.hwxt.util.GlobalFinalAttr;
import cn.com.hwxt.util.XmlObjUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.io.File;
import java.util.List;
import java.util.Map;

@Service("GdDataService")
@WebService(name = "GdDataWs", targetNamespace = "http://service.hwxt.com.cn/")
public class GdServiceImpl extends BaseService implements GdService {
    private String rsltMsg = "<?xml version='1.0' encoding='UTF-8'?><rslt><flag value='#DID#'/><msg value='#MSG#'/></rslt>";

    @WebMethod
    public String dataReciveXml(@WebParam(name = "xmlName") String xmlName
            , @WebParam(name = "dataXml") String dataXml, @WebParam(name = "gdrCode") String gdrCode) {
        Integer did = -1;
        String rsltStr = "";
        try {
            Table table = XmlObjUtil.xml2Obj(dataXml, Table.class);
            List<Field> xmlfields = table.getFields();// 读取xml配置信息获取要写入的各个字段
            did = super.gdData(xmlfields , null , xmlName , gdrCode);
            rsltStr = rsltMsg.replace("#DID#" , did.toString());
            if(did > -1){
                rsltStr = rsltStr.replace("#MSG#" , "");
            }else{
                rsltStr = rsltStr.replace("#MSG#" , "未捕获，请查看日志。");
            }
        } catch (Exception e) {
            log.error(e.getMessage() , e);
            rsltStr = rsltMsg.replace("#DID#" , "-1").replace("#MSG#" , e.getMessage());
        }
        return rsltStr;
    }

    @WebMethod
    public String dataReciveJson(@WebParam(name = "xmlName") String xmlName
            , @WebParam(name = "dataJson") String dataJson, @WebParam(name = "gdrCode") String gdrCode) {
        Integer did = -1;
        String rsltStr = "";
        try {
            String xmlPath = xmlName.toUpperCase().contains(".XML") ?
                    GlobalFinalAttr.XML_PATH + xmlName : GlobalFinalAttr.XML_PATH + xmlName + ".XML";
            File xmlFile = new File(xmlPath);
            String xmlString = FileUtils.readFileToString(xmlFile, GlobalFinalAttr.PRJ_CHAR_CODE);
            Table table = XmlObjUtil.xml2Obj(xmlString, Table.class);
            List<Field> xmlfields = table.getFields();// 读取xml配置信息获取要写入的各个字段

            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> vars = null;
            vars = mapper.readValue(dataJson, Map.class);

            did = super.gdData(xmlfields , vars , xmlName , gdrCode);
            rsltStr = rsltMsg.replace("#DID#" , did.toString());
            if(did > -1){
                rsltStr = rsltStr.replace("#MSG#" , "");
            }else{
                rsltStr = rsltStr.replace("#MSG#" , "未捕获，请查看日志。");
            }
        } catch (Exception e) {
            log.error(e.getMessage() , e);
            rsltStr = rsltMsg.replace("#DID#" , "-1").replace("#MSG#" , e.getMessage());
        }
        return rsltStr;
    }


    @WebMethod
    public Integer fileReciveXml(@WebParam(name = "xmlName") String xmlName
            , @WebParam(name = "dataXml") String dataXml, @WebParam(name = "gdrCode") String gdrCode) {
        Integer did = -1;
        try {
            Table table = XmlObjUtil.xml2Obj(dataXml, Table.class);
            List<Field> xmlfields = table.getFields();// 读取xml配置信息获取要写入的各个字段
            did = super.gdData(xmlfields , null , xmlName , gdrCode);
        } catch (Exception e) {
            log.error("插入一条数据错误.fileReciveTxt:", e);
            did = -1;
        }
        return did;
    }

    @WebMethod
    public Integer fileReciveJson(@WebParam(name = "xmlName") String xmlName
            , @WebParam(name = "dataJson") String dataJson, @WebParam(name = "gdrCode") String gdrCode) {
        Integer did = -1;
        try {
            String xmlPath = xmlName.toUpperCase().contains(".XML") ?
                    GlobalFinalAttr.XML_PATH + xmlName : GlobalFinalAttr.XML_PATH + xmlName + ".XML";
            File xmlFile = new File(xmlPath);
            String xmlString = FileUtils.readFileToString(xmlFile, GlobalFinalAttr.PRJ_CHAR_CODE);
            Table table = XmlObjUtil.xml2Obj(xmlString, Table.class);
            List<Field> xmlfields = table.getFields();// 读取xml配置信息获取要写入的各个字段

            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> vars = null;
            vars = mapper.readValue(dataJson, Map.class);

            did = super.gdData(xmlfields , vars , xmlName , gdrCode);
        } catch (Exception e) {
            log.error(e.getMessage() , e);
            did = -1;
        }
        return did;
    }

    @Autowired
    private SUserMapper sUserMapper;
    private Logger log = (Logger) LoggerFactory.getLogger(this.getClass());
}