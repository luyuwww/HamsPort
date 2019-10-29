package cn.com.hwxt.weaver;

import org.junit.Test;
import weaver.flow.*;

import javax.xml.bind.JAXBElement;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * @author DaMo
 * @UPDATE 2019/10/21-23:36
 * @since 2019/10/21-23:36
 */
public class  TestWorkFLow {
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
    public void testCreateFLow() throws MalformedURLException {
        WorkflowService ws = new WorkflowService(new URL("http://oa.szcgc.com:18088/services/WorkflowService?wsdl"));
        WorkflowServicePortType service = ws.getWorkflowServiceHttpPort();
        ObjectFactory of = new ObjectFactory();
        WorkflowRequestInfo wi = of.createWorkflowRequestInfo();
        wi.setCanView(true);
        wi.setCanEdit(true);
        wi.setRequestId(of.createWorkflowRequestInfoRequestId(theFlowID));
        wi.setRequestName(of.createWorkflowRequestInfoRequestName(theFlowName));
        wi.setRequestLevel(of.createWorkflowRequestInfoRequestLevel(theFlowRqsLevel));
        //        wi.setMessageType();
        //        wi.setStatus();
        //        wi.setCreateTime();
        wi.setCreatorId(of.createWorkflowRequestInfoCreatorId(sqrEsbid));
        wi.setCreatorName(of.createWorkflowRequestInfoCreatorName(sqrxm));

        //base
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
        //sqr
        WorkflowRequestTableField mainField00 = of.createWorkflowRequestTableField();
        mainField00.setFieldName(of.createWorkflowRequestTableFieldFieldName("sqr"));
        mainField00.setFieldValue(of.createWorkflowRequestTableFieldFieldValue("249"));
        mainField00.setView(true);
        mainField00.setEdit(false);
        mainField00.setFieldType(of.createWorkflowRequestTableFieldFieldType("String"));
        workflowRequestTableFields.add(0 , mainField00);

        WorkflowRequestTableField mainField01 = of.createWorkflowRequestTableField();
        mainField01.setFieldName(of.createWorkflowRequestTableFieldFieldName("sqr"));
        mainField01.setFieldValue(of.createWorkflowRequestTableFieldFieldValue("sqrvalue"));
        mainField01.setView(true);
        mainField01.setEdit(false);
        mainField01.setFieldType(of.createWorkflowRequestTableFieldFieldType("String"));
        workflowRequestTableFields.add(1 , mainField01);

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
        for (int i = 0; i < 100; i++) {//每个申请的条目
            WorkflowRequestTableRecord workflowRequestTableRecord = of.createWorkflowRequestTableRecord();
            ArrayOfWorkflowRequestTableField arrayOfWorkflowRequestTableField = of.createArrayOfWorkflowRequestTableField();
            //添加条目中每个字段
            List<WorkflowRequestTableField> workflowRequestTableFields1 = arrayOfWorkflowRequestTableField.getWorkflowRequestTableField();
            for (int j = 0; j < 30; j++) {
                WorkflowRequestTableField workflowRequestTableField = of.createWorkflowRequestTableField();
                workflowRequestTableField.setFieldName(of.createWorkflowRequestTableFieldFieldName("TITLE"));
                workflowRequestTableField.setFieldValue(of.createWorkflowRequestTableFieldFieldValue("SDFSLLk的通知"));
                workflowRequestTableField.setFieldDBType(of.createWorkflowRequestTableFieldFieldDBType("String"));
                workflowRequestTableField.setFieldShowName(of.createWorkflowRequestTableFieldFieldShowName("XXXX"));
                workflowRequestTableFields1.add(workflowRequestTableField);
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

        System.out.println(service.doCreateWorkflowRequest(wi , 249));
    }


    @Test
    public void testStarFLow() throws MalformedURLException {
        WorkflowService ws = new WorkflowService(new URL("http://oa.szcgc.com:18088/services/WorkflowService?wsdl"));
        WorkflowServicePortType service = ws.getWorkflowServiceHttpPort();
        ObjectFactory of = new ObjectFactory();
        WorkflowRequestInfo wi = of.createWorkflowRequestInfo();
        wi.setCanView(true);
        wi.setCanEdit(true);
        wi.setRequestId(of.createWorkflowRequestInfoRequestId(theFlowID));
        wi.setRequestName(of.createWorkflowRequestInfoRequestName(theFlowName));
        wi.setRequestLevel(of.createWorkflowRequestInfoRequestLevel(theFlowRqsLevel));
        //        wi.setMessageType();
        //        wi.setStatus();
        //        wi.setCreateTime();
        wi.setCreatorId(of.createWorkflowRequestInfoCreatorId(sqrEsbid));
        wi.setCreatorName(of.createWorkflowRequestInfoCreatorName(sqrxm));

        //base
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
        //sqr
        WorkflowRequestTableField mainField00 = of.createWorkflowRequestTableField();
        mainField00.setFieldName(of.createWorkflowRequestTableFieldFieldName("sqr"));
        mainField00.setFieldValue(of.createWorkflowRequestTableFieldFieldValue("249"));
        mainField00.setView(true);
        mainField00.setEdit(false);
        mainField00.setFieldType(of.createWorkflowRequestTableFieldFieldType("String"));
        workflowRequestTableFields.add(0 , mainField00);

        WorkflowRequestTableField mainField01 = of.createWorkflowRequestTableField();
        mainField01.setFieldName(of.createWorkflowRequestTableFieldFieldName("sqr"));
        mainField01.setFieldValue(of.createWorkflowRequestTableFieldFieldValue("sqrvalue"));
        mainField01.setView(true);
        mainField01.setEdit(false);
        mainField01.setFieldType(of.createWorkflowRequestTableFieldFieldType("String"));
        workflowRequestTableFields.add(1 , mainField01);

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
        for (int i = 0; i < 100; i++) {//每个申请的条目
            WorkflowRequestTableRecord workflowRequestTableRecord = of.createWorkflowRequestTableRecord();
            ArrayOfWorkflowRequestTableField arrayOfWorkflowRequestTableField = of.createArrayOfWorkflowRequestTableField();
            //添加条目中每个字段
            List<WorkflowRequestTableField> workflowRequestTableFields1 = arrayOfWorkflowRequestTableField.getWorkflowRequestTableField();
            for (int j = 0; j < 30; j++) {
                WorkflowRequestTableField workflowRequestTableField = of.createWorkflowRequestTableField();
                workflowRequestTableField.setFieldName(of.createWorkflowRequestTableFieldFieldName("TITLE"));
                workflowRequestTableField.setFieldValue(of.createWorkflowRequestTableFieldFieldValue("SDFSLLk的通知"));
                workflowRequestTableField.setFieldDBType(of.createWorkflowRequestTableFieldFieldDBType("String"));
                workflowRequestTableField.setFieldShowName(of.createWorkflowRequestTableFieldFieldShowName("XXXX"));
                workflowRequestTableFields1.add(workflowRequestTableField);
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

        System.out.println(service.submitWorkflowRequest(wi , 71116 , 249 , "submit" , "11"));
    }

    private String defFlowID = "76";
    private String defFlowName = "借（领）用项目合同档案审批";
    private String defFlowTypeID = "18";
    private String defFlowTypeName = "业务管理";

    private String theFlowID = "11111-test001";
    private String theFlowName = "第一个测试流程-test001";
    private String theFlowRqsLevel = "0";//level
    private String sqrcode = "";
    private String sqrEsbid = "249";
    private String sqrxm = "蓝工";

    private String mainTbName = "mainTable";
    private String detailTbName = "detailTable";

}
