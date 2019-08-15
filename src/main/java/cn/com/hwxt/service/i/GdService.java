package cn.com.hwxt.service.i;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(name = "GdDataWs", targetNamespace = "http://service.hwxt.com.cn/")
public interface GdService {

    @WebMethod
    public String dataReciveXml(@WebParam(name = "xmlName") String xmlName
            , @WebParam(name = "dataXml") String dataXml, @WebParam(name = "gdrCode") String gdrCode);


    @WebMethod
    public String dataReciveJson(@WebParam(name = "xmlName") String xmlName
            , @WebParam(name = "dataJson") String dataJson, @WebParam(name = "gdrCode") String gdrCode);

    @WebMethod
    public Integer fileReciveXml(@WebParam(name = "xmlName") String xmlName
            , @WebParam(name = "dataXml") String dataXml, @WebParam(name = "gdrCode") String gdrCode);


    @WebMethod
    public Integer fileReciveJson(@WebParam(name = "xmlName") String xmlName
            , @WebParam(name = "dataJson") String dataJson, @WebParam(name = "gdrCode") String gdrCode);
}
