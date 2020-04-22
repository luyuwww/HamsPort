package cn.com.hwxt.util;

import ch.qos.logback.classic.Logger;
import cn.com.hwxt.pojo.HttpClientResult;
import com.seeyou.pojo.DataPojo;
import com.seeyou.pojo.Department;
import com.seeyou.pojo.Member;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
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
            String url =  "http://"+ip+"/seeyon/rest/data/members/"+ unitName +"?token="+validate+"&"+Math.random();
            System.out.println(url);
            rslt = HttpClientUtils.doGet(url);
        } catch (Exception e) {
            log.error(e.getMessage() , e);
        }
        if(null != rslt && rslt.getCode() == 200){
            return rslt.getContent();
        }else{
            log.error(rslt.toString());
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
            String url =  "http://"+ip+"/seeyon/rest/data/departments/"+ unitName +"?token="+validate+"&"+Math.random();
            System.out.println(url);
            rslt = HttpClientUtils.doGet(url);
        } catch (Exception e) {
            log.error(e.getMessage() , e);
        }
        if(null != rslt && rslt.getCode() == 200){
            return rslt.getContent();
        }else{
            log.error(rslt.toString());
            return "";
        }
    }

    //xml到部门的转换
    public static List<Department> convert2Dept(String xml){
        List<Department> rslt = new ArrayList<>();
        DataPojo dataPojoResult = null;
        if(StringUtils.isBlank(xml)){
            return rslt;
        }
        Reader reader = null;
        try {
            reader = new StringReader(xml);
            Unmarshaller unmarshaller = JAXBContext.newInstance(DataPojo.class).createUnmarshaller();
            dataPojoResult = (DataPojo) unmarshaller.unmarshal(reader);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        if( dataPojoResult != null){
            List<DataPojo.DataProperty.DataPojo1> oaDeptList = dataPojoResult.getDataProperty().getDataPojo();
            for (DataPojo.DataProperty.DataPojo1 dataPojo1 : oaDeptList) {
                List< DataPojo.DataProperty.DataPojo1.DataProperty1> oneObject =  dataPojo1.getDataProperty();
                Department dept = new Department();
                dept.setDepSort(getValue(oneObject , "dep_sort"));
                dept.setDiscursion(getValue(oneObject , "discursion"));
                dept.setDepartmentNumber(getValue(oneObject , "departmentNumber"));
                dept.setAccountId(getValue(oneObject , "accountId"));
                dept.setDepartmentName(getDepartmentList(oneObject , "departmentName"));
                if( dept.getDepartmentName().size() > 0){
                    dept.setName(dept.getDepartmentName().get(dept.getDepartmentName().size()-1));
                    if(dept.getDepartmentName().size() > 1){
                        dept.setParentName(dept.getDepartmentName().get(dept.getDepartmentName().size()-2));
                    }
                }
                rslt.add(dept);

            }
        }
        return rslt;
    }

    //xml到用户的转换
    public static List<Member> convert2User(String xml){
        List<Member> rslt = new ArrayList<>();
        DataPojo dataPojoResult = null;
        if(StringUtils.isBlank(xml)){
            return rslt;
        }
        Reader reader = null;
        try {
            reader = new StringReader(xml);
            Unmarshaller unmarshaller = JAXBContext.newInstance(DataPojo.class).createUnmarshaller();
            dataPojoResult = (DataPojo) unmarshaller.unmarshal(reader);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        if( dataPojoResult != null){
            List<DataPojo.DataProperty.DataPojo1> oaDeptList = dataPojoResult.getDataProperty().getDataPojo();
            for (DataPojo.DataProperty.DataPojo1 dataPojo1 : oaDeptList) {
                List< DataPojo.DataProperty.DataPojo1.DataProperty1> oneObject =  dataPojo1.getDataProperty();
                Member member = new Member();

                member.setTrueName(getValue(oneObject , "trueName"));
                member.setLoginName(getValue(oneObject , "loginName"));
                member.setStaffNumber(getValue(oneObject , "staffNumber"));
                member.setEmail(getValue(oneObject , "email"));
                member.setPerSort(getValue(oneObject , "per_sort"));
                member.setId(getValue(oneObject , "id"));
                member.setDiscursion(getValue(oneObject , "discursion"));


                member .setDepartmentName(getDepartmentList(oneObject , "departmentName"));

                if( member.getDepartmentName().size() > 0){
                    member.setPname(member.getDepartmentName().get(member.getDepartmentName().size()-1));
                }
                rslt.add(member);
            }
        }
        return rslt;
    }

    private static String getValue(List<DataPojo.DataProperty.DataPojo1.DataProperty1> dataProperty , String key){
        String value = "";
        for (DataPojo.DataProperty.DataPojo1.DataProperty1 prop : dataProperty) {
            if(prop.getPropertyname().equals(key)){
                value = prop.getValue();
                if (StringUtils.isBlank(value) && prop.getContent().size() > 0) {
                    value = prop.getContent().get(0).toString();
                }
                break;
            }

        }
        return value;
    }
    private static List<String> getDepartmentList(List<DataPojo.DataProperty.DataPojo1.DataProperty1> dataProperty  , String key){
        List<String> nameList = new ArrayList<>();
        for (DataPojo.DataProperty.DataPojo1.DataProperty1 dp : dataProperty) {
            if(dp.getPropertyname().equals(key) && dp.getContent().size()>0 ){
                List<Serializable> depNames = dp.getContent();
                for (Serializable depName : depNames) {
                    if(depName instanceof JAXBElement){
                        JAXBElement ojbect = (JAXBElement) depName;
                        String tValue = ((com.seeyou.pojo.DataPojo.DataProperty.DataPojo1.DataProperty1.DataValue)ojbect.getValue()).getValue();
                        if(StringUtils.isNotBlank(tValue)){
                            nameList.add(tValue);
                        }
                    }
                }
            }
        }
        return nameList;
    }

    public static String jdugeSSO(String ip , String token){
        String name = "";
        String url = "http://"+ip+ "/seeyon/thirdpartyController.do?token="+token+"&radom="+Math.random();
        System.out.println(url);
        HttpClientResult rslt = null;
        try {
            rslt = HttpClientUtils.doGet(url);
            if(rslt.getCode() == 200){
                name = rslt.getContent();
            }
        } catch (Exception e) {
            name= "";
            e.printStackTrace();
        }
        return name;
    }
    private static  Logger log = (Logger) LoggerFactory.getLogger(SeeYouUtil.class);
}

