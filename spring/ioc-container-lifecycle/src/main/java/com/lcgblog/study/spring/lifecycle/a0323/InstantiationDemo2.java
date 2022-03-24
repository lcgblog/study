package com.lcgblog.study.spring.lifecycle.a0323;

import com.lcgblog.study.spring.lifecycle.beans.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

// Xml方式实例化
// 1，BD注册
// BeanDefinition注册关键类 org.springframework.beans.factory.xml.XmlBeanDefinitionReader.registerBeanDefinitions
// => org.springframework.beans.factory.xml.DefaultBeanDefinitionDocumentReader.processBeanDefinition
// => BeanDefinitionReaderUtils.registerBeanDefinition(bdHolder, getReaderContext().getRegistry());
// 2，验证第二次实例化 使用的是resolved
public class InstantiationDemo2 {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("classpath:instantiation_beans.xml");
        System.out.println(applicationContext.getBean("user"));
        //org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBeanInstance
        //第二次直接跑resolved下的instantiateBean(beanName, mbd);
        System.out.println(applicationContext.getBean("user"));
    }

}
