package com.lcgblog.study.spring.iocontainerbean.b0126;

import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

//lifecycle(bean definition): no, lookup: no, inject: yes
//registerResolvableDependency
@Configuration
public class DependencySourceDemo3 {

    @Autowired
    private String testStr;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext();
        ((DefaultListableBeanFactory)applicationContext.getBeanFactory())
                .registerResolvableDependency(String.class,"hello2");
        applicationContext.register(DependencySourceDemo3.class);
        applicationContext.refresh();
        System.out.println(applicationContext.getBean(DependencySourceDemo3.class).testStr);
        System.out.println(((ListableBeanFactory)applicationContext).getBeansOfType(String.class));//{}
    }

}
