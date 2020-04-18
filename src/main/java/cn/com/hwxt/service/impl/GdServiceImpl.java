package cn.com.hwxt.service.impl;

import ch.qos.logback.classic.Logger;
import cn.com.hwxt.dao.i.SUserMapper;
import cn.com.hwxt.pojo.SBizEepQueue;
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
@WebService(name = "GdDataWs", targetNamespace = "http://unisra.www.com/")
public class GdServiceImpl extends BaseService implements GdService {
    //            , @WebParam(name = "levelStr") String levelStr , @WebParam(name = "libcode") Integer libcode,@WebParam(name = "pzm") String pzm
    private String rsltMsg = "<?xml version='1.0' encoding='UTF-8'?><rslt><flag value='#FLAG#'/><msg value='#MSG#'/></rslt>";
    private String rsltJson ="{\"DID\":\"#DID#\",\"MSG\":\"#MSG#\"}";
    @WebMethod
    public String dataReciveXml(@WebParam(name = "appKey") String appKey  , @WebParam(name = "dataXml") String dataXml
            , @WebParam(name = "pk") String pk , @WebParam(name = "packageMd5") String md5){
        log.error("appKey:"+appKey);
        log.error("dataXml:"+dataXml);
        log.error("pk:"+pk);
        log.error("md5:"+md5);
        String rsltStr = "";

        if(appKey.split("-").length <4){
            throw new RuntimeException("appkey format error");
        }
        String levelStr = appKey.split("-")[1];
        Integer libcode = Integer.parseInt( appKey.split("_")[2]);
        String pzm = appKey.split("-")[3];

        SBizEepQueue beq = new SBizEepQueue(appKey, pk, dataXml , md5 , levelStr,  libcode , pzm);
        try {
            sUserMapper.insertSbizEepQueue(beq);
            rsltStr = rsltMsg.replace("#FLAG#" , "true");
            rsltStr = rsltStr.replace("#MSG#" , "");
        } catch (Exception e) {
            rsltStr = rsltMsg.replace("#FLAG#" , "false");
            rsltStr = rsltStr.replace("#MSG#" , e.getMessage());
            e.printStackTrace();
        }
        return rsltStr;
    }

    @WebMethod
    public String dataReciveJson(@WebParam(name = "appKey") String appKey , @WebParam(name = "dataJson") String dataJson
            , @WebParam(name = "pk") String pk , @WebParam(name = "md5") String md5) {
        log.error("appKey:"+appKey);
        log.error("dataJson:"+dataJson);
        log.error("pk:"+pk);
        log.error("md5:"+md5);
        String rsltStr = "";
        if(appKey.split("-").length <4){
            throw new RuntimeException("appkey format error");
        }

        String levelStr = appKey.split("-")[1];
        Integer libcode = Integer.parseInt( appKey.split("-")[2]);
        String pzm = appKey.split("-")[3];

        SBizEepQueue beq = new SBizEepQueue(appKey, pk, dataJson , md5 , levelStr,  libcode , pzm);
        try {
            sUserMapper.insertSbizEepQueue(beq);
            rsltStr = rsltMsg.replace("#FLAG#" , "true");
            rsltStr = rsltStr.replace("#MSG#" , "");
        } catch (Exception e) {
            rsltStr = rsltMsg.replace("#FLAG#" , "false");
            rsltStr = rsltStr.replace("#MSG#" , e.getMessage());
            e.printStackTrace();
        }
        return rsltStr;
    }
    @Autowired
    private SUserMapper sUserMapper;
    private Logger log = (Logger) LoggerFactory.getLogger(this.getClass());
}