package com.lcgblog.study.spring.ioc.overview.dependency.lookup;

import com.lcgblog.study.spring.ioc.overview.annotation.Super;
import com.lcgblog.study.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

//依赖查找
public class DependencyLookupDemo {

    public static void main(String[] args) {
        //配置XML配置文件
        //启动应用上下文
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/dependency-lookup-context.xml");

        //实时查找
        User user = beanFactory.getBean(User.class);
        System.out.println(user);

        //延迟查找, BeanFactory, FactoryBean, ObjectFactory 区别
        ObjectFactory<User> objectFactory = (ObjectFactory<User>)beanFactory.getBean("objectFactory");
        User user1 = objectFactory.getObject();
        System.out.println(user1);

        //查找集合对象
        if(beanFactory instanceof ListableBeanFactory){
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, User> users = listableBeanFactory.getBeansOfType(User.class);
            System.out.println(users);
        }
        //查找注解
        if(beanFactory instanceof ListableBeanFactory){
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, Object> superUsers = listableBeanFactory.getBeansWithAnnotation(Super.class);
            System.out.println(superUsers);
        }
    }
}
