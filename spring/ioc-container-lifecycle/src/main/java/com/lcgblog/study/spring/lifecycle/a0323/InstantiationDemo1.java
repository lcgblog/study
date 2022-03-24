package com.lcgblog.study.spring.lifecycle.a0323;

import com.lcgblog.study.spring.lifecycle.beans.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

//注解方式实例化，关键类:
// ConfigurationClassBeanDefinitionReader.loadBeanDefinitionsForBeanMethod
@Configuration
public class InstantiationDemo1 {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(InstantiationDemo1.class);
        applicationContext.refresh();
        //debug on org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean
        //相当于使用InstantiationDemo1.user()工厂方法实例化bean
        System.out.println(applicationContext.getBean("user"));
    }

    @Bean
    public User user(){
        User user = new User();
        user.setName("Tom");
        user.setId(1L);
        user.setList(List.of("111","222"));
        return user;
    }

}
