package com.lcgblog.study.spring.lifecycle.a0323;

import com.lcgblog.study.spring.lifecycle.beans.User;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

//实例化，验证通过ObjectFactory实例化
// ObjectFactoryCreatingFactoryBean.TargetBeanObjectFactory.getObject
@Configuration
public class InstantiationDemo3 {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("classpath:instantiation_beans2.xml");
        System.out.println(((ObjectFactory<User>)applicationContext.getBean(ObjectFactory.class)).getObject());
    }

}
