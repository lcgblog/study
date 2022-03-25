package com.lcgblog.study.spring.lifecycle.a0324;

import com.lcgblog.study.spring.lifecycle.beans.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import java.util.List;

//验证InstantiationAware接口的实例后处理功能
//主要功能：对bean实例在赋值前进行操作，并且可以取消默认赋值操作
public class InstantiationPostProcessDemo1 {

    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory =
                new DefaultListableBeanFactory();
        BeanDefinitionBuilder userBD = BeanDefinitionBuilder.rootBeanDefinition(User.class)
                .addPropertyValue("id", 1L)
                .addPropertyValue("name", "Tom")
                .addPropertyValue("list", List.of("aaa", "bbb"));
        beanFactory.registerBeanDefinition("user", userBD.getBeanDefinition());
        beanFactory.registerBeanDefinition("user2", userBD.getBeanDefinition());
        System.out.println(beanFactory.getBean("user"));
        System.out.println(beanFactory.getBean("user2"));
        beanFactory.addBeanPostProcessor(new MyPostProcessor());
        beanFactory.registerBeanDefinition("user", userBD.getBeanDefinition());
        beanFactory.registerBeanDefinition("user2", userBD.getBeanDefinition());
        System.out.println(beanFactory.getBean("user"));
        System.out.println(beanFactory.getBean("user2"));
    }

    static class MyPostProcessor implements SmartInstantiationAwareBeanPostProcessor {
        @Override
        public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
            if (bean instanceof User && "user".equals(beanName)) {
                User user = (User)bean;
                user.setId(2L);
                user.setName("EnhancedTom");
                //indicate not to fulfill by spring default mechanism
                return false;
            }
            return true;
        }
    }
}
