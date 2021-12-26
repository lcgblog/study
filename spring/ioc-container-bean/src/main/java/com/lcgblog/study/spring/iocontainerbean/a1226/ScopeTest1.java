package com.lcgblog.study.spring.iocontainerbean.a1226;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

public class ScopeTest1 {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(ScopeTest1.class);
        System.out.println("容器启动");
        System.out.println("第一次实验");
        //拿两次 => singleton => true
        System.out.println(applicationContext.getBean("box1") == applicationContext.getBean("box1"));
        System.out.println("第二次实验");
        //prototype => false
        System.out.println(applicationContext.getBean("box2") == applicationContext.getBean("box2"));
        applicationContext.close();//prototype方式创建的bean不会执行销毁方法
        System.out.println("容器销毁");

    }

    @Bean(destroyMethod = "destroy", initMethod = "init")
    public Box box1(){
        return new Box();
    }

    @Scope("prototype")
    @Bean(destroyMethod = "destroy", initMethod = "init")
    public Box box2(){
        return new Box();
    }


    public static class Box{
        public void init(){
            System.out.println(this+"初始化了");
        }

        public Box(){
            System.out.println(this+"构造了");
        }
        public void destroy(){
            System.out.println(this+"销毁了");
        }
    }
}
