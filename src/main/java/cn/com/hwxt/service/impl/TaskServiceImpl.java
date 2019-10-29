package cn.com.hwxt.service.impl;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import cn.com.hwxt.service.BaseService;
import cn.com.hwxt.service.i.TaskService;
import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ch.qos.logback.classic.Logger;

/**
 * oa流程接口服务类,当oa流程完毕后.需要调用此接口完成流程
 * @author LuYu
 */
@Service("taskService")
@WebService(name="TaskWsServcie" , targetNamespace = "http://service.unis.com/")
public class TaskServiceImpl extends BaseService implements TaskService {
	@WebMethod
	public String receiveApproval(@WebParam(name = "taskId") String taskId
			, @WebParam(name = "result") String result){
		String wsRslt = "1";
		Map<String, Object> map = null;
		Integer status = 0;
		if(StringUtils.isNotBlank(taskId) && StringUtils.isNotBlank(result)){
			map = new HashMap<String, Object>();
			map.put("OutSystemFqrCode", "ROOT");
			map.put("spjg", result.contains("true") || result.contains("成功") ? "true":"false");
			try {
				ObjectMapper mapper = new ObjectMapper();  
				StringWriter sw = new StringWriter();
				mapper.writeValue(sw, map);
				//http://localhost/Lams/activiti/completeTask
				status = sendMsg("http://"+lamsIP+"/Lams/activiti/completeTask" 
				, taskId, sw.toString() , Boolean.FALSE.toString()) ;
				System.out.println("call lams.activiti status: " + status);
				wsRslt = "0";
			} catch (Exception e) {
				log.error(e.getMessage());
				wsRslt = wsRslt+"["+e.getMessage()+"]";
			}
		}else{
			wsRslt = wsRslt+"[taskid 或者 result为空]";
		}
		return wsRslt;
	}

	/**
	 * 发动请求到 Lams
	 * @param url
	 * @param taskId
	 * @param varsJson
	 * @param cancelSubmit
	 * @return
	 */
	public Integer sendMsg(String url , String taskId , String varsJson , String cancelSubmit){
		HttpPost post = null;
		HttpClient client = null;
		Integer status = 0;
		try {
			post = new HttpPost(url);
			client = new DefaultHttpClient();
			// 填充表单
			List<NameValuePair> params = new ArrayList<NameValuePair>();

			params.add(new BasicNameValuePair("taskId", taskId));
			params.add(new BasicNameValuePair("varsJson", varsJson));
			params.add(new BasicNameValuePair("cancelSubmit", cancelSubmit));
			post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			status = client.execute(post).getStatusLine().getStatusCode();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} finally {
			HttpClientUtils.closeQuietly(client);
		}
		return status;
	}
	private Logger log =  (Logger) LoggerFactory.getLogger(this.getClass());
}