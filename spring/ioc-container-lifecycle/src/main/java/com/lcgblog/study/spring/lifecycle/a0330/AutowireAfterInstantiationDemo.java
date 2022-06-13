package com.lcgblog.study.spring.lifecycle.a0330;

import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

/**
 * 验证在属性赋之前回调操作之前，
 * 即InstantiationAwareBeanPostProcessor#postProcessProperties
 * 会进行属性注入，根据AutowireMode决定
 */
public class AutowireAfterInstantiationDemo {
    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        BeanDefinition bd1 = BeanDefinitionBuilder.genericBeanDefinition(TestBean.class)
                .addPropertyValue("name","V1")
                .getBeanDefinition();
        BeanDefinition bd2 = BeanDefinitionBuilder.genericBeanDefinition(TestBean.class)
                .addPropertyValue("name","V2")
                .setPrimary(true)
                .getBeanDefinition();
        BeanDefinition bd3 = BeanDefinitionBuilder.genericBeanDefinition(TestBeanHolder.class)
                .setAutowireMode(AutowireCapableBeanFactory.AUTOWIRE_BY_NAME)//V1
//                .setAutowireMode(AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE)//V2
                .setPrimary(true)
                .getBeanDefinition();
        beanFactory.addBeanPostProcessor(new InstantiationAwareBeanPostProcessor() {
            @Override
            public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
                if(beanName.equals("test3")){
                    System.out.println("属性赋值前回调执行前->"+pvs.getPropertyValue("test").getValue());
                    final MutablePropertyValues mpv = new MutablePropertyValues(pvs);
                    mpv.removePropertyValue("test");
                    mpv.addPropertyValue("test", beanFactory.getBean("test2"));
                    return mpv;
                }
                return pvs;
            }
        });
        beanFactory.registerBeanDefinition("test", bd1);
        beanFactory.registerBeanDefinition("test2", bd2);
        beanFactory.registerBeanDefinition("test3", bd3);
        beanFactory.preInstantiateSingletons();
        System.out.println(beanFactory.getBean("test3"));
    }

    private static class TestBean {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "TestBean{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    private static class TestBeanHolder {
        private TestBean test;

        public TestBean getTest() {
            return test;
        }

        public void setTest(TestBean test) {
            this.test = test;
        }

        @Override
        public String toString() {
            return "TestBeanHolder{" +
                    "test=" + test +
                    '}';
        }
    }
}
