package com.fjminred.crm.settings.dao;

import com.fjminred.crm.settings.domian.DicValue;

import java.util.List;

public interface DicValueDao {

    List<DicValue> getListByCode(String code);
}
