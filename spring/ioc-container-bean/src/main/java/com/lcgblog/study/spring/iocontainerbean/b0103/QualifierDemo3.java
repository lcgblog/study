package com.lcgblog.study.spring.iocontainerbean.b0103;

import com.lcgblog.study.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import java.lang.annotation.*;
import java.util.List;

//利用@Qualifier在bean定义时进行分组，注入时使用@Qualifier将只注入有@Qualifier标识的
//利用@Qualifer的派生注解可以进一步进行分组
//派生的注解上如果@Qualifer没有设置value，则@Qualifer注入时也将注入配置该派生注解的bean
public class QualifierDemo3 {

    @Autowired
    private List<User> allUsers;

    @Autowired
    @Qualifier
    private List<User> qualifierUsers1;

    @Autowired
    @UserGroup
    private List<User> qualifierUsers2;

    @Autowired
    @UserGroup2
    private List<User> qualifierUsers3;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(QualifierDemo3.class);
        QualifierDemo3 qualifierDemo1 = applicationContext.getBean(QualifierDemo3.class);
        System.out.println(qualifierDemo1.allUsers);//1,2,3,4
        System.out.println(qualifierDemo1.qualifierUsers1);//2,3 由于UserGroup的@Qualifier没有设置value，此处user3被注入，类似继承
        System.out.println(qualifierDemo1.qualifierUsers2);//3
        System.out.println(qualifierDemo1.qualifierUsers3);//4
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

    @UserGroup
    @Bean
    public User user3(){
        return createUser(3L);
    }

    @UserGroup2
    @Bean
    public User user4(){
        return createUser(4L);
    }

    private static User createUser(Long id){
        User user = new User();
        user.setId(id);
        return user;
    }

    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    @Documented
    @Qualifier
    public @interface UserGroup{
    }

    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    @Documented
    @Qualifier("UserGroup2")
    public @interface UserGroup2{
    }
}
