package cn.com.hwxt;

import cn.com.hwxt.util.DateUtil;
import net.sf.json.JSONObject;

/**
 * @author DaMo
 * @UPDATE 2019/8/23-16:00
 * @since 2019/8/23-16:00
 */
public class TestJsonObject {
    public static void main(String[] args) {
        JSONObject doneMsg = new JSONObject();
        doneMsg.put("requestname","档案借阅审批");
        doneMsg.put("workflowname","利用流程");
        doneMsg.put("nodename","审批节点");
        doneMsg.put("syscode","lams");
        doneMsg.put("flowid","001-ams2000-1");
        doneMsg.put("receiver","004820");
        doneMsg.put("pcurl","http://www.baidu.com");
        System.out.println(doneMsg.toString());


        JSONObject oaTodoMessage = new JSONObject();
        oaTodoMessage.put("syscode","lams");
        oaTodoMessage.put("flowid","001-ams2000-1");
        oaTodoMessage.put("requestname","档案借阅申请");
        oaTodoMessage.put("workflowname","利用流程");
        oaTodoMessage.put("isremark","0");
        oaTodoMessage.put("viewtype","0");
        oaTodoMessage.put("nodename","审批节点");
        oaTodoMessage.put("pcurl","http://www.baidu.com");
        oaTodoMessage.put("creator","004820");//用户需要ESBID
        oaTodoMessage.put("createdatetime", DateUtil.getCurrentTimeStr());
        oaTodoMessage.put("receiver","004820");
        oaTodoMessage.put("receivedatetime",DateUtil.getCurrentTimeStr());
        System.out.println(oaTodoMessage.toString());

    }
}
