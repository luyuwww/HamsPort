package cn.com.hwxt.service.impl;

import ch.qos.logback.classic.Logger;
import cn.com.hwxt.dao.i.SBacklogMapper;
import cn.com.hwxt.dao.i.SUserMapper;
import cn.com.hwxt.pojo.SBacklog;
import cn.com.hwxt.pojo.SGroup;
import cn.com.hwxt.pojo.SUser;
import cn.com.hwxt.pojo.SBacklogExample;
import cn.com.hwxt.service.BaseService;
import cn.com.hwxt.service.i.NoticeService;
import cn.com.hwxt.util.CommonUtil;
import cn.com.hwxt.util.DateUtil;
import cn.com.hwxt.util.GlobalFinalAttr;
import cn.com.hwxt.util.SeriKeyOper;
import net.sf.json.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import weaver.flow.*;
import weaver.todo.webservice.OfsTodoDataWebService;
import weaver.todo.webservice.OfsTodoDataWebServicePortType;

import javax.xml.bind.JAXBElement;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service("noticeServiceImpl")
public class NoticeServiceImpl extends BaseService implements NoticeService {

    public void sendActivitiMsg(String userCodes, String varsJson, String actTaskID) {
        if(isDebug){
            log.error(varsJson);
            System.out.println(varsJson);
        }
        if(goToOutSystem){
            System.out.println(1);
            toOutSysFlow(userCodes , varsJson , actTaskID);
        }else{
            toToDo(userCodes , varsJson , actTaskID);
        }

    }
    //star out system flow
    private void toOutSysFlow(String userCodes, String varsJson, String actTaskID){
        String sqrbm = "";
        String lymd = "";
        String sqrdm = "";
        String sqrxm = "";
        String sqyy = "";
        String sqtype = "";
        String lylx = "";
        Integer lylxNum = 1;//默认时间段
        Integer times = 0;
        String starTime = "";
        String endTime = "";
        String requestTime = "";
        String mj = "";
        SUser user = null;

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> vars = null;
        try {
            String[] userCodeList = StringUtils.split(userCodes, "[,]");
            vars = mapper.readValue(varsJson, Map.class);
            sqrxm = (vars.get("sqrxm") == null ? "" : vars.get("sqrxm").toString());
            lymd = (vars.get("lymd") == null ? "" : vars.get("lymd").toString());
            sqrdm = (vars.get("sqrdm") == null ? "" : vars.get("sqrdm").toString());
            sqrbm = (vars.get("sqrbm") == null ? "" : vars.get("sqrbm").toString());
            sqyy = (vars.get("sqyy") == null ? "" : vars.get("sqyy").toString());
            sqtype = (vars.get("sqtype") == null ? "" : vars.get("sqtype").toString());

            if(sqtype.equals("下载") || sqtype.equals("2")){
                sqtype = "浏览、下载";
            }else if(sqtype.equals("借阅")|| sqtype.equals("3")){
                sqtype = "实物借阅";
            }

            lylx = (vars.get("timeortimes") == null ? "" : vars.get("timeortimes").toString());
            requestTime = (vars.get("sqrq") == null ? "" : vars.get("sqrq").toString());
            if (lylx.equals("次数")) {
                lylxNum = 0;
                times = MapUtils.getInteger(vars, "times");
            } else {
                starTime = (vars.get("startime") == null ? "" : vars.get("startime").toString());
                endTime = (vars.get("endtime") == null ? "" : vars.get("endtime").toString());
            }

            mj = (vars.get("mj") == null ? "" : vars.get("mj").toString());
            List<Map<String, Object>> dataList = (List<Map<String, Object>>) vars.get("dataList");

            System.out.println(2);
            if (null == dataList || dataList.size() < 1) {
                log.error("档案系统发送了错误数.dataList为空");
                return;
            }
            for (String userCode : userCodeList) {
                SUser sqrUser = sUserMapper.getUserByUsercode(sqrdm);
                if (sqrUser != null && StringUtils.isNotBlank(sqrUser.getEsbid())) {
                    WorkflowServicePortType service = getFlowWebService();
                    ObjectFactory of = new ObjectFactory();
                    WorkflowRequestInfo wi = of.createWorkflowRequestInfo();
                    wi.setCanView(true);
                    wi.setCanEdit(true);
                    wi.setRequestId(of.createWorkflowRequestInfoRequestId(actTaskID));
                    wi.setRequestName(of.createWorkflowRequestInfoRequestName(sqTitle));
                    wi.setRequestLevel(of.createWorkflowRequestInfoRequestLevel(theFlowRqsLevel));
                    //        wi.setMessageType();
                    //        wi.setStatus();
                    //        wi.setCreateTime();
                    //组织机构同步的时候注意需要吧oa的userid放入档案系统的s_user.esbid
                    wi.setCreatorId(of.createWorkflowRequestInfoCreatorId(sqrUser.getEsbid()));
                    wi.setCreatorName(of.createWorkflowRequestInfoCreatorName(sqrUser.getUsername()));
                    WorkflowBaseInfo wfBaseInfo = of.createWorkflowBaseInfo();
                    wfBaseInfo.setWorkflowId(of.createWorkflowBaseInfoWorkflowId(defFlowID));
                    wfBaseInfo.setWorkflowName(of.createWorkflowBaseInfoWorkflowName(defFlowName));
                    wfBaseInfo.setWorkflowTypeId(of.createWorkflowBaseInfoWorkflowTypeId(defFlowTypeID));
                    wfBaseInfo.setWorkflowTypeName(of.createWorkflowBaseInfoWorkflowTypeName(defFlowTypeName));
                    JAXBElement<WorkflowBaseInfo> wfbiJAXBE = of.createWorkflowRequestInfoWorkflowBaseInfo(wfBaseInfo);
                    wi.setWorkflowBaseInfo(wfbiJAXBE);

                    /****************main table start*************/
                    WorkflowMainTableInfo mainTableInfo = of.createWorkflowMainTableInfo();
                    mainTableInfo.setTableDBName(of.createWorkflowMainTableInfoTableDBName(mainTbName));
                    ArrayOfWorkflowRequestTableRecord arrayOfWorkflowRequestTableRecord = of.createArrayOfWorkflowRequestTableRecord();
                    List<WorkflowRequestTableRecord> recodeList = arrayOfWorkflowRequestTableRecord.getWorkflowRequestTableRecord();

                    WorkflowRequestTableRecord theRecode = of.createWorkflowRequestTableRecord();
                    ArrayOfWorkflowRequestTableField mainFieldArray = of.createArrayOfWorkflowRequestTableField();
                    List<WorkflowRequestTableField> workflowRequestTableFields = mainFieldArray.getWorkflowRequestTableField();

                    Integer mainFieldIndex = 0;
                    //申请人部门
                    WorkflowRequestTableField mainField00 = of.createWorkflowRequestTableField();
                    mainField00.setFieldName(of.createWorkflowRequestTableFieldFieldName("sqrbm"));
                    mainField00.setFieldShowName(of.createWorkflowRequestTableFieldFieldShowName("申请人部门"));
                    mainField00.setFieldValue(of.createWorkflowRequestTableFieldFieldValue(sqrbm));
                    mainField00.setView(true);
                    mainField00.setEdit(false);
                    mainField00.setFieldType(of.createWorkflowRequestTableFieldFieldType("String"));
                    workflowRequestTableFields.add(mainFieldIndex++ , mainField00);
                    //申请人姓名
                    WorkflowRequestTableField mainField01 = of.createWorkflowRequestTableField();
                    mainField01.setFieldName(of.createWorkflowRequestTableFieldFieldName("sqrxm"));
                    mainField01.setFieldShowName(of.createWorkflowRequestTableFieldFieldShowName("申请人姓名"));
                    mainField01.setFieldValue(of.createWorkflowRequestTableFieldFieldValue(sqrxm));
                    mainField01.setView(true);
                    mainField01.setEdit(false);
                    mainField01.setFieldType(of.createWorkflowRequestTableFieldFieldType("String"));
                    workflowRequestTableFields.add(mainFieldIndex++ , mainField01);
                    //利用目的
                    WorkflowRequestTableField mainField02 = of.createWorkflowRequestTableField();
                    mainField02.setFieldName(of.createWorkflowRequestTableFieldFieldName("lymd"));
                    mainField02.setFieldShowName(of.createWorkflowRequestTableFieldFieldShowName("利用目的"));
                    mainField02.setFieldValue(of.createWorkflowRequestTableFieldFieldValue(lymd));
                    mainField02.setView(true);
                    mainField02.setEdit(false);
                    mainField02.setFieldType(of.createWorkflowRequestTableFieldFieldType("String"));
                    workflowRequestTableFields.add(mainFieldIndex++ , mainField02);
                    //申请人代码
                    WorkflowRequestTableField mainField03 = of.createWorkflowRequestTableField();
                    mainField03.setFieldName(of.createWorkflowRequestTableFieldFieldName("sqrdm"));
                    mainField03.setFieldShowName(of.createWorkflowRequestTableFieldFieldShowName("申请人登录名"));
                    mainField03.setFieldValue(of.createWorkflowRequestTableFieldFieldValue(sqrdm));
                    mainField03.setView(true);
                    mainField03.setEdit(false);
                    mainField03.setFieldType(of.createWorkflowRequestTableFieldFieldType("String"));
                    workflowRequestTableFields.add(mainFieldIndex++ , mainField03);
                    //申请缘由
                    WorkflowRequestTableField mainField04 = of.createWorkflowRequestTableField();
                    mainField04.setFieldName(of.createWorkflowRequestTableFieldFieldName("sqyy"));
                    mainField04.setFieldShowName(of.createWorkflowRequestTableFieldFieldShowName("申请缘由"));
                    mainField04.setFieldValue(of.createWorkflowRequestTableFieldFieldValue(sqyy));
                    mainField04.setView(true);
                    mainField04.setEdit(false);
                    mainField04.setFieldType(of.createWorkflowRequestTableFieldFieldType("String"));
                    workflowRequestTableFields.add(mainFieldIndex++ , mainField04);
                    //申请操作类型 sqtype
                    WorkflowRequestTableField mainField05 = of.createWorkflowRequestTableField();
                    mainField05.setFieldName(of.createWorkflowRequestTableFieldFieldName("sqtype"));
                    mainField05.setFieldShowName(of.createWorkflowRequestTableFieldFieldShowName("申请操作类型"));
                    mainField05.setFieldValue(of.createWorkflowRequestTableFieldFieldValue(sqtype));
                    mainField05.setView(true);
                    mainField05.setEdit(false);
                    mainField05.setFieldType(of.createWorkflowRequestTableFieldFieldType("String"));
                    workflowRequestTableFields.add(mainFieldIndex++ , mainField05);
                    //利用类型
                    WorkflowRequestTableField mainField06 = of.createWorkflowRequestTableField();
                    mainField06.setFieldName(of.createWorkflowRequestTableFieldFieldName("lylx"));
                    mainField06.setFieldShowName(of.createWorkflowRequestTableFieldFieldShowName("利用类型"));
                    mainField06.setFieldValue(of.createWorkflowRequestTableFieldFieldValue(lylx));
                    mainField06.setView(true);
                    mainField06.setEdit(false);
                    mainField06.setFieldType(of.createWorkflowRequestTableFieldFieldType("String"));
                    workflowRequestTableFields.add(mainFieldIndex++ , mainField06);
                    if (lylx.equals("次数")) {
                        //申请次数
                        WorkflowRequestTableField mainField07 = of.createWorkflowRequestTableField();
                        mainField07.setFieldName(of.createWorkflowRequestTableFieldFieldName("times"));
                        mainField07.setFieldShowName(of.createWorkflowRequestTableFieldFieldShowName("申请次数"));
                        mainField07.setFieldValue(of.createWorkflowRequestTableFieldFieldValue(times+""));
                        mainField07.setView(true);
                        mainField07.setEdit(false);
                        mainField07.setFieldType(of.createWorkflowRequestTableFieldFieldType("String"));
                        workflowRequestTableFields.add(mainFieldIndex++ , mainField07);
                    }else{
                        //开始时间
                        WorkflowRequestTableField mainField08 = of.createWorkflowRequestTableField();
                        mainField08.setFieldName(of.createWorkflowRequestTableFieldFieldName("starTime"));
                        mainField08.setFieldShowName(of.createWorkflowRequestTableFieldFieldShowName("开始时间"));
                        mainField08.setFieldValue(of.createWorkflowRequestTableFieldFieldValue(starTime));
                        mainField08.setView(true);
                        mainField08.setEdit(false);
                        mainField08.setFieldType(of.createWorkflowRequestTableFieldFieldType("String"));
                        workflowRequestTableFields.add(mainFieldIndex++ , mainField08);
                        //结束时间
                        WorkflowRequestTableField mainField09 = of.createWorkflowRequestTableField();
                        mainField09.setFieldName(of.createWorkflowRequestTableFieldFieldName("endTime"));
                        mainField09.setFieldShowName(of.createWorkflowRequestTableFieldFieldShowName("结束时间"));
                        mainField09.setFieldValue(of.createWorkflowRequestTableFieldFieldValue(endTime));
                        mainField09.setView(true);
                        mainField09.setEdit(false);
                        mainField09.setFieldType(of.createWorkflowRequestTableFieldFieldType("String"));
                        workflowRequestTableFields.add(mainFieldIndex++ , mainField09);
                    }
                    //申请时间
                    WorkflowRequestTableField mainField10 = of.createWorkflowRequestTableField();
                    mainField10.setFieldName(of.createWorkflowRequestTableFieldFieldName("requestTime"));
                    mainField10.setFieldShowName(of.createWorkflowRequestTableFieldFieldShowName("申请时间"));
                    mainField10.setFieldValue(of.createWorkflowRequestTableFieldFieldValue(requestTime));
                    mainField10.setView(true);
                    mainField10.setEdit(false);
                    mainField10.setFieldType(of.createWorkflowRequestTableFieldFieldType("String"));
                    workflowRequestTableFields.add(mainFieldIndex++ , mainField10);
                    //密级
                    WorkflowRequestTableField mainField11 = of.createWorkflowRequestTableField();
                    mainField11.setFieldName(of.createWorkflowRequestTableFieldFieldName("mj"));
                    mainField11.setFieldShowName(of.createWorkflowRequestTableFieldFieldShowName("密级"));
                    mainField11.setFieldValue(of.createWorkflowRequestTableFieldFieldValue(mj));
                    mainField11.setView(true);
                    mainField11.setEdit(false);
                    mainField11.setFieldType(of.createWorkflowRequestTableFieldFieldType("String"));
                    workflowRequestTableFields.add(mainFieldIndex++ , mainField11);

                    JAXBElement<ArrayOfWorkflowRequestTableField> fieldJAXBElement = of.createWorkflowRequestTableRecordWorkflowRequestTableFields(mainFieldArray);

                    theRecode.setWorkflowRequestTableFields(fieldJAXBElement);
                    recodeList.add(theRecode);
                    JAXBElement<ArrayOfWorkflowRequestTableRecord> requestRecodes = of.createWorkflowMainTableInfoRequestRecords(arrayOfWorkflowRequestTableRecord);
                    mainTableInfo.setRequestRecords(requestRecodes);
                    wi.setWorkflowMainTableInfo(of.createWorkflowRequestInfoWorkflowMainTableInfo(mainTableInfo));
                    /****************main table end*************/
                    /****************申请的条目表 table start*************/
                    ArrayOfWorkflowDetailTableInfo arrayOfWorkflowDetailTableInfo = of.createArrayOfWorkflowDetailTableInfo();
                    List<WorkflowDetailTableInfo> workflowDetailTableInfosList = arrayOfWorkflowDetailTableInfo.getWorkflowDetailTableInfo();
                    WorkflowDetailTableInfo workflowDetailTableInfo = of.createWorkflowDetailTableInfo();
                    workflowDetailTableInfo.setTableDBName(of.createWorkflowDetailTableInfoTableDBName(detailTbName));

                    ArrayOfWorkflowRequestTableRecord arrayOfWorkflowRequestTableRecord1 = of.createArrayOfWorkflowRequestTableRecord();
                    List<WorkflowRequestTableRecord> workflowRequestTableRecords = arrayOfWorkflowRequestTableRecord1.getWorkflowRequestTableRecord();

                    for (Map<String, Object> map : dataList)  {//每个申请的条目
                        WorkflowRequestTableRecord workflowRequestTableRecord = of.createWorkflowRequestTableRecord();
                        ArrayOfWorkflowRequestTableField arrayOfWorkflowRequestTableField = of.createArrayOfWorkflowRequestTableField();
                        //添加条目中每个字段
                        List<WorkflowRequestTableField> workflowRequestTableFields1 = arrayOfWorkflowRequestTableField.getWorkflowRequestTableField();
                        Set<String> keySet = map.keySet();
                        for (String key : keySet) {
                            Object theValue =  MapUtils.getObject(map , key);
                            if(null != theValue){
                                WorkflowRequestTableField workflowRequestTableField = of.createWorkflowRequestTableField();
                                workflowRequestTableField.setFieldName(of.createWorkflowRequestTableFieldFieldName("key"));
                                workflowRequestTableField.setFieldValue(of.createWorkflowRequestTableFieldFieldValue(theValue+""));
                                workflowRequestTableField.setFieldDBType(of.createWorkflowRequestTableFieldFieldDBType("String"));
                                workflowRequestTableField.setFieldShowName(of.createWorkflowRequestTableFieldFieldShowName("XXXX"));
                                workflowRequestTableFields1.add(workflowRequestTableField);
                            }
                        }
                        JAXBElement<ArrayOfWorkflowRequestTableField> fieldJAXBElement1 = of.createWorkflowRequestTableRecordWorkflowRequestTableFields(arrayOfWorkflowRequestTableField);
                        workflowRequestTableRecord.setWorkflowRequestTableFields(fieldJAXBElement1);
                        workflowRequestTableRecords.add(workflowRequestTableRecord);

                    }
                    JAXBElement<ArrayOfWorkflowRequestTableRecord> arrayOfWorkflowRequestTableRecordJAXBElement = of.createWorkflowMainTableInfoRequestRecords(arrayOfWorkflowRequestTableRecord1);

                    workflowDetailTableInfo.setWorkflowRequestTableRecords(arrayOfWorkflowRequestTableRecordJAXBElement);
                    workflowDetailTableInfosList.add(workflowDetailTableInfo);
                    JAXBElement<ArrayOfWorkflowDetailTableInfo> detailTableInfoJAXBElement = of.createWorkflowRequestInfoWorkflowDetailTableInfos(arrayOfWorkflowDetailTableInfo);
                    wi.setWorkflowDetailTableInfos(detailTableInfoJAXBElement);
                    /****************申请的条目表 table end*************/
                    log.error(service.doCreateWorkflowRequest(wi , Integer.parseInt(sqrUser.getEsbid())));
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
    //star todo
    private void toToDo(String userCodes, String varsJson, String actTaskID){
        System.out.println("审批人："+userCodes);
        String sqrdm = "";
        String sqrxm = "";
        String sqrq = "";
        String flowEndCallSqr = "";//是否发起给申请人

        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> vars = null;
        try {
            String[] userCodeList = StringUtils.split(userCodes,"[,]");
            vars = mapper.readValue(varsJson, Map.class);
            sqrq = (vars.get("sqrq") == null ? "" : vars.get("sqrq").toString());
            sqrq = sqrq.substring(0 , sqrq.indexOf("."));//去除格式多余的0
            sqrdm = (vars.get("sqrdm") == null ? "" : vars.get("sqrdm").toString());
            sqrxm = (vars.get("sqrxm") == null ? "" : vars.get("sqrxm").toString());
            flowEndCallSqr = (vars.get("flowEndCallSqr") == null ? "" : vars.get("flowEndCallSqr").toString());
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

                    //流程结束 通知申请人 (泛微 申请已办    接收人 创建人都是自己  remark 1)
                    if(StringUtils.isNotBlank(flowEndCallSqr) && flowEndCallSqr.equals("CALL")) {
                        JSONObject oaTodoMessage = new JSONObject();
                        oaTodoMessage.put("requestname","档案借阅申请-已经处理");
                        oaTodoMessage.put("workflowname","利用流程");
                        oaTodoMessage.put("nodename","结束节点");
                        oaTodoMessage.put("pcurl",returnStr);
                        oaTodoMessage.put("appurl","");
                        oaTodoMessage.put("creator",fqr);
                        oaTodoMessage.put("createdatetime",sqrq);
                        oaTodoMessage.put("receivedatetime",sqrq);
                        boolean oaRly = oaTodoMsg(oaTodoMessage , actTaskID , fqr , 1);
                        if(oaRly){log.error("OA发送消息成功！");}
                    }else{//通知审批人
                        JSONObject oaTodoMessage = new JSONObject();
                        oaTodoMessage.put("requestname","档案借阅申请("+sqrxm+"["+sqrdm+"])");
                        oaTodoMessage.put("workflowname","利用流程");
                        oaTodoMessage.put("nodename","审批节点");
                        oaTodoMessage.put("pcurl",returnStr);
                        oaTodoMessage.put("appurl","");
                        oaTodoMessage.put("creator",sqrdm);
                        oaTodoMessage.put("createdatetime",sqrq);
                        oaTodoMessage.put("receivedatetime",sqrq);
                        boolean oaRly = oaTodoMsg(oaTodoMessage , actTaskID , fqr , 0 );
                        if(oaRly){log.error("OA发送消息成功！");}
                    }
                } catch (Exception e) {
                    log.error(e.getMessage() , e );
                }
            }
        } catch (Exception e) {
            log.error("消息待办:" + e.getMessage());
        }
    }

    public void sendDestoryMsg(String actTaskId , String fqr , String flowStatus){
        if(StringUtils.isBlank(actTaskId)){
            return;
        }
        //flowStatus=FINISHED 最后的结束节点 要把线上所有的变成办结
        if(StringUtils.isNotBlank(flowStatus) && flowStatus.equals("FINISHED")){
            List<SBacklog> backlogs = sBacklogMapper.getAllBacklogOnProcess(actTaskId);
            for (SBacklog backlog : backlogs) {
                JSONObject doneMsg = new JSONObject();
                doneMsg.put("requestname","档案借阅审批");
                doneMsg.put("workflowname","利用流程");
                doneMsg.put("nodename","审批节点");
                doneMsg.put("createdatetime",DateUtil.getCurrentTimeStr());
                doneMsg.put("receivedatetime",DateUtil.getCurrentTimeStr());
                //paraMap.put("flowStatus", (isLastTask ? "FINISHED" : "CONTINUE")) 这个值是Lams里面规定的
//                 * isremark   0：待办     2：已办   4：办结
//                 * viewtype   0：未读     1：已读;
//            oaSendDoOrTodo(doneMessage , actTaskID , fqr , 2 ,1);
                boolean oaRe = oaSendDoOrTodo(doneMsg, "TODOD", backlog.getUsercode() , 4, 0);
                if(oaRe){
                    log.error("oa已办处理成功："+actTaskId);
                }else {
                    log.error("oa已办处理异常："+actTaskId);
                }
            }
        }else{
            JSONObject doneMsg = new JSONObject();
            doneMsg.put("requestname","档案借阅审批");
            doneMsg.put("workflowname","利用流程");
            doneMsg.put("nodename","审批节点");
            doneMsg.put("createdatetime",DateUtil.getCurrentTimeStr());
            doneMsg.put("receivedatetime",DateUtil.getCurrentTimeStr());
            boolean oaRe = oaSendDoOrTodo(doneMsg, actTaskId, fqr , 2 , 0);
            if(oaRe){
                log.error("oa已办处理成功："+actTaskId);
            }else {
                log.error("oa已办处理异常："+actTaskId);
            }
        }


        String upStateSql = "update s_backlog set isoper = '1' where acttaskid = '"+actTaskId+"'";
        jdbcDao.excute(upStateSql);
    }

    public String deleteUserRequestInfoByJson(String usercode , String taskid){
        JSONObject delJson = new JSONObject();
        delJson.put("syscode",weaverSyscode);
        delJson.put("flowid",taskid);
        delJson.put("userid",usercode);
        String result = "";
        System.out.println(delJson.toString());
        try {
            String reply =  getTodoService().deleteUserRequestInfoByJson(delJson.toString());
            JSONObject jsonResult = JSONObject.fromObject(reply);
            result  =jsonResult.toString();
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new RuntimeException(e);
        }
        return result;
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
     * OA待办消息 或者 申请已办 remark=0待办  remark=1已办
     */
    private Boolean oaTodoMsg(JSONObject doneMessage, String actTaskID, String fqr , Integer remark){
        return oaSendDoOrTodo(doneMessage , actTaskID , fqr , remark , 0);
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
     * 得到服务客户端申明 外发待办
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

    /**
     * 得到服务客户端申明  外发路程
     * @return
     */
    private WorkflowServicePortType getFlowWebService(){
        if(null == flowWebServcie){
            URL url = null;
            try {
                url = new URL(flowWsdl);
                flowWebServcie = new WorkflowService(url).getWorkflowServiceHttpPort();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return flowWebServcie;
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
    @Autowired
    @Value("${lams.flow.debug}")
    private Boolean isDebug;
    @Autowired
    @Value("${lams.flow.GoToOutSystem}")
    private Boolean goToOutSystem;



    @Autowired
    @Value("${lams.flow.defFlowID}")
    private String defFlowID;
    @Autowired
    @Value("${lams.flow.defFlowName}")
    private String defFlowName;
    @Autowired
    @Value("${lams.flow.defFlowTypeID}")
    private String defFlowTypeID;
    @Autowired
    @Value("${lams.flow.defFlowTypeName}")
    private String defFlowTypeName;
    @Autowired
    @Value("${lams.flow.theFlowRqsLevel}")
    private String theFlowRqsLevel;//level
    @Autowired
    @Value("${lams.flow.mainTbName}")
    private String mainTbName;
    @Autowired
    @Value("${lams.flow.detailTbName}")
    private String detailTbName;
    @Autowired
    @Value("${lams.flow.wsdl}")
    private String flowWsdl;
    @Autowired
    @Value("${lams.flow.SqTitle}")
    private String sqTitle;

    private OfsTodoDataWebServicePortType ofsTodoDataWebService;
    private  WorkflowServicePortType flowWebServcie;
    private Logger log = (Logger) LoggerFactory.getLogger(this.getClass());
}
