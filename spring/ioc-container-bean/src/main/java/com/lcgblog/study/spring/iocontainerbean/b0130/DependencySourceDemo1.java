package com.lcgblog.study.spring.iocontainerbean.b0130;

import com.lcgblog.study.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

//lifecycle(bean definition): yes, lookup: yes, inject: yes
//registerDefinition
public class DependencySourceDemo1 {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext();
        applicationContext.refresh();
        BeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(User.class)
                .addPropertyValue("id",1)
                .addPropertyValue("name","Tom")
                .getBeanDefinition();
        applicationContext.registerBeanDefinition("user", beanDefinition);
        applicationContext.registerBeanDefinition("user2", beanDefinition);
        System.out.println(applicationContext.getBean("user"));
        System.out.println(applicationContext.getBean("user2"));
        Arrays.stream(applicationContext.getBeanDefinitionNames())
                .forEach(System.out::println);
    }
}
