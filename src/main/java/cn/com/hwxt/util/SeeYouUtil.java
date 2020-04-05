package cn.com.hwxt.util;

import ch.qos.logback.classic.Logger;
import cn.com.hwxt.pojo.HttpClientResult;
import com.seeyou.pojo.Department;
import com.seeyou.pojo.Member;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author DaMo
 * @UPDATE 2020/4/5-22:59
 * @since 2020/4/5-22:59
 */
public class SeeYouUtil {
    public static Map<String ,Object> json2Map(String json){
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> vars = null;
        try {
            vars = mapper.readValue(json, Map.class);
        } catch (IOException e) {
            vars = new HashMap<>();
            e.printStackTrace();
        }
        return vars;
    }

    public static String getValidateStr(String ip ,String appKey , String appPassword){
        String validateStr = "";
        String url = "http://"+ip+"/seeyon/rest/token/"+appKey +"/"+appPassword+"?"+Math.random();
        System.out.println(url);
        HttpClientResult rslt =  null;
        try {
            rslt = HttpClientUtils.doGet(url);
        } catch (Exception e) {
            validateStr = "";
            log.error(e.getMessage() , e);
        }
        if(null != rslt && rslt.getCode() == 200){
            Map<String ,Object> map = json2Map(rslt.getContent());
            validateStr = MapUtils.getString(map , "id");
        }
       return validateStr;
    }

    public static String getMemberXml(String ip , String unitName , String validate){
        HttpClientResult rslt =  null;
        String url =  "http://"+ip+"/seeyon/rest/data/members/"+ URLEncoder.encode(unitName ) +"?token="+validate+"&"+Math.random();
        System.out.println(url);
        try {
            rslt = HttpClientUtils.doGet(url);
        } catch (Exception e) {
            log.error(e.getMessage() , e);
        }
        if(null != rslt && rslt.getCode() == 200){
            return rslt.getContent();
        }else{
            return "";
        }
    }


    public static String getDeptXml(String ip , String unitName , String validate){
        HttpClientResult rslt =  null;
        String url =  "http://"+ip+"/seeyon/rest/data/departments/"+ URLEncoder.encode(unitName) +"?token="+validate+"&"+Math.random();
        System.out.println(url);
        try {
            rslt = HttpClientUtils.doGet(url);
        } catch (Exception e) {
            log.error(e.getMessage() , e);
        }
        if(null != rslt && rslt.getCode() == 200){
            return rslt.getContent();
        }else{
            return "";
        }
    }

    public static String getMemberXml(String ip , String unitName , String appKey , String appPassword){
        HttpClientResult rslt =  null;
        try {
            String validate = getValidateStr(ip , appKey , appPassword);
            if(StringUtils.isBlank(validate)){
                throw new RuntimeException("get SeeyouValidate error");
            }
            String url =  "http://"+ip+"/seeyon/rest/data/members/"+ URLEncoder.encode(unitName ) +"?token="+validate+"&"+Math.random();
            System.out.println(url);
            rslt = HttpClientUtils.doGet(url);
        } catch (Exception e) {
            log.error(e.getMessage() , e);
        }
        if(null != rslt && rslt.getCode() == 200){
            return rslt.getContent();
        }else{
            return "";
        }
    }


    public static String getDeptXml(String ip , String unitName , String appKey , String appPassword){
        HttpClientResult rslt =  null;
        try {
            String validate = getValidateStr(ip , appKey , appPassword);
            if(StringUtils.isBlank(validate)){
                throw new RuntimeException("get SeeyouValidate error");
            }
            String url =  "http://"+ip+"/seeyon/rest/data/departments/"+ URLEncoder.encode(unitName) +"?token="+validate+"&"+Math.random();
            System.out.println(url);
            rslt = HttpClientUtils.doGet(url);
        } catch (Exception e) {
            log.error(e.getMessage() , e);
        }
        if(null != rslt && rslt.getCode() == 200){
            return rslt.getContent();
        }else{
            return "";
        }
    }

    //TODO 完成xml到部门的转换
    public static List<Department> convert2Dept(String xml){
        return null;
    }

    //TODO 完成xml到用户的转换
    public static List<Member> convert2User(String xml){
        return null;
    }

    private static  Logger log = (Logger) LoggerFactory.getLogger(SeeYouUtil.class);
}

