package com.lcgblog.study.spring.iocontainerbean.a0927;

import com.lcgblog.study.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

//注册bean
public class RegisterBeanDemo2 {

    public static void main(String[] args) {
        //方式二 20210927
        //1.通过BeanDefinitionBuilder创建bean定义信息
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
        beanDefinitionBuilder.addPropertyValue("id", 1L);
        beanDefinitionBuilder.addPropertyValue("name", "zs");

        //2.通过BeanDefinitionRegistry注册bean
        BeanDefinitionRegistry beanDefinitionRegistry = (BeanDefinitionRegistry)applicationContext.getBeanFactory();
        beanDefinitionRegistry.registerBeanDefinition("user", beanDefinitionBuilder.getBeanDefinition());

        //2.1 或者通过BeanDefinitionReaderUtils来注册一个无名字的bean
        BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinitionBuilder.getBeanDefinition(), beanDefinitionRegistry);
        applicationContext.refresh();
        BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinitionBuilder.getBeanDefinition(), beanDefinitionRegistry);//refresh之后注册将直接创建
        System.out.println(applicationContext.getBeansOfType(User.class));

        applicationContext.close();
    }

}
