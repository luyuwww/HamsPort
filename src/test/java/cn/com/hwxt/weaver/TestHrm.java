package cn.com.hwxt.weaver;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import weaver.hrm.HrmService;
import weaver.hrm.HrmServicePortType;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author DaMo
 * @UPDATE 2019/10/21-22:53
 * @since 2019/10/21-22:53
 */

public class TestHrm {

    /**
     * 测试hrm接口是否通
     */
    @Test
    public  void testCase(){
        HrmServicePortType hrmServicePortType = null;
        try {
            URL url = new URL("http://oa.szcgc.com:18088/services/HrmService?wsdl");

            hrmServicePortType = new HrmService(url).getHrmServiceHttpPort();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        String xml = hrmServicePortType.getHrmSubcompanyInfoXML("10.8.5.52");
        System.out.println(xml);
    }
}
