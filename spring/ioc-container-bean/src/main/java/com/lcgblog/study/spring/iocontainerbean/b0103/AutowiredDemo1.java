package com.lcgblog.study.spring.iocontainerbean.b0103;

import com.lcgblog.study.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.List;

//按bean名称注入
public class AutowiredDemo1 {

    @Autowired
    private List<User> allUsers;

    @Autowired
    private User user1;

    @Autowired
    private User user2;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(AutowiredDemo1.class);
        AutowiredDemo1 autowiredDemo1 = applicationContext.getBean(AutowiredDemo1.class);
        System.out.println(autowiredDemo1.allUsers);
        System.out.println(autowiredDemo1.user1);//id=1
        System.out.println(autowiredDemo1.user2);//id=2
        applicationContext.close();
    }

    @Bean
    public User user1(){
        return createUser(1L);
    }

    @Bean
    public User user2(){
        return createUser(2L);
    }

    private static User createUser(Long id){
        User user = new User();
        user.setId(id);
        return user;
    }
}
