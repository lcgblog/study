package com.lcgblog.study.spring.iocontainerbean.b0105;

import com.lcgblog.study.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class ObjectProviderDemo {

    //two main benefits
    //1. lazy initiated like ObjectFactory
    //2. Iterable
    @Autowired
    private ObjectProvider<User> userObjectProvider;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(ObjectProviderDemo.class);
        ObjectProviderDemo objectProviderDemo = applicationContext.getBean(ObjectProviderDemo.class);
        System.out.println(objectProviderDemo.userObjectProvider);
        for(User user : objectProviderDemo.userObjectProvider){
            System.out.println(user);
        }
    }

    @Lazy
    @Bean
    public User user1(){
        User user = new User();
        user.setId(1L);
        return user;
    }

    @Lazy
    @Bean
    public User user2(){
        User user = new User();
        user.setId(2L);
        return user;
    }
}
