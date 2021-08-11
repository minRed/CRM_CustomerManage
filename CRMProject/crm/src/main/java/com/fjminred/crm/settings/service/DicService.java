package com.fjminred.crm.settings.service;

import com.fjminred.crm.settings.domian.DicValue;

import java.util.List;
import java.util.Map;

public interface DicService {


    Map<String, List<DicValue>> getAll();
}
