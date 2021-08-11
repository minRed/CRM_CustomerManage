package com.fjminred.crm.settings.service;

import com.fjminred.crm.exception.LoginExecption;
import com.fjminred.crm.settings.domian.User;

import java.util.List;

public interface UserService {
    User login(String loginA, String loginP, String ip) throws LoginExecption;

    List<User> getUserList();
}
