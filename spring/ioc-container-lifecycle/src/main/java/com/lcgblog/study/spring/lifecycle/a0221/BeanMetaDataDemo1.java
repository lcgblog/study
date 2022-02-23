package com.lcgblog.study.spring.lifecycle.a0221;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;

//面向资源元信息配置
public class BeanMetaDataDemo1 {

    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        PropertiesBeanDefinitionReader beanDefinitionReader =
                new PropertiesBeanDefinitionReader(beanFactory);
        int loadedCount = beanDefinitionReader.loadBeanDefinitions(
                new EncodedResource(new ClassPathResource("META-INF/user.properties"), "UTF-8"));
        System.out.println("Loaded " + loadedCount + " beans");
        System.out.println(beanFactory.getBean("user"));
    }

}
