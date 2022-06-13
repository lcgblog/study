package com.lcgblog.study.spring.lifecycle.a0613;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

/**
 * autowire模式下类型注入的优先级要比Name更高
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
                .addAutowiredProperty("test")
                .setPrimary(true)
                .getBeanDefinition();

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
