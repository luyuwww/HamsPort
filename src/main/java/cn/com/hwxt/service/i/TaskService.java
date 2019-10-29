package cn.com.hwxt.service.i;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(name="TaskWsServcie" , targetNamespace = "http://service.unis.com/")
public interface TaskService {
	/**
	 * 接收OA服务,并且向Lams发送流程结束标识
	 * @param uuid 任务id
	 * @param result 结果
	 * @return 0 成功     1失败(原因)
	 */
	@WebMethod
	public String receiveApproval(@WebParam(name = "taskId") String taskId, @WebParam(name = "result") String result);
}
