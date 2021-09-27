package com.lcgblog.study.spring.iocontainerbean.a0927;

import com.lcgblog.study.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

//Bean的延迟初始化,创建容器时不立即初始化，而是等到第一次访问时才初始化
@Configuration
public class BeanLazyInitializationDemo1 {

    @Lazy
    @Autowired
    private User user;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeanLazyInitializationDemo1.class);
        System.out.println("创建Context");
        System.out.println(applicationContext.getBeansOfType(User.class));
        applicationContext.close();
    }

    //方式一
    @Lazy
    @Bean
    public User user() {
        System.out.println("创建user");
        User user = new User();
        user.setId(1L);
        user.setName("111");
        return user;
    }

    //方式二
    // <bean lazy-init="true"  xxx />
}
