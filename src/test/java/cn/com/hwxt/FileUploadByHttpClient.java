package cn.com.hwxt;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.nio.charset.Charset;

/**
 * @author DaMo  java上传电子文件
 * @UPDATE 2019-08-03-11:43
 * @since 2019-08-03-11:43
 */
public class FileUploadByHttpClient {
    public static void main(String[] args) throws Exception {
        String fileStr = "d:/Core Java笔记MS Word版.doc";
        File ff = new File(fileStr);
        ContentType utf8 = ContentType.create("text/plain", Charset.forName("UTF-8"));
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httppost = new HttpPost("http://localhost:8887/HamsPort/uploadEfile");
            FileBody bin = new FileBody(ff);

            HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .addPart("Filedata", bin)
                    //可能出现中文这样调用 需要 utf ,注意title不要后缀
                    .addTextBody("title","Core Java笔记MS Word版" , utf8)
                    //写成可以配置的正式环境和测试环境不同 测试是这个值
                    .addTextBody("tableName","E_FILE29")
                    //上次调用返回的did
                    .addTextBody("fileDID","11")
                    //写成可以配置的正式环境和测试环境不同
                    .addTextBody("pzm","ddata")
                    //按照业务确定
                    .addTextBody("upUserCode","ROOT" , utf8)
                    .build();
            httppost.setEntity(reqEntity);
            System.out.println("executing request " + httppost.getRequestLine());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                System.out.println(response.getStatusLine());
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    System.out.println("Response content length: " + resEntity.getContentLength());
                }
                EntityUtils.consume(resEntity);
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }
}

/*  maven依赖
        <dependency>
            <groupId>org.apache.httpcomponents</groupId>
            <artifactId>httpclient</artifactId>
            <version>4.5</version>
        </dependency>
        <dependency>
            <groupId>org.apache.httpcomponents</groupId>
            <artifactId>httpmime</artifactId>
            <version>4.5</version>
        </dependency>
 */