package com.lcgblog.study.spring.lifecycle.a0323;

import com.lcgblog.study.spring.lifecycle.beans.User;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

//实例化，验证通过构造器实例化
//断点 AbstractAutowireCapableBeanFactory:1204
// AbstractAutowireCapableBeanFactory.autowireConstructor(beanName, mbd, ctors, args)
@Configuration
public class InstantiationDemo4 {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("classpath:instantiation_beans3.xml");
        System.out.println(applicationContext.getBean("user"));
    }

}
