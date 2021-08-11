package com.fjminred.crm.settings.dao;

import com.fjminred.crm.settings.domian.User;

import java.util.List;
import java.util.Map;

public interface UserDao {

    User login(Map<String, Object> map);

    List<User> getUserList();
}
