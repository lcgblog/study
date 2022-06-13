package com.lcgblog.study.spring.lifecycle.a0613;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

public class DestructionDemo1 {

    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory =
                new DefaultListableBeanFactory();
        BeanDefinition bd = BeanDefinitionBuilder
                .genericBeanDefinition(TestBean.class)
                .addPropertyValue("name","TomV1")
                .getBeanDefinition();
        beanFactory.addBeanPostProcessor(new DestructionAwareBeanPostProcessor() {
            @Override
            public void postProcessBeforeDestruction(Object bean, String beanName) throws BeansException {
                if(bean instanceof TestBean){
                    System.out.println("销毁前 -> " + bean);
                    ((TestBean) bean).setName("TomV2");
                }
            }
        });
        beanFactory.registerBeanDefinition("testBean", bd);
        TestBean bean = beanFactory.getBean("testBean", TestBean.class);
        System.out.println("销毁bean");
        beanFactory.destroyBean("testBean", bean);
        System.out.println("销毁后 -> " + bean);
    }

    private static class TestBean implements DisposableBean {
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

        @Override
        public void destroy() {
            System.out.println("执行销毁");
        }
    }
}
