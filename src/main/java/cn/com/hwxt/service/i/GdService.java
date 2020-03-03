package cn.com.hwxt.service.i;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(name = "GdDataWs", targetNamespace = "http://unisra.www.com/")
public interface GdService {

    @WebMethod
    public String dataReciveXml(@WebParam(name = "appKey") String appKey  , @WebParam(name = "dataXml") String dataXml
            , @WebParam(name = "pk") String pk , @WebParam(name = "md5") String md5);


    @WebMethod
    public String dataReciveJson(@WebParam(name = "appKey") String appKey , @WebParam(name = "dataJson") String dataJson
            , @WebParam(name = "pk") String pk , @WebParam(name = "md5") String md5);
}
