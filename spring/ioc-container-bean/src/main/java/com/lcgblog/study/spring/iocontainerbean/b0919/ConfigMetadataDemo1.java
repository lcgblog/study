package com.lcgblog.study.spring.iocontainerbean.b0919;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

//基于注解的配置元信息测试
@ImportResource("b0919/bean.xml")
@Import(ConfigMetadataConfig.class)
@ComponentScan("com.lcgblog.study.spring.iocontainerbean.b0919.inner")
public class ConfigMetadataDemo1 {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext();
        applicationContext.register(ConfigMetadataDemo1.class);
        applicationContext.refresh();
        //User{id=1, name='zs', like=null}
        System.out.println(applicationContext.getBean("user1"));
        //User{id=19, name='haha', like=null}
        System.out.println(applicationContext.getBean("user"));
        //User{id=20, name='haha', like=null}
        System.out.println(applicationContext.getBean("user4"));
    }
}
