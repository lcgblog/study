package com.lcgblog.study.spring.iocontainerbean.b0126;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

//bean factory post processor
@Configuration
public class DependencySourceDemo5 {

    @Autowired
    private String testStr;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext();
        applicationContext.addBeanFactoryPostProcessor(new BeanFactoryPostProcessor() {
            @Override
            public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
                beanFactory.registerResolvableDependency(String.class, "hello3");
            }
        });
        applicationContext.refresh();
        applicationContext.register(DependencySourceDemo5.class);
        System.out.println(applicationContext.getBean(DependencySourceDemo5.class).testStr);
    }

}
