package com.lcgblog.study.spring.iocontainerbean.a0926;

import com.lcgblog.study.spring.ioc.overview.domain.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanNameDemo2 {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeanNameDemo2.class);
        System.out.println(applicationContext.getBean("user"));
    }

    @Bean
    public User user(){
        User user = new User();
        user.setName("gg");
        user.setId(4L);
        return user;
    }

}
