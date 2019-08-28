package cn.com.hwxt.service.impl;

import ch.qos.logback.classic.Logger;
import cn.com.hwxt.dao.i.SBacklogMapper;
import cn.com.hwxt.dao.i.SUserMapper;
import cn.com.hwxt.pojo.SBacklog;
import cn.com.hwxt.pojo.SUser;
import cn.com.hwxt.pojo.SBacklogExample;
import cn.com.hwxt.service.BaseService;
import cn.com.hwxt.service.i.NoticeService;
import cn.com.hwxt.util.CommonUtil;
import cn.com.hwxt.util.DateUtil;
import cn.com.hwxt.util.SeriKeyOper;
import localhost.services.hrmservice.HrmService;
import localhost.services.hrmservice.HrmServicePortType;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import weaver.todo.webservice.OfsTodoDataWebService;
import weaver.todo.webservice.OfsTodoDataWebServicePortType;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

@Service("noticeServiceImpl")
public class NoticeServiceImpl extends BaseService implements NoticeService {

    public void sendActivitiMsg(String userCodes, String varsJson, String actTaskID) {
        System.out.println("审批人："+userCodes);
        String sqrdm = "";
        String sqrxm = "";
        String sqrq = "";

        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> vars = null;
        try {
            String[] userCodeList = StringUtils.split(userCodes,"[,]");
            vars = mapper.readValue(varsJson, Map.class);
            sqrq = (vars.get("sqrq") == null ? "" : vars.get("sqrq").toString());
            sqrq = sqrq.substring(0 , sqrq.indexOf("."));//去除格式多余的0
            sqrdm = (vars.get("sqrdm") == null ? "" : vars.get("sqrdm").toString());
            sqrxm = (vars.get("sqrxm") == null ? "" : vars.get("sqrxm").toString());
            String fqrNames = "";
            for (String fqr : userCodeList) {
                try {
                    //待办消息
                    String returnStr = "Lams?random="+Math.random();
                    SUser user = sUserMapper.getUserByUsercode(fqr);
                    if (null != user) {
                        returnStr = CommonUtil.generat2WebUrl(user, actTaskID);
                    }else{
                        continue;
                    }
                    JSONObject oaTodoMessage = new JSONObject();
                    oaTodoMessage.put("requestname","档案借阅申请("+sqrxm+"["+sqrdm+"])");
                    oaTodoMessage.put("workflowname","利用流程");
                    oaTodoMessage.put("nodename","审批节点");
                    oaTodoMessage.put("pcurl",returnStr);
                    oaTodoMessage.put("appurl","");
                    oaTodoMessage.put("creator",sqrdm);
                    oaTodoMessage.put("createdatetime",sqrq);
                    oaTodoMessage.put("receivedatetime",sqrq);
                    boolean oaRly = oaTodoMsg(oaTodoMessage , actTaskID , fqr);
                    if(oaRly){log.error("OA发送消息成功！");}
                } catch (Exception e) {
                    log.error(e.getMessage() , e );
                }
            }

        } catch (Exception e) {
            log.error("消息待办:" + e.getMessage());
        }
    }

    public void sendDestoryMsg(String actTaskId , String  fqr){
        JSONObject doneMsg = new JSONObject();
        doneMsg.put("requestname","档案借阅审批");
        doneMsg.put("workflowname","利用流程");
        doneMsg.put("nodename","审批节点");
        doneMsg.put("createdatetime",DateUtil.getCurrentTimeStr());
        doneMsg.put("receivedatetime",DateUtil.getCurrentTimeStr());
        boolean oaRe = oaDoneMsg(doneMsg, actTaskId, fqr);
        if(oaRe){
            log.error("oa已办处理成功："+actTaskId);
        }else {
            log.error("oa已办处理异常："+actTaskId);
        }
        String upStateSql = "update s_backlog set isoper = '1' where acttaskid = '"+actTaskId+"'";
        jdbcDao.excute(upStateSql);
    }

    @Override
    public String toDoList(String usercode) {
        String result = "";
        Integer todoNum = sBacklogMapper.getBackLogNum(" OPERMODULEOWNER='izerui' AND OPERMODULEZH='任务列表'"
                + " AND ISOPER=0 AND USERCODE ='" + usercode + "'");
        if (todoNum > 0) {
            try {
                SUser user = sUserMapper.getUserByUsercode(usercode);
                if (user != null) {
                    String title = "档案系统审批: 待处理" + todoNum + "条数据";
                    String url = generatUrl(user, "任务列表", "izerui");
                    System.out.println(title);
                    System.out.println(url);
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return result;
    }

    @Override
    public List<SBacklog> allBacklog(String usercode) {
        SBacklogExample ex = new SBacklogExample();
        ex.createCriteria().andUsercodeEqualTo(usercode);
        List<SBacklog> backLoList = sBacklogMapper.selectByExample(ex);
        return backLoList;
    }

    /**
     * 得到登录Lams并且进入相应模块的URL
     *
     * @param user     用户
     * @param modName  模块中文名称
     * @param modOwner 模块所属者
     */
    private String generatUrl(SUser user, String modName, String modOwner) {
        StringBuffer sb = new StringBuffer();
        sb.append("http://").append(lamsIP).append("/Lams/autoLogin?card=").append(SeriKeyOper.encrypt(user.getUsercode()));
        sb.append("&serikey=").append(SeriKeyOper.encrypt(user.getPasswd())).append("&moduleName=");
        sb.append(SeriKeyOper.encrypt(modName)).append("&moduleOwner=").append(SeriKeyOper.encrypt(modOwner));
        sb.append("&random=").append(Math.random());
        return sb.toString();
    }

    /**
     * OA待办消息
     */
    private Boolean oaTodoMsg(JSONObject doneMessage, String actTaskID, String fqr){
        return oaSendDoOrTodo(doneMessage , actTaskID , fqr , 0 , 0);
    }

    /**
     * OA已办消息
     * @param doneMessage
     * @return
     */
    private boolean oaDoneMsg(JSONObject doneMessage, String actTaskID, String fqr){
        return oaSendDoOrTodo(doneMessage , actTaskID , fqr , 2 ,1);
    }

    /**
     * isremark   0：待办     2：已办   4：办结
     * viewtype   0：未读     1：已读;
     * @return
     */
    private Boolean oaSendDoOrTodo(JSONObject doneMessage, String actTaskID, String fqr
            , Integer isRemard , Integer viewType){
        Boolean resultFlag = Boolean.FALSE;
        doneMessage.put("syscode",weaverSyscode);
        doneMessage.put("flowid",actTaskID);
        doneMessage.put("receiver",fqr);
        doneMessage.put("isremark",isRemard.toString());
        doneMessage.put("viewtype",viewType.toString());

        System.out.println("isRemard:"+ isRemard+" viewType:"+ viewType+" oaSendDoOrTodo-："+doneMessage);
        try {
            String reply =  getTodoService().receiveRequestInfoByJson(doneMessage.toString());
            JSONObject jsonResult = JSONObject.fromObject(reply);
            String result = jsonResult.get("operResult").toString();
            if("1".equals(result)){
                resultFlag = Boolean.TRUE;
            }else {
                String message = jsonResult.get("message").toString();
                log.error("OA已办发送已办异常："+ message);
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new RuntimeException(e);
        }
        return resultFlag;
    }
    /**
     * 得到服务客户端申明
     * @return
     */
    private OfsTodoDataWebServicePortType getTodoService(){
        if(null == ofsTodoDataWebService){
            URL url = null;
            try {
                url = new URL(weaverTodoWSDL);
                ofsTodoDataWebService = new OfsTodoDataWebService(url).getOfsTodoDataWebServiceHttpPort();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return ofsTodoDataWebService;
    }

    public NoticeServiceImpl() {
        try {
            EncodedResource todoRes = new EncodedResource(new ClassPathResource("vm/todo.vm"), "UTF-8");
            msgVM = FileCopyUtils.copyToString(todoRes.getReader());
//			System.out.println(msgVM);
        } catch (IOException e) {
            log.error(e.getMessage());
            System.out.println("系统初始化错误");
            System.exit(0);
        }
    }

    private String msgVM;

    @Autowired
    private SUserMapper sUserMapper;
    @Autowired
    private SBacklogMapper sBacklogMapper;
    @Autowired
    @Value("${sendinfo.todo.title}")
    private String sendInfoTitle;

    @Autowired
    @Value("${weaver.todo.wsdl}")
    private String weaverTodoWSDL;
    @Autowired
    @Value("${weaver.todo.syscode}")
    private String weaverSyscode;

    private OfsTodoDataWebServicePortType ofsTodoDataWebService;
    private Logger log = (Logger) LoggerFactory.getLogger(this.getClass());
}
