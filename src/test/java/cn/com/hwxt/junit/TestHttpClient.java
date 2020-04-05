package cn.com.hwxt.junit;

import cn.com.hwxt.pojo.HttpClientResult;
import cn.com.hwxt.util.HttpClientUtils;
import cn.com.hwxt.util.SeeYouUtil;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.net.URLEncoder;

/**
 * @author DaMo
 * @UPDATE 2020/4/5-22:35
 * @since 2020/4/5-22:35
 */
public class TestHttpClient {
    public static String key = "daapi";
    public static String appPassWord = "1ytkDSyYDyf2H3wy2B2oOtfz";
    public static String unitName = "贵州铝厂有限责任公司";
    public static String ip = "222.85.147.231:8889";

    @Test
    /**
     * 测试获取验证码
     */
    public void testGetValidateStr() throws Exception {
        String ip = "http://222.85.147.231:8889/seeyon/rest/token/";
        String url = ip+key +"/"+appPassWord;
        System.out.println(url);
        HttpClientResult rslt =  HttpClientUtils.doGet(url);
        System.out.println(rslt);
        FileUtils.writeStringToFile(new File("d:/aa.validate.xml") , rslt.toString());
    }
    @Test
    /**
     * 测试获取验证码 通过SeeyouUtil方法
     */
    public void testGetValidateStrBySeeYouUtil() throws Exception {

        String url = ip+key +"/"+appPassWord;
        System.out.println(SeeYouUtil.getValidateStr(ip , key ,appPassWord));
    }

    @Test
    /**
     * 测试获取所有部门
     */
    public void testGetMember() throws Exception {
        String ip = "http://222.85.147.231:8889/seeyon/rest/data/members/";
        String url = ip+ URLEncoder.encode(unitName ) +"?token="+"b17525ae-c3af-47c4-b386-2ee06fe9972a";
        System.out.println(url);
        HttpClientResult rslt =  HttpClientUtils.doGet(url);
        System.out.println(rslt);

        FileUtils.writeStringToFile(new File("d:/aa.member.xml") , rslt.toString());

    }
    @Test
    /**
     * 测试获取所有部门
     */
    public void testGetMemberBySeeyouUtil() throws Exception {
        System.out.println(SeeYouUtil.getMemberXml(ip , unitName ,  key ,  appPassWord));
    }
    @Test
    /**
     * 测试获取所有用户
     */
    public void testGetDep() throws Exception {
        String ip = "http://222.85.147.231:8889/seeyon/rest/data/departments/";
        String url = ip+ URLEncoder.encode(unitName ) +"?token="+"bec9cbc9-9ebb-465d-92d9-3af44dc02384";
        System.out.println(url);
        HttpClientResult rslt =  HttpClientUtils.doGet(url);
        System.out.println(rslt);
        FileUtils.writeStringToFile(new File("d:/aa.Dept.xml") , rslt.toString());
    }

    @Test
    /**
     * 测试获取所有用户
     */
    public void testGetDepBySeeYouUtil() throws Exception {
        System.out.println(SeeYouUtil.getDeptXml(ip , unitName ,  key ,  appPassWord));
    }

    @Test
    /**
     * 测试单点
     */
    public void testSSO() throws Exception {
        String ip = "http://222.85.147.231:8889/seeyon/thirdpartyController.do";
        String url = ip+ "?token="+"c31e5391-19a6-44b4-a7db-09177085e604";
        System.out.println(url);
        HttpClientResult rslt =  HttpClientUtils.doGet(url);
        System.out.println(rslt);
    }


}
