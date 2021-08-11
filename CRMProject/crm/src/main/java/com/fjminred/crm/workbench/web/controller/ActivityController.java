package com.fjminred.crm.workbench.web.controller;


import com.fjminred.crm.settings.domian.User;
import com.fjminred.crm.settings.service.UserService;
import com.fjminred.crm.utils.DateTimeUtil;
import com.fjminred.crm.utils.UUIDUtil;
import com.fjminred.crm.vo.PaginationVO;
import com.fjminred.crm.workbench.domain.Activity;
import com.fjminred.crm.workbench.domain.ActivityRemark;
import com.fjminred.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ActivityController {

    @Autowired
    private UserService us;

    @Autowired
    private ActivityService as;

    @RequestMapping("/workbench/activity/getUserList.do")
    @ResponseBody
    public List<User> getUserList() {

        List<User> user = us.getUserList();
        return user;
    }

    @RequestMapping("/workbench/activity/save.do")
    @ResponseBody
    public String save(HttpServletRequest request) {


        String id = UUIDUtil.getUUID();
        String owner = request.getParameter("owner");
        String name = request.getParameter("name");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String cost = request.getParameter("cost");
        String description = request.getParameter("description");
        String createTime = DateTimeUtil.getSysTime();

        String createBy = ((User) request.getSession().getAttribute("user")).getName();

        Activity activity = new Activity();

        activity.setId(id);
        activity.setOwner(owner);
        activity.setName(name);
        activity.setStartDate(startDate);
        activity.setEndDate(endDate);
        activity.setCost(cost);
        activity.setDescription(description);
        activity.setCreateTime(createTime);
        activity.setCreateBy(createBy);

        boolean flag = as.save(activity);

        return flag + "";
    }

    @RequestMapping("/workbench/activity/pageList.do")
    @ResponseBody
    public PaginationVO<Activity> pageList(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String name = request.getParameter("name");
        String owner = request.getParameter("owner");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        String pageNoStr = request.getParameter("pageNo");
        int pageNo = Integer.valueOf(pageNoStr);
        String pageSizeStr = request.getParameter("pageSize");
        int pageSize = Integer.valueOf(pageSizeStr);
        int skipCount = (pageNo - 1) * pageSize;

        map.put("name", name);
        map.put("owner", owner);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("skipCount", skipCount);
        map.put("pageSize", pageSize);

        PaginationVO<Activity> vo = as.pageList(map);

        return vo;

    }

    @RequestMapping("/workbench/activity/delete.do")
    @ResponseBody
    public String delete(HttpServletRequest request) {

        String[] ids = request.getParameterValues("id");

        boolean flag = as.delete(ids);

        return flag + "";
    }

    @RequestMapping("/workbench/activity/getUserListAndActivity.do")
    @ResponseBody
    public Map<String, Object> getUserListAndActivity(HttpServletRequest request) {

        String id = request.getParameter("id");

        Map<String, Object> map = as.getUserListAndActivity(id);

        return map;
    }

    @RequestMapping("/workbench/activity/update.do")
    @ResponseBody
    public String update(HttpServletRequest request) {


        String id = request.getParameter("id");
        String owner = request.getParameter("owner");
        String name = request.getParameter("name");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String cost = request.getParameter("cost");
        String description = request.getParameter("description");

        String editTime = DateTimeUtil.getSysTime();

        String editBy = ((User) request.getSession().getAttribute("user")).getName();

        Activity activity = new Activity();

        activity.setId(id);
        activity.setOwner(owner);
        activity.setName(name);
        activity.setStartDate(startDate);
        activity.setEndDate(endDate);
        activity.setCost(cost);
        activity.setDescription(description);
        activity.setEditTime(editTime);
        activity.setEditBy(editBy);

        boolean flag = as.update(activity);

        return flag + "";
    }

    @RequestMapping("/workbench/activity/detail.do")
    public ModelAndView detail(String id) {
        ModelAndView mv = new ModelAndView();
        Activity a = as.detail(id);

        mv.addObject("a", a);

        mv.setViewName("/workbench/activity/detail.jsp");
        return mv;

    }

    @RequestMapping("/workbench/activity/getRemarkListByAid.do")
    @ResponseBody
    public List<ActivityRemark> getRemarkListByAid(HttpServletRequest request) {

        String id = request.getParameter("activityId");

        List<ActivityRemark> arList = as.getRemarkListByAid(id);

        return arList;

    }

    @RequestMapping("workbench/activity/deleteRemark.do")
    @ResponseBody
    public String deleteRemark(String id) {


        boolean flag = as.deleteRemark(id);

        return flag + "";
    }

    @RequestMapping("/workbench/activity/saveRemark.do")
    @ResponseBody
    public Map<String, Object> saveRemark(HttpServletRequest request) {

        String noteContent = request.getParameter("noteContent");
        String activityId = request.getParameter("activityId");
        String id = UUIDUtil.getUUID();
        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User) request.getSession().getAttribute("user")).getName();
        String editFlag = "0";

        ActivityRemark ar = new ActivityRemark();
        ar.setId(id);
        ar.setNoteContent(noteContent);
        ar.setActivityId(activityId);
        ar.setCreateTime(createTime);
        ar.setCreateBy(createBy);
        ar.setEditFlag(editFlag);

        boolean flag = as.saveRemark(ar);

        Map<String, Object> map = new HashMap<>();
        map.put("success", flag);
        map.put("ar", ar);

        return map;

    }

    @RequestMapping("/workbench/activity/updateRemark.do")
    @ResponseBody
    public Map<String, Object> updateRemark(HttpServletRequest request) {

        String id = request.getParameter("id");
        String noteContent = request.getParameter("noteContent");
        String editTime = DateTimeUtil.getSysTime();
        String editBy = ((User) request.getSession().getAttribute("user")).getName();
        String editFlag = "1";

        ActivityRemark ar = new ActivityRemark();
        ar.setId(id);
        ar.setNoteContent(noteContent);
        ar.setEditTime(editTime);
        ar.setEditBy(editBy);
        ar.setEditFlag(editFlag);

        boolean flag = as.updateRemark(ar);

        Map<String, Object> map = new HashMap<>();
        map.put("success", flag);
        map.put("ar", ar);

        return map;
    }
}