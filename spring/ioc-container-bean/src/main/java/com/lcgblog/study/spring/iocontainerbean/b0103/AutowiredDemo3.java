package com.lcgblog.study.spring.iocontainerbean.b0103;

import com.lcgblog.study.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.List;

//@Autowired在没有名称匹配的bean时，注入类型匹配的bean, 类型相同存在多个候选bean时，需要指定@Primary否则报错
public class AutowiredDemo3 {

    @Autowired
    private List<User> allUsers;

    @Autowired
    private User user3;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(AutowiredDemo3.class);
        AutowiredDemo3 qualifierDemo1 = applicationContext.getBean(AutowiredDemo3.class);
        System.out.println(qualifierDemo1.allUsers);
        System.out.println(qualifierDemo1.user3);//id=1
        applicationContext.close();
    }
    @Bean
    public User user1(){
        return createUser(1L);
    }


    private static User createUser(Long id){
        User user = new User();
        user.setId(id);
        return user;
    }
}
