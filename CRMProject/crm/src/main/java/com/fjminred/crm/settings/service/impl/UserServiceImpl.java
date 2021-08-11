package com.fjminred.crm.settings.service.impl;

import com.fjminred.crm.exception.LoginExecption;
import com.fjminred.crm.settings.dao.UserDao;
import com.fjminred.crm.settings.domian.User;
import com.fjminred.crm.settings.service.UserService;
import com.fjminred.crm.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserDao userDao;

    @Override
    public User login(String loginA, String loginP, String ip) throws LoginExecption {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("loginA",loginA);
        map.put("loginP",loginP);

        User user = userDao.login(map);

        if (user == null){
            throw  new LoginExecption("账号或密码错误");
        }

        String expireTime = user.getExpireTime();
        String currentTime = DateTimeUtil.getSysTime();
        if(expireTime.compareTo(currentTime) < 0){
            throw new LoginExecption("账号已失效");
        }

        String lockState = user.getLockState();
        if ("0".equals(lockState)){
            throw new LoginExecption("账号处于锁定状态");
        }

        String allowIps = user.getAllowIps();
        if (!allowIps.contains(ip)){
            throw new LoginExecption("ip地址受限");
        }

        return user;
    }

    @Override
    public List<User> getUserList() {

        List<User> users = userDao.getUserList();

        return users;
    }
}
