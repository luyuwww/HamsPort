package cn.com.hwxt.service.impl;

import ch.qos.logback.classic.Logger;
import cn.com.hwxt.service.BaseService;
import cn.com.hwxt.service.i.LscPostService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.tempuri.WebArchivesFor10;
import org.tempuri.WebArchivesFor10Soap;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * @author DaMo
 * @UPDATE 2019/9/2-11:38
 * @since 2019/9/2-11:38
 */
@Service("postLscService")
public class LscPostServiceImpl extends BaseService implements LscPostService {
    @Override
    public void checkBarCodeIsRecive() {
        System.out.println(new DateTime(System.currentTimeMillis()).toString());
        String sql = "SELECT ATTREX,DID,OUTKEY,JSZT,F7 FROM "+yljTableName+" WHERE "+ whereSql;
        System.out.println(sql);
        List<Map<String, Object>> mapList = super.quertListMap(sql);
        for (Map<String, Object>  item : mapList) {
            System.out.println(item.get("DID"));
            updateDtableJSZT(MapUtils.getInteger(item , "DID"),
                    callLscServcie(item));
        }
    }

    /**
     * 调用李思成服务成功后更新预立卷D表接收状态
     */
    private void updateDtableJSZT(Integer did , Boolean zt){
        String updateSql = "UPDATE "+yljTableName+" SET JSZT='";
        if(zt){
            updateSql +="成功' ";
        }else{
            updateSql +="失败' ";
        }
        updateSql +=" WHERE DID="+did;
        System.out.println(updateSql);
        super.execSql(updateSql);
    }

    /**
     * 调用李思成服务器
     * @param uuid
     * @param attrex
     * @return
     */
    private Boolean callLscServcie(Map<String, Object>  item){
        String uuid = MapUtils.getString(item , "OUTKEY");
        Integer theAttrex = MapUtils.getInteger(item , "ATTREX");
        Boolean rslt  = Boolean.FALSE;
        if(StringUtils.isNotBlank(uuid)){
            if(theAttrex.equals(2)){
                rslt = getService().submitDoucmentResult(uuid, Boolean.TRUE);
            }else{
                rslt = getService().submitDoucmentResult(uuid, Boolean.FALSE);
            }
            log.error(uuid + "调用服务器成功");
        }else{
            log.error("callLscServcie.uuid 不能为空");
        }
        return rslt;
    }

    private WebArchivesFor10Soap getService(){
        if(null == service){
            URL url = null;
            try {
                url = new URL(sqlserverSchemaName);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            service = new WebArchivesFor10(url).getWebArchivesFor10Soap();
        }
        return service;
    }

    private WebArchivesFor10Soap service = null;

    @Autowired
    @Value("${lsc.callback.wsdl}")
    protected String sqlserverSchemaName;

    @Autowired
    @Value("${lsc.callback.YLJ.D_FILE.TBNAME}")
    protected String yljTableName;
    @Autowired
    @Value("${lsc.callback.YLJ.wheresql}")
    protected String whereSql;
    private Logger log = (Logger) LoggerFactory.getLogger(this.getClass());
}
