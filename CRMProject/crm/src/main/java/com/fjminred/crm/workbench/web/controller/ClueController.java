package com.fjminred.crm.workbench.web.controller;


import com.fjminred.crm.settings.domian.User;
import com.fjminred.crm.settings.service.UserService;
import com.fjminred.crm.utils.DateTimeUtil;
import com.fjminred.crm.utils.UUIDUtil;
import com.fjminred.crm.workbench.dao.ClueActivityRelationDao;
import com.fjminred.crm.workbench.domain.Activity;
import com.fjminred.crm.workbench.domain.Clue;
import com.fjminred.crm.workbench.domain.Tran;
import com.fjminred.crm.workbench.service.ActivityService;
import com.fjminred.crm.workbench.service.ClueService;
import org.omg.PortableInterceptor.ACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ClueController {

    @Autowired
    private UserService us;

    @Autowired
    private ClueService cs;

    @Autowired
    private ActivityService as;


    @RequestMapping("/workbench/clue/getUserList.do")
    @ResponseBody
    public List<User> getUserList(){
        List<User> uList = us.getUserList();
        return uList;
    }

    @RequestMapping("/workbench/clue/save.do")
    @ResponseBody
    public String save(HttpServletRequest request, Clue clue){

        String id = UUIDUtil.getUUID();
//        String fullname = request.getParameter("fullname");
//        String appellation = request.getParameter("appellation");
//        String owner = request.getParameter("owner");
//        String company = request.getParameter("company");
//        String job = request.getParameter("job");
//        String email = request.getParameter("email");
//        String phone = request.getParameter("phone");
//        String website = request.getParameter("website");
//        String mphone = request.getParameter("mphone");
//        String state = request.getParameter("state");
//        String source = request.getParameter("source");
        String createBy = ((User)request.getSession().getAttribute("user")).getName();
        String createTime = DateTimeUtil.getSysTime();
//        String description = request.getParameter("description");
//        String contactSummary = request.getParameter("contactSummary");
//        String nextContactTime = request.getParameter("nextContactTime");
//        String address = request.getParameter("address");

        clue.setId(id);
        clue.setCreateBy(createBy);
        clue.setCreateTime(createTime);

        boolean flag = cs.save(clue);

        return flag + "";

    }

    @RequestMapping("/workbench/clue/detail.do")
    public ModelAndView detail(String id){


        ModelAndView mv = new ModelAndView();

        Clue c = cs.detail(id);

        mv.addObject("c",c);

        mv.setViewName("/workbench/clue/detail.jsp");

        return mv;

    }

    @RequestMapping("/workbench/clue/getActivityListByClueId.do")
    @ResponseBody
    public List<Activity> getActivityListByClueId(String clueId){

        List<Activity> aList = as.getActivityListByClueId(clueId);
        return aList;
    }

    @RequestMapping("/workbench/clue/unbund.do")
    @ResponseBody
    public String unbund(String id){

        boolean flag = cs.unbund(id);
        return flag + "";

    }

    @RequestMapping("/workbench/clue/getActivityListByNameAndNotByClueId.do")
    @ResponseBody
    public List<Activity> getActivityListByNameAndNotByClueId(String aname,String clueId){

        Map<String,String> map = new HashMap<>();
        map.put("aname", aname);
        map.put("clueId",clueId);

        List<Activity> aList = as.getActivityListByNameAndNotByClueId(map);

        return aList;

    }

    @RequestMapping("/workbench/clue/bund.do")
    @ResponseBody
    public String bund(HttpServletRequest request){

        String cid = request.getParameter("cid");
        String[] aids = request.getParameterValues("aid");

        boolean flag = cs.bund(cid,aids);
        return flag + "";

    }

    @RequestMapping("/workbench/clue/getAcitivityListByName.do")
    @ResponseBody
    public List<Activity> getAcitivityListByName(String aname){

        List<Activity> aList = as.getAcitivityListByName(aname);


        return aList;

    }

    @RequestMapping("/workbench/clue/convert.do")
    public void convert(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String clueId = request.getParameter("clueId");

        String flag = request.getParameter("flag");
        String createBy = ((User)request.getSession().getAttribute("user")).getName();

        Tran t = null;

        if ("a".equals(flag)){

            t = new Tran();

            String money = request.getParameter("money");
            String name = request.getParameter("name");
            String expectedDate = request.getParameter("expectedDate");
            String stage = request.getParameter("stage");
            String activityId = request.getParameter("activityId");
            String id = UUIDUtil.getUUID();
            String createTime = DateTimeUtil.getSysTime();

            t.setId(id);
            t.setId(money);
            t.setName(name);
            t.setActivityId(activityId);
            t.setExpectedDate(expectedDate);
            t.setStage(stage);
            t.setCreateBy(createBy);
            t.setCreateTime(createTime);

        }

        boolean flag1 = cs.convert(clueId,t,createBy);

        if (flag1){

            response.sendRedirect(request.getContextPath() + "/workbench/clue/index.jsp");

        }


    }
}
