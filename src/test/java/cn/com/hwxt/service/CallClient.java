package cn.com.hwxt.service;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author DaMo
 * @UPDATE 2019/8/15-10:44
 * @since 2019/8/15-10:44
 */
public class CallClient {
    public static void main(String[] args) throws Exception {
        File xml = new File("D:/D_FILE13.XML");
        String xmlStr = FileUtils.readFileToString(xml , "UTF-8");
        System.out.println(xmlStr);
        URL wsdl = new URL("http://localhost:8887/HamsPort/cxf/GdDataWsSingle?wsdl");
        GdDataWs ws = new GdServiceImplService(wsdl).getGdDataWsPort();
        System.out.println(ws.dataReciveXml("D_FILE13",xmlStr,"zhuye"));
//
    }

}
