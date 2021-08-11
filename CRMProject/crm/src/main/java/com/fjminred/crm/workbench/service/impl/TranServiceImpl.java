package com.fjminred.crm.workbench.service.impl;

import com.fjminred.crm.utils.DateTimeUtil;
import com.fjminred.crm.utils.UUIDUtil;
import com.fjminred.crm.workbench.dao.CustomerDao;
import com.fjminred.crm.workbench.dao.TranDao;
import com.fjminred.crm.workbench.dao.TranHistoryDao;
import com.fjminred.crm.workbench.domain.Customer;
import com.fjminred.crm.workbench.domain.Tran;
import com.fjminred.crm.workbench.domain.TranHistory;
import com.fjminred.crm.workbench.service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TranServiceImpl implements TranService {

    @Autowired
    private TranDao tranDao;

    @Autowired
    private TranHistoryDao tranHistoryDao;

    @Autowired
    private CustomerDao customerDao;

    @Override
    public boolean save(Tran t, String customerName) {

        boolean flag = true;

        Customer cus = customerDao.getCustomerByName(customerName);

        if (cus == null){

            cus = new Customer();
            cus.setId(UUIDUtil.getUUID());
            cus.setCreateTime(DateTimeUtil.getSysTime());
            cus.setCreateBy(t.getCreateBy());
            cus.setName(customerName);
            cus.setContactSummary(t.getContactSummary());
            cus.setNextContactTime(t.getNextContactTime());
            cus.setOwner(t.getOwner());

            int count1 = customerDao.save(cus);
            if (count1 != 1) {
                flag = false;
            }
        }

        t.setCustomerId(cus.getId());

        int count2 = tranDao.save(t);
        if (count2 != 1) {
            flag = false;
        }

        TranHistory th = new TranHistory();
        th.setId(UUIDUtil.getUUID());
        th.setTranId(t.getId());
        th.setStage(t.getStage());
        th.setMoney(t.getMoney());
        th.setExpectedDate(t.getExpectedDate());
        th.setCreateTime(DateTimeUtil.getSysTime());
        th.setCreateBy(t.getCreateBy());
        int count3 = tranHistoryDao.save(th);
        if (count3 != 1) {
            flag = false;
        }


        return flag;
    }

    @Override
    public Tran detail(String id) {
        Tran t = tranDao.detail(id);
        return t;
    }

    @Override
    public List<TranHistory> getHistoryListByTranId(String tranId) {

        List<TranHistory> thList = tranHistoryDao.getHistoryListByTranId(tranId);

        return thList;
    }

    @Override
    public boolean changeStage(Tran t) {

        boolean flag = true;

        int count  = tranDao.changeStage(t);

        if (count !=1){
            flag = false;
        }

        TranHistory th = new TranHistory();
        th.setId(UUIDUtil.getUUID());
        th.setTranId(t.getId());
        th.setCreateBy(t.getEditBy());
        th.setCreateTime(t.getCreateTime());
        th.setMoney(t.getMoney());
        th.setExpectedDate(t.getExpectedDate());

        int count2 = tranHistoryDao.save(th);
        if (count2 !=1){
            flag = false;
        }

        return flag;
    }

    @Override
    public Map<String, Object> getCharts() {
        int total = tranDao.getTotal();

        List<Map<String,Object>> dataList = tranDao.getCharts();

        Map<String,Object> map = new HashMap<>();
        map.put("total",total);
        map.put("dataList",dataList);

        return map;

    }
}
