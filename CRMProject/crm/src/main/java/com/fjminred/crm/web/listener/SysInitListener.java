package com.fjminred.crm.web.listener;

import com.fjminred.crm.settings.domian.DicValue;
import com.fjminred.crm.settings.service.DicService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.*;

public class SysInitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {

        System.out.println("上下域文件被创建了");

        ServletContext application = event.getServletContext();

        String config = "applicationContext.xml";

        ApplicationContext ac = new ClassPathXmlApplicationContext(config);
        DicService ds = (DicService) ac.getBean("dicService");

        Map<String, List<DicValue>> map = ds.getAll();
        Set<String> set = map.keySet();
        for (String key : set){
            application.setAttribute(key,map.get(key));

        }


        Map<String,String> pMap = new HashMap<>();
        ResourceBundle rb = ResourceBundle.getBundle("Stage2Possibility");

        Enumeration<String> keys = rb.getKeys();

        while (keys.hasMoreElements()){
            String key = keys.nextElement();
            String value = rb.getString(key);
            pMap.put(key,value);
        }

        application.setAttribute("pMap",pMap);

        System.out.println("上下域文件被创建了2");


    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {

    }
}
