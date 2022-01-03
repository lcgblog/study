package com.lcgblog.study.spring.iocontainerbean.b0103;

import com.lcgblog.study.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.util.List;

//如果有主候选bean，则注入该主候选bean
public class AutowiredDemo2 {

    @Autowired
    private List<User> allUsers;

    @Autowired
    private User user1;

    @Autowired
    private User user2;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(AutowiredDemo2.class);
        AutowiredDemo2 qualifierDemo1 = applicationContext.getBean(AutowiredDemo2.class);
        System.out.println(qualifierDemo1.allUsers);
        System.out.println(qualifierDemo1.user1);//id=1
        System.out.println(qualifierDemo1.user2);//id=1
        applicationContext.close();
    }

    @Primary
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
