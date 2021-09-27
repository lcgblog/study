package com.lcgblog.study.spring.iocontainerbean.a0927;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

//bean的销毁
public class BeanDestroyDemo1 implements DisposableBean {
    public static void main(String[] args) throws Exception{
        //@PreDestroy
        //实现DisposableBean接口的destroy()
        //自定义销毁（XML，Bean注解，BeanDefinition API）

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.registerBeanDefinition("BeanDestroyDemo1", BeanDefinitionBuilder
                .genericBeanDefinition(BeanDestroyDemo1.class).setDestroyMethodName("finished").getBeanDefinition());
        applicationContext.refresh();
        applicationContext.close();
        System.gc();
        Thread.sleep(3000);


    }

    @PreDestroy
    public void preDestroy(){
        System.out.println("preDestroy");
    }


    @Override
    public void destroy() throws Exception {
        System.out.println("DisposableBean destroy");
    }

    public void finished(){
        System.out.println("Customized destroy");
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("spring gc");
    }
}
