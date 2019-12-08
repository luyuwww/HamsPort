package cn.com.hwxt.action;

import ch.qos.logback.classic.Logger;
import cn.com.hwxt.dao.i.SUserMapper;
import cn.com.hwxt.pojo.OaCompany;
import cn.com.hwxt.pojo.SUser;
import cn.com.hwxt.service.i.ArcService;
import cn.com.hwxt.service.i.NoticeService;
import cn.com.hwxt.service.i.SyncDepAndUserService;
import cn.com.hwxt.util.CommonUtil;
import cn.com.hwxt.util.GlobalFinalAttr;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import weaver.hrm.jaxb.SubCompanyBeanArray;
import weaver.hrm.webservice.SubCompanyBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CommonCtler {
    /**
     * 主页跳转
     */
    @RequestMapping(value = {"/index.html", "/"})
    public String gotoIndex() {
        return "index.jsp";
    }

    /**
     * 列出所有日志- add
     */
    @RequestMapping(value = "/viewLogList")
    public String viewLogList(Model model) {
        try {
            File[] listFile = new File(logHomeAdd).listFiles();
            model.addAttribute("listFile", listFile);
            model.addAttribute("fileType", "log");
            return "listLog.jsp";
        } catch (Exception e) {
            log.error("获取日志列表错误.", e);
            return "index.jsp";
        }
    }

    /**
     * 列出所有XML
     */
    @RequestMapping(value = "/viewXMLList")
    public String viewXMLList(Model model) {
        try {
            File[] listFile = new File(GlobalFinalAttr.XML_PATH).listFiles();
            model.addAttribute("listFile", listFile);
            model.addAttribute("fileType", "xml");
            return "listLog.jsp";
        } catch (Exception e) {
            log.error("获取日志列表错误.", e);
            return "index.jsp";
        }
    }

    /**
     * 查看日志
     */
    @RequestMapping("/viewLog")
    public void viewLog(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter out = null;
        try {
            response.setContentType("text/html;charset=GBK ");
            out = response.getWriter();
            out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
            out.println("<HTML>");
            out.println("<BODY>");
            out.println("<XMP>");
            String filePath = request.getParameter("logFilePath");
            String fileType = request.getParameter("fileType");
            if (StringUtils.isEmpty(filePath)) {
                out.print("获取日志错误!");
            } else {
                filePath = new String(filePath.getBytes("ISO-8859-1"), "UTF-8");
                File tempFile = new File((fileType.equals("xml") ? GlobalFinalAttr.XML_PATH : logHomeAdd)
                        + File.separatorChar + filePath);
                if (null != tempFile) {
                    List<String> stList = FileUtils.readLines(tempFile);
                    for (String str : stList) {
//						out.println(str+"<br/>");
                        out.println(str);
                    }
                } else {
                    out.print("获取日志错误!");
                }
            }
            out.println("</XMP>");
            out.println("</BODY>");
            out.println("</HTML>");
        } catch (Exception e) {
            out.println("读取日志错误" + e.getMessage());
            log.error("读取日志错误" + e.getMessage());
        } finally {
            out.flush();
            out.close();
        }
    }

    /**
     * 列出所有用户 测试方法
     */
    @RequestMapping(value = "/getUsers", method = RequestMethod.GET)
    public String userList(Model model) {
        model.addAttribute("userlist", arcServcieImpl.listAllUser());
        return "userlist.jsp";
    }

    /**
     * 列出所有用户 测试方法
     */
    @RequestMapping(value = "/syncAll", method = RequestMethod.GET)
    public void syncAll(HttpServletResponse response) {
        PrintWriter out = null;
        try {
            response.setContentType("text/html;charset=GBK ");
            out = response.getWriter();
            out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
            out.println("<HTML>");
            out.println("<BODY>");
            out.println("<XMP>");
            out.println("OK");
            syncDepAndUserService.sync();
            out.println("</XMP>");
            out.println("</BODY>");
            out.println("</HTML>");
        } catch (Exception e) {
            out.println("读取日志错误" + e.getMessage());
            log.error("读取日志错误" + e.getMessage());
        } finally {
            out.flush();
            out.close();
        }
    }

    /**
     * 列出所有子单位
     */
    @RequestMapping(value = "/listAllOrg", method = RequestMethod.GET)
    public String listAllOrg(Model model) {
        List<OaCompany> oaCompanies = new ArrayList<>();
        List<SubCompanyBeanArray.SubCompanyBean> list = syncDepAndUserService.oaOrgList();
        model.addAttribute("orgList", list);
        return "orgList.jsp";
    }
    /**
     * 删除指定用户待办
     */
    @RequestMapping(value = "/deleteUserRequestInfoByJson", method = RequestMethod.GET)
    public String deleteUserRequestInfoByJson() {
        return "deleteUserRequestInfoByJson.jsp";
    }
    /**
     * 删除指定用户待办
     */
    @RequestMapping(value = "/deleteTask", method = RequestMethod.POST)
    public String deleteTask(Model model , @RequestParam String usercode, @RequestParam String taskid) {
        model.addAttribute("message" , noticeServiceImpl.deleteUserRequestInfoByJson(usercode , taskid));
        return "syncResult.jsp";
    }

    /**
     * 单点登录
     */
    @RequestMapping(value = "/sso", method = RequestMethod.GET)
    public String sso(@RequestParam String usercode, @RequestParam String token) {
        String lamsUrl = "http://" + arcServcieImpl.getLamsIP() + "/Lams/directLogin?usercode=";
        Boolean result = judgeSSO(usercode, token);
        if (result) {//返回0 表示成功
            lamsUrl = lamsUrl + usercode;
            log.error("验证成功可以登录档案系统!");
        } else {
            log.error("验证失败!");
        }
        return "redirect:" + lamsUrl;
    }


    /**
     * 流程过来的消息 需要发送到 邮件和待办
     */

    @RequestMapping(value = "/sendMsg", method = RequestMethod.POST)
    public void sendMsg(@RequestParam String userCodes, @RequestParam String varsJson, @RequestParam String actTaskID) {
        if (StringUtils.isNotEmpty(varsJson) && StringUtils.isNotEmpty(actTaskID)) {
            noticeServiceImpl.sendActivitiMsg(userCodes, varsJson, actTaskID);
        }
    }

    /**
     * 当流程结束 申请人或者流程管理员销毁流程的时候 调用销毁待办
     */
    @RequestMapping(value = "/sendDestoryMsg", method = RequestMethod.POST)
    public void sendDestoryMsg(@RequestParam String operUserCode, @RequestParam String actTaskId, @RequestParam String flowStatus) {
        if (StringUtils.isNotEmpty(operUserCode) && StringUtils.isNotEmpty(actTaskId)) {
            noticeServiceImpl.sendDestoryMsg(actTaskId, operUserCode , flowStatus);
        }
    }

    @RequestMapping("/syncDclassfy")
    public void syncDclassfy(@RequestParam Integer libcode, HttpServletResponse response) {
        PrintWriter out = null;
        try {
            response.setContentType("text/html;charset=GBK ");
            out = response.getWriter();
            out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
            out.println("<HTML>");
            out.println("<BODY>");
            out.println("<XMP>");
            out.println(arcServcieImpl.syncDclassfy(libcode));
            out.println("</XMP>");
            out.println("</BODY>");
            out.println("</HTML>");
        } catch (Exception e) {
            out.println("读取日志错误" + e.getMessage());
            log.error("读取日志错误" + e.getMessage());
        } finally {
            out.flush();
            out.close();
        }
    }

    /**
     * 内部调用 判断是否是允许用户 ture是的
     */
    private Boolean judgeSSO(String usercode, String token) {
        Boolean result = false;
        return result;
    }

    @Autowired
    private ArcService arcServcieImpl;
    @Autowired
    private NoticeService noticeServiceImpl;
    @Autowired
    private SyncDepAndUserService syncDepAndUserService;
    @Autowired
    protected SUserMapper sUserMapper;
    @Autowired
    @Value("${interface.log.home.address}")
    private String logHomeAdd;
    @Autowired
    @Value("${lams.ip}")
    private String lamsIP;
    private Logger log = (Logger) LoggerFactory.getLogger(this.getClass());
}
