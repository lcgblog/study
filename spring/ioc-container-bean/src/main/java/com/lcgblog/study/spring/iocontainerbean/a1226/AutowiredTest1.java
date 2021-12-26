package com.lcgblog.study.spring.iocontainerbean.a1226;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AutowiredTest1 {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("a1226/autowired-test1.xml");
        applicationContext.refresh();
        System.out.println(applicationContext.getBean("user"));
        applicationContext.close();
    }
}
