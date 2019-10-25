package cn.com.hwxt.weaver;

import org.junit.Test;
import weaver.flow.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * @author DaMo
 * @UPDATE 2019/10/21-23:36
 * @since 2019/10/21-23:36
 */
public class TestWorkFLow {
    /**
     * 测试列出所有流程
     * @throws MalformedURLException
     */
    @Test
    public void testCountFLow() throws MalformedURLException {
        WorkflowService ws = new WorkflowService(new URL("http://oa.szcgc.com:18088/services/WorkflowService?wsdl"));
        WorkflowServicePortType service = ws.getWorkflowServiceHttpPort();
        System.out.println(service.getAllWorkflowRequestCount(249 , null));
        ArrayOfWorkflowRequestInfo arry = service.getAllWorkflowRequestList(1,100,2,249,null);
        List<WorkflowRequestInfo> list =  arry.getWorkflowRequestInfo();
        for (WorkflowRequestInfo wf : list) {
            System.out.println(wf.getWorkflowBaseInfo().getValue().getWorkflowId().getValue()
                    +":"+wf.getWorkflowBaseInfo().getValue().getWorkflowName().getValue());

        }
    }

    @Test
    public void testStarFLow() throws MalformedURLException {
        WorkflowService ws = new WorkflowService(new URL("http://oa.szcgc.com:18088/services/WorkflowService?wsdl"));
        WorkflowServicePortType service = ws.getWorkflowServiceHttpPort();
        ObjectFactory of = new ObjectFactory();
        WorkflowRequestInfo wi = of.createWorkflowRequestInfo();
        wi.setWorkflowBaseInfo(of.creatework);

        System.out.println(service.doCreateWorkflowRequest(null , 249));
    }

}
