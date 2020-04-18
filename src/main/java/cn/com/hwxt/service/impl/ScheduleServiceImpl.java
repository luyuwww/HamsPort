package cn.com.hwxt.service.impl;

import ch.qos.logback.classic.Logger;
import cn.com.hwxt.dao.JdbcDao;
import cn.com.hwxt.dao.i.SUserMapper;
import cn.com.hwxt.pojo.SBizEepQueue;
import cn.com.hwxt.service.i.ScheduleService;
import org.codehaus.jackson.map.ser.StdSerializers;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author DaMo
 * @UPDATE 2020/4/8-11:03
 * @since 2020/4/8-11:03
 */
public class ScheduleServiceImpl implements ScheduleService {

    @Override
    public String parseEepQueue() {
        String msg = "";
        if(working){
            return msg;
        }
        working = Boolean.TRUE;
        try {
            List<SBizEepQueue>  queue = sUserMapper.getSBizEepQueues("STATUS IS NULL OR STATUS=0");
            for (SBizEepQueue sBizEepQueue : queue) {
                System.out.println(sBizEepQueue.getPackagePath());
                String rslt = parseEEP(sBizEepQueue);
                if(rslt.equalsIgnoreCase("OK")){
                    sUserMapper.updateee
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage() , e);
            e.printStackTrace();
        }finally {
            working = Boolean.FALSE;
        }
        return msg;
    }

    /**
     * 开始解析一个包
     * @param queue
     * @return
     */
    private  String parseEEP(SBizEepQueue queue){
        return "OK";
    }
    @Autowired
    private SUserMapper sUserMapper;

    private static Boolean working = Boolean.FALSE;

    private Logger log = (Logger) LoggerFactory.getLogger(this.getClass());
}
