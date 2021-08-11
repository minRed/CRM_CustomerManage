package com.fjminred.crm.settings.web.controller;

import com.fjminred.crm.settings.domian.User;
import com.fjminred.crm.settings.service.UserService;
import com.fjminred.crm.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@Controller
public class UserController{

    @Autowired
    private UserService service;

    @RequestMapping("/settings/user/login.do")
    @ResponseBody
    public Map<String,Object> login(HttpServletRequest request,String loginAct, String loginPwd){

        String loginA = loginAct;
        String loginP = MD5Util.getMD5(loginPwd);
        String ip = request.getRemoteAddr();
        Map<String,Object> map = new HashMap<String,Object>();
        try{
            User user = service.login(loginA,loginP,ip);
            request.getSession().setAttribute("user",user);
            map.put("success",true);
        }catch (Exception e){
            e.printStackTrace();
            String msg = e.getMessage();
            map.put("success",false);
            map.put("msg",msg);
        }

        return map;
    }

}
