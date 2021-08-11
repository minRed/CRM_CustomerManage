package com.fjminred.crm.settings.service.impl;


import com.fjminred.crm.settings.dao.DicTypeDao;
import com.fjminred.crm.settings.dao.DicValueDao;
import com.fjminred.crm.settings.domian.DicType;
import com.fjminred.crm.settings.domian.DicValue;
import com.fjminred.crm.settings.service.DicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DicServiceImpl implements DicService {


    @Autowired
    private DicTypeDao dicTypeDao;

    @Autowired
    private DicValueDao dicValueDao;

    @Override
    public Map<String, List<DicValue>> getAll() {
        Map<String, List<DicValue>> map = new HashMap<>();
        List<DicType> dtList = dicTypeDao.getTypeList();

        for (DicType dt : dtList) {
            String code = dt.getCode();

            List<DicValue> dvList = dicValueDao.getListByCode(code);

            map.put(code+"List",dvList);
        }

        return map;

    }
}
