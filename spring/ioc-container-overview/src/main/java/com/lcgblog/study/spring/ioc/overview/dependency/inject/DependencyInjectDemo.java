package com.lcgblog.study.spring.ioc.overview.dependency.inject;

import com.lcgblog.study.spring.ioc.overview.domain.Like;
import com.lcgblog.study.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Bean;

public class DependencyInjectDemo {
    public static void main(String[] args) throws Exception {
        //xml注入
//        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/dependency-inject-context.xml");
//        System.out.println(beanFactory.getBean(User.class));

        //Annotation注入
//        BeanFactory beanFactory = new AnnotationConfigApplicationContext(DependencyInjectDemo.class);
//        System.out.println(beanFactory.getBean("user"));

        //API 注入
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        BeanDefinitionBuilder userDef = BeanDefinitionBuilder.genericBeanDefinition(User.class);
        userDef.addPropertyValue("id","3");
        userDef.addPropertyValue("name","王五");
        userDef.addPropertyReference("like","like");
        BeanDefinitionBuilder likeDef = BeanDefinitionBuilder.genericBeanDefinition(Like.class);
        likeDef.addPropertyValue("like","Banana");
        beanFactory.registerBeanDefinition("user",userDef.getBeanDefinition());
        beanFactory.registerBeanDefinition("like",likeDef.getBeanDefinition());
        System.out.println(beanFactory.getBean("user"));
    }

    @Bean
    public User user(Like like){
        User user = new User();
        user.setId(2L);
        user.setName("李四");
        user.setLike(like);
        return user;
    }
    @Bean
    public Like like(){
        Like like = new Like();
        like.setLike("Apple");
        return like;
    }
}
