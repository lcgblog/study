package com.lcgblog.study.spring.iocontainerbean.b0103;

import com.lcgblog.study.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.List;

//利用@Qualifier在bean定义时进行分组，注入时使用@Qualifier将只注入有@Qualifier标识的
//设置Qualifier的value可以进一步进行分组
public class QualifierDemo2 {

    @Autowired
    private List<User> allUsers;

    @Autowired
    @Qualifier
    private List<User> qualifierUsers1;

    @Autowired
    @Qualifier("a")
    private List<User> qualifierUsers2;

    @Autowired
    @Qualifier("b")
    private List<User> qualifierUsers3;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(QualifierDemo2.class);
        QualifierDemo2 qualifierDemo1 = applicationContext.getBean(QualifierDemo2.class);
        System.out.println(qualifierDemo1.allUsers);//1,2,3,4,5
        System.out.println(qualifierDemo1.qualifierUsers1);//2
        System.out.println(qualifierDemo1.qualifierUsers2);//3
        System.out.println(qualifierDemo1.qualifierUsers3);//4,5
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

    @Qualifier("a")
    @Bean
    public User user3(){
        return createUser(3L);
    }

    @Qualifier("b")
    @Bean
    public User user4(){
        return createUser(4L);
    }


    @Qualifier("b")
    @Bean
    public User user5(){
        return createUser(5L);
    }


    private static User createUser(Long id){
        User user = new User();
        user.setId(id);
        return user;
    }
}
