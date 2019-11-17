package com.util;

import com.dao.SSQDao;
import com.entity.SSQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class ContextUtils  {



    private static ApplicationContext context;

    public  static ApplicationContext getContext(){
        if(context==null){
            context = new ClassPathXmlApplicationContext("spring-mybatis.xml");
        }
        return context;
    }

    public static void main(String[] args) {
        ApplicationContext context = ContextUtils.getContext();
        System.out.println(context);
        System.out.println(context.getBean(SSQDao.class));
    }




}
