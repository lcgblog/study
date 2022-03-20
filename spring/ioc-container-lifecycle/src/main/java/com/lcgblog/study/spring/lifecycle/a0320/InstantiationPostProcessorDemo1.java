package com.lcgblog.study.spring.lifecycle.a0320;

import com.lcgblog.study.spring.lifecycle.beans.SuperUser;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

//Bean实例化前，对实例进行替换
public class InstantiationPostProcessorDemo1 {

    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.addBeanPostProcessor(new MyBeanPostProcessor());
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        beanDefinitionReader.loadBeanDefinitions("classpath:merged_beans.xml");
        System.out.println(beanFactory.getBean("user"));
        System.out.println(beanFactory.getBean("superUser"));
    }

    static class MyBeanPostProcessor implements InstantiationAwareBeanPostProcessor{

        @Override
        public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
            System.out.println(beanName);
            System.out.println(bean);
            if(bean instanceof SuperUser){
                //to test if it can override super user created from xml definition
                return new SuperUser();
            }
            return null;
        }
    }

}
