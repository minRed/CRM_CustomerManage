package com.fjminred.crm.workbench.web.controller;

import com.fjminred.crm.settings.domian.User;
import com.fjminred.crm.settings.service.UserService;
import com.fjminred.crm.utils.DateTimeUtil;
import com.fjminred.crm.utils.UUIDUtil;
import com.fjminred.crm.workbench.domain.Tran;
import com.fjminred.crm.workbench.domain.TranHistory;
import com.fjminred.crm.workbench.service.CustomerService;
import com.fjminred.crm.workbench.service.TranService;
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
public class TranController {

    @Autowired
    private CustomerService cs;

    @Autowired
    private UserService us;

    @Autowired
    private TranService ts;

    @RequestMapping("/workbench/transaction/add.do")
    public ModelAndView add(){

        ModelAndView mv = new ModelAndView();

        List<User> uList = us.getUserList();

        mv.addObject("uList",uList);

        mv.setViewName("/workbench/transaction/save.jsp");

        return mv;

    }

    @RequestMapping("/workbench/transaction/getCustomerName.do")
    @ResponseBody
    public List<String> getCustomerName(String name) {

        List<String> sList = cs.getCustomerName(name);

        return sList;

    }

    @RequestMapping("/workbench/transaction/save.do")
    @ResponseBody
    public void save(HttpServletRequest request, HttpServletResponse response, Tran t) throws IOException {

       t.setId(UUIDUtil.getUUID());
       t.setCreateTime(DateTimeUtil.getSysTime());
       t.setCreateBy(((User)request.getSession().getAttribute("user")).getName());
       String customerName = request.getParameter("customerName");

       boolean flag = ts.save(t,customerName);

       if (flag){
           response.sendRedirect(request.getContextPath() + "/workbench/transaction/index.jsp");
       }
    }

    @RequestMapping("/workbench/transaction/detail.do")
    public ModelAndView detail(HttpServletRequest request,String id){
        ModelAndView mv = new ModelAndView();
        Tran t = ts.detail(id);
        String stage = t.getStage();
        Map<String,String> pMap = (Map<String, String>) request.getServletContext().getAttribute("pMap");
        String possibility = pMap.get(stage);

        t.setPossibility(possibility);
        mv.addObject("t",t);
        mv.setViewName("/workbench/transaction/detail.jsp");

        return mv;


    }

    @RequestMapping("/workbench/transaction/getHistoryListByTranId.do")
    @ResponseBody
    public List<TranHistory> getHistoryListByTranId(HttpServletRequest request,String tranId){

        List<TranHistory> thList = ts.getHistoryListByTranId(tranId);
        Map<String,String> pMap = (Map<String, String>) request.getServletContext().getAttribute("pMap");

        for(TranHistory th : thList){
            String stage = th.getStage();
            String possibility = pMap.get(stage);
            th.setPossibility(possibility);

        }
        return thList;
    }

    @RequestMapping("/workbench/transaction/changeStage.do")
    @ResponseBody
    public Map<String, Object> changeStage(HttpServletRequest request,Tran t) {

        String editTime = DateTimeUtil.getSysTime();
        String editBy = ((User)request.getSession().getAttribute("user")).getName();
        t.setEditBy(editBy);
        t.setEditTime(editTime);
        Map<String,String> pMap = (Map<String, String>) request.getServletContext().getAttribute("pMap");
        t.setPossibility(pMap.get(t.getStage()));

        boolean flag = ts.changeStage(t);

        Map<String,Object> map = new HashMap<String, Object>();
        map.put("success",flag);
        map.put("t",t);

        return map;
    }


    @RequestMapping("/workbench/transaction/getCharts.do")
    @ResponseBody
    public Map<String,Object> getCharts(){

        Map<String,Object> map = ts.getCharts();

        return map;
    }
}
