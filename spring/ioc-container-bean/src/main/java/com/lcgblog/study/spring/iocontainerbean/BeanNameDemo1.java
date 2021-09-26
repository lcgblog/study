package com.lcgblog.study.spring.iocontainerbean;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanNameDemo1 {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:bean.xml");
        System.out.println(applicationContext.getBean("user1"));
        System.out.println(applicationContext.getBean("user2"));
        System.out.println(applicationContext.getBean("user3"));
        System.out.println(applicationContext.getBean("user1") == applicationContext.getBean("user2"));

        //org.springframework.beans.factory.support.DefaultBeanNameGenerator
        System.out.println(applicationContext.getBean("com.lcgblog.study.spring.ioc.overview.domain.User#0"));
        System.out.println(applicationContext.getBean("com.lcgblog.study.spring.ioc.overview.domain.User#1"));
    }

}
