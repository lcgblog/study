package com.lcgblog.study.spring.ioc.overview.container;

import com.lcgblog.study.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

import java.util.Map;

/**
 * {@link BeanFactory} as IoC Container
 */
public class IocContainerDemo {
    public static void main(String[] args) {
        //create BeanFactory container
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        //load configuration
        int count = reader.loadBeanDefinitions("classpath:/META-INF/dependency-lookup-context.xml");
        System.out.printf("Bean loaded count %s", count);
        //dependency lookup
        Map<String, User> beansOfType = beanFactory.getBeansOfType(User.class);
        System.out.println(beansOfType);
    }
}
