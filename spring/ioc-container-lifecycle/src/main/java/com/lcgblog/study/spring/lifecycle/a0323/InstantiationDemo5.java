package com.lcgblog.study.spring.lifecycle.a0323;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

//实例化，验证通过构造器自动绑定来进行实例化
//自动绑定的对象来自于ConstructorResolver.resolveAutowiredArgument
//this.beanFactory.resolveDependency(
//					new DependencyDescriptor(param, true), beanName, autowiredBeanNames, typeConverter)
// => DependencyDescriptor.resolveCandidate(String beanName, Class<?> requiredType, BeanFactory beanFactory)
// => beanFactory.getBean(beanName)
@Configuration
public class InstantiationDemo5 {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("classpath:instantiation_beans4.xml");
        System.out.println(applicationContext.getBean("autowiredUser"));
    }

}
