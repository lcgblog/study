package com.lcgblog.study.spring.iocontainerbean;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanAliasDemo1 {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:bean.xml");
        System.out.println(applicationContext.getBean("user1"));
        System.out.println(applicationContext.getBean("user1") == applicationContext.getBean("subSystemA-user"));
    }
}
