package com.lcgblog.study.spring.iocontainerbean.a0927;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;

//bean的初始化
public class BeanInitializationDemo1 implements InitializingBean {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        BeanDefinitionRegistry registry = (BeanDefinitionRegistry)applicationContext.getBeanFactory();
        BeanDefinitionReaderUtils.registerWithGeneratedName(BeanDefinitionBuilder
                .genericBeanDefinition(BeanInitializationDemo1.class).setInitMethodName("init").getBeanDefinition(), registry);
        applicationContext.refresh();
        applicationContext.close();


        //自定义初始化方法(XML,Java注解,JavaAPI)
        //问题：以上三种的执行顺序

    }

    //1. @PostConstruct注解
    @PostConstruct
    public void postConstruct(){
        System.out.println("Init with postConstruct");
    }

    //2. 实现InitializingBean接口的afterPropertiesSet()
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Init with InitializingBean");
    }

    public void init(){
        System.out.println("customized init");
    }
}
