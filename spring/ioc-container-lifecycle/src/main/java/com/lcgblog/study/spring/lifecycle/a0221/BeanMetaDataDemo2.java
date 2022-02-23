package com.lcgblog.study.spring.lifecycle.a0221;

import com.lcgblog.study.spring.lifecycle.beans.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;

//面向注解元信息配置
@Configuration
public class BeanMetaDataDemo2 {

    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        AnnotatedBeanDefinitionReader beanDefinitionReader =
                new AnnotatedBeanDefinitionReader(beanFactory);
        int countBefore = beanFactory.getBeanDefinitionCount();
        beanDefinitionReader.register(BeanMetaDataDemo2.class);
        int countAfter = beanFactory.getBeanDefinitionCount();
        System.out.println("loaded " + (countAfter - countBefore) + " beans");
        System.out.println(beanFactory.getBean(BeanMetaDataDemo2.class));
    }

}
