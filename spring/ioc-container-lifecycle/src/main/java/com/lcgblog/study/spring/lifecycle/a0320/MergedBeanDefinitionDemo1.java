package com.lcgblog.study.spring.lifecycle.a0320;

import com.lcgblog.study.spring.lifecycle.beans.SuperUser;
import com.lcgblog.study.spring.lifecycle.beans.User;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

//没有parent的bean definition会直接生成rootBeanDefinition
//有parent的会将parent的root bean definition copy一份一份下来，并将当前的generic bean definition进行增量覆盖，
//最后将覆盖后的rbd（root bean definition）放入mbd（merged bean definition）缓存中
public class MergedBeanDefinitionDemo1 {

    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        beanDefinitionReader.loadBeanDefinitions("classpath:merged_beans.xml");
        System.out.println(beanFactory.getBean("user"));
        System.out.println(beanFactory.getBean("superUser"));
        System.out.println(beanFactory.getBean("user",User.class).getList().hashCode());
        System.out.println(beanFactory.getBean(SuperUser.class).getList().hashCode());
        beanFactory.getBean(SuperUser.class).getList().add("ccc");//will change the list of superUser
        System.out.println(beanFactory.getBean("user"));
        System.out.println(beanFactory.getBean("superUser"));
        System.out.println(beanFactory.getBean("user",User.class).getList().hashCode());
        System.out.println(beanFactory.getBean(SuperUser.class).getList().hashCode());//hashcode is changed
    }


}
