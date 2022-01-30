package com.lcgblog.study.spring.iocontainerbean.b0126;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

//lifecycle(bean definition): no, lookup: yes, inject: yes
//registerSingleton
@Configuration
public class DependencySourceDemo2 {

    @Autowired
    private String testStr;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext();
        ((DefaultListableBeanFactory)applicationContext.getBeanFactory())
                .registerSingleton("testStr","hello");
        applicationContext.register(DependencySourceDemo2.class);
        applicationContext.refresh();
        System.out.println(applicationContext.getBean(DependencySourceDemo2.class).testStr);
        System.out.println(applicationContext.getBean("testStr"));
    }

}
