package com.lcgblog.study.spring.lifecycle.a0320;

import com.lcgblog.study.spring.lifecycle.beans.User;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;


//自定义spring的类加载器
public class CustomClassLoaderDemo1 {

    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.setBeanClassLoader(new MyClassLoader());
        BeanDefinition beanDefinition = BeanDefinitionBuilder.rootBeanDefinition("com.lcgblog.study.spring.lifecycle.beans.User")
                .addPropertyValue("id",1)
                .addPropertyValue("name","Jack").getBeanDefinition();
        beanFactory.registerBeanDefinition("user", beanDefinition);
        System.out.println(beanFactory.getBean("user"));
    }

    static class MyClassLoader extends ClassLoader{
        @Override
        public Class<?> loadClass(String name) throws ClassNotFoundException {
            System.out.println("custom load class...");
            return super.loadClass(name);
        }
    }
}
