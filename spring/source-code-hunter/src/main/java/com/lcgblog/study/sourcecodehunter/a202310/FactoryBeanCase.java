package com.lcgblog.study.sourcecodehunter.a202310;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FactoryBeanCase {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(FactoryBeanCase.class);
        System.out.println(applicationContext.getBean("myFactoryBean"));
        System.out.println(applicationContext.getBean("&myFactoryBean"));
    }

    @Bean
    public MyFactoryBean myFactoryBean(){
        return new MyFactoryBean();
    }

    public static class User{
        @Override
        public String toString() {
            return "User";
        }
    }

    public static class MyFactoryBean implements FactoryBean<User> {

        @Override
        public User getObject() throws Exception {
            return new User();
        }

        @Override
        public Class<?> getObjectType() {
            return User.class;
        }

        @Override
        public String toString() {
            return "MyFactoryBean";
        }
    }
}
