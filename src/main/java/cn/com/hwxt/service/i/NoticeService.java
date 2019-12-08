package cn.com.hwxt.service.i;

import cn.com.hwxt.pojo.SBacklog;

import java.util.List;

public interface NoticeService {
    /**
     * <p>Title: sendActivitiMsg</p>
     * <p>Description: 发送流程待办</p>
     *
     * @param userCodes
     * @param varsJson
     * @param actTaskID
     * @date 2014年6月19日
     */
    public void sendActivitiMsg(String userCodes, String varsJson, String actTaskID);

    /**
     * 得到个人待办
     *
     * @param usercode
     */
    public String toDoList(String usercode);

    /**
     * 根据用户的code和taskid删除待办 手动
     * @param usercode
     * @param taskid
     * @return
     */
    public String deleteUserRequestInfoByJson(String usercode , String taskid);

    /**
     * paraMap.put("flowStatus", (isLastTask ? "FINISHED" : "CONTINUE"));
     * @param actTaskId
     * @param fqr
     * @param flowStatus
     */
    public void sendDestoryMsg(String actTaskId , String fqr , String flowStatus);
}
