package com.lcgblog.study.spring.iocontainerbean.b0103;

import com.lcgblog.study.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.List;

//利用@Qualifier在bean定义时进行分组，注入时使用@Qualifier将只注入有@Qualifier标识的
public class QualifierDemo1 {

    @Autowired
    private List<User> allUsers;

    @Autowired
    @Qualifier
    private List<User> qualifierUsers;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(QualifierDemo1.class);
        QualifierDemo1 qualifierDemo1 = applicationContext.getBean(QualifierDemo1.class);
        System.out.println(qualifierDemo1.allUsers);//id=1,id=2,id=3
        System.out.println(qualifierDemo1.qualifierUsers);//id=2,id=3
        applicationContext.close();
    }

    @Bean
    public User user1(){
        return createUser(1L);
    }

    @Qualifier
    @Bean
    public User user2() {
        return createUser(2L);
    }

    @Qualifier
    @Bean
    public User user3(){
        return createUser(3L);
    }

    private static User createUser(Long id){
        User user = new User();
        user.setId(id);
        return user;
    }
}
