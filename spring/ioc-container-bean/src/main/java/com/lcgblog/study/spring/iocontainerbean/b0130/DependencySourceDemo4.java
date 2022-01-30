package com.lcgblog.study.spring.iocontainerbean.b0130;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource(value = "default.properties", encoding = "UTF-8")
@Configuration
public class DependencySourceDemo4 {

    @Value("${str1}")
    private String str1;
    @Value("${str2}")
    private String str2;
    @Value("${str3:none}")
    private String str3;
    @Value("direct value")
    private String str4;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(DependencySourceDemo4.class);
        System.out.println(applicationContext.getBean(DependencySourceDemo4.class).str1);
        System.out.println(applicationContext.getBean(DependencySourceDemo4.class).str2);
        System.out.println(applicationContext.getBean(DependencySourceDemo4.class).str3);
        System.out.println(applicationContext.getBean(DependencySourceDemo4.class).str4);

    }

}
