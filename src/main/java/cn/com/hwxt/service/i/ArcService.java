package cn.com.hwxt.service.i;

import cn.com.hwxt.pojo.SUser;

import javax.jws.WebService;
import java.util.List;

@WebService(name = "ArcDataWs", targetNamespace = "http://service.hwxt.com.cn/")
public interface ArcService {
    /**
     * 数据接受服务
     *
     * @param xml
     */
    public String fileRecive(String xml);

    /**
     * 列出所有用户
     */
    public List<SUser> listAllUser();

    /**
     * 得到bmid
     *
     * @param depCode
     * @return BMID str
     */
    public String getBmidStrByDepCode(String depCode);

    /**
     * 得到bmid
     *
     * @param userCode
     */
    public String getBmidStrByUserCode(String userCode);

    public String syncDclassfy(Integer libcode);
}
