package com.lcgblog.study.spring.ioc.overview.container;

import com.lcgblog.study.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Map;

/**
 * {@link org.springframework.context.ApplicationContext} as IoC container
 */
public class IocContainerDemo2 {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(IocContainerDemo2.class);//register a bean
        applicationContext.refresh();//start up container
        lookupCollectionByType(applicationContext, User.class);
        applicationContext.close();//stop container
    }

    @Bean
    private User user(){
        User user = new User();
        user.setId(5L);
        user.setName("Jack");
        return user;
    }

    private static <T> void lookupCollectionByType(BeanFactory beanFactory, Class<T> clazz){
        if(beanFactory instanceof ListableBeanFactory){
            Map<String, T> beans = ((ListableBeanFactory)beanFactory).getBeansOfType(clazz);
            System.out.println(beans);
        }
    }

}
