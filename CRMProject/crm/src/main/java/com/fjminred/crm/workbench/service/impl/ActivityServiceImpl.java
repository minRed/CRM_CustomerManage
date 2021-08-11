package com.fjminred.crm.workbench.service.impl;

import com.fjminred.crm.settings.dao.UserDao;
import com.fjminred.crm.settings.domian.User;
import com.fjminred.crm.vo.PaginationVO;
import com.fjminred.crm.workbench.dao.ActivityDao;
import com.fjminred.crm.workbench.dao.ActivityRemarkDao;
import com.fjminred.crm.workbench.domain.Activity;
import com.fjminred.crm.workbench.domain.ActivityRemark;
import com.fjminred.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ActivityServiceImpl  implements ActivityService {

    @Autowired
    private ActivityDao activityDao;

    @Autowired
    private ActivityRemarkDao activityRemarkDao;

    @Autowired
    private UserDao userDao;

    @Override
    public boolean save(Activity activity) {

        boolean flag = true;

        int count = activityDao.save(activity);

        if (count != 1){
            flag = false;
        }

        return flag;
    }

    @Override
    public PaginationVO<Activity> pageList(Map<String, Object> map) {

        int total = activityDao.getTotalByCondition(map);


        List<Activity> dataList = activityDao.getActivityByCondition(map);

        PaginationVO<Activity> vo = new PaginationVO<>();
        vo.setTotal(total);
        vo.setDataList(dataList);

        return vo;
    }

    @Override
    public Activity detail(String id) {

        Activity a = activityDao.detail(id);
        return a;
    }

    @Override
    public List<ActivityRemark> getRemarkListByAid(String id) {
        List<ActivityRemark> activityRemarks = activityRemarkDao.getRemarkListByAid(id);
        return activityRemarks;
    }

    @Override
    public boolean deleteRemark(String id) {

        boolean flag = true;
        int i = activityRemarkDao.deleteRemark(id);

        if (i != 1){
            flag = false;
        }

        return flag;
    }

    @Override
    public boolean saveRemark(ActivityRemark ar) {
        boolean flag = true;

        int count = activityRemarkDao.saveRemark(ar);

        if (count != 1) {
            flag = false;
        }
        return flag;
    }

    @Override
    public boolean updateRemark(ActivityRemark ar) {
        boolean flag = true;

        int count = activityRemarkDao.updateRemark(ar);

        if (count != 1) {
            flag = false;
        }
        return flag;
    }

    @Override
    public List<Activity> getActivityListByClueId(String clueId) {

        List<Activity> aList = activityDao.getActivityListByClueId(clueId);
        return aList;
    }

    @Override
    public List<Activity> getActivityListByNameAndNotByClueId(Map<String, String> map) {

        List<Activity> aList = activityDao.getActivityListByNameAndNotByClueId(map);

        return aList;
    }

    @Override
    public List<Activity> getAcitivityListByName(String aname) {
        List<Activity> aList = activityDao.getAcitivityListByName(aname);
        return aList;
    }

    @Override
    public boolean update(Activity activity) {
        boolean flag = true;

        int count = activityDao.update(activity);

        if (count != 1){
            flag = false;
        }

        return flag;
    }

    @Override
    public boolean delete(String[] ids) {

        boolean flag = true;

        int count1 = activityRemarkDao.getCountByAids(ids);

        int count2 = activityRemarkDao.deleteByAids(ids);

        if(count1!=count2){
            flag = false;
        }

        int count3 = activityDao.delete(ids);

        if(count3!=ids.length){
            flag = false;
        }

        return flag;
    }

    @Override
    public Map<String, Object> getUserListAndActivity(String id) {

        List<User> userList = userDao.getUserList();

        Activity a = activityDao.getById(id);

        Map<String, Object> map = new HashMap<>();
        map.put("uList",userList);
        map.put("a",a);

        return map;
    }


}
