package com.lcgblog.study.spring.ioc.overview.dependency.inject;

import com.lcgblog.study.spring.ioc.overview.repository.UserRepository;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DependencyInjectDemo {
    public static void main(String[] args) {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/dependency-inject-context.xml");
        UserRepository userRepository = beanFactory.getBean(UserRepository .class);
        System.out.println(userRepository.getUserList());
        System.out.println(userRepository.getBeanFactory() == beanFactory); // false
        System.out.println(userRepository.getBeanFactory() == ((ApplicationContext)beanFactory).getParentBeanFactory()); // false
        System.out.println(userRepository.getBeanFactory() == ((AbstractApplicationContext)beanFactory).getBeanFactory()); // true
        //because application is not BeanFactory itself, it just implements the BeanFactory interface
        // and combines with a real beanFactory to realize the BeanFactory interface
    }
}
