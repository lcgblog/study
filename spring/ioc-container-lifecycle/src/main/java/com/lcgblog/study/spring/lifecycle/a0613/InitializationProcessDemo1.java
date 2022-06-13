package com.lcgblog.study.spring.lifecycle.a0613;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;


/**
 * Bean在 "实例化->赋值->赋值aware属性" 后， 进行初始化阶段
 * 执行位置:
 * org.springframework.beans.factory.config.AutowireCapableBeanFactory
 *  #initializeBean(java.lang.Object, java.lang.String)
 * 执行周期分为：初始化前->初始化->初始化后->初始化完成
 * 其中依托于BeanPostProcessor的两个接口方法实现初始化前和初始化后的执行逻辑
 * 初始化方式分为：注解方式，接口方法方式，BeanDefinition方式
 * 初始化完成：org.springframework.beans.factory.config.ConfigurableListableBeanFactory#preInstantiateSingletons()
 *  该方法会把所有BD getBean一遍,容器启动后最后一件事就是这个，确保所有bean都初始化完成
 *  回调接口：org.springframework.beans.factory.SmartInitializingSingleton#afterSingletonsInstantiated()
 */
public class InitializationProcessDemo1 {

    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        BeanDefinition bd = BeanDefinitionBuilder
                .genericBeanDefinition(TestBean.class)
                .addPropertyValue("name","TomV1")
                .getBeanDefinition();
        beanFactory.addBeanPostProcessor(new BeanPostProcessor() {
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                if(bean instanceof TestBean){
                    System.out.println("初始化前 -> " + bean);
                    ((TestBean)bean).setName("Tom_V2");
                }
                return bean;
            }

            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if(bean instanceof TestBean){
                    System.out.println("初始化后 -> " + bean);
                    ((TestBean)bean).setName("Tom_V4");
                }
                return bean;
            }
        });
        beanFactory.registerBeanDefinition("testBean", bd);
        System.out.println("Registered bean");
        //完成初始化
        beanFactory.preInstantiateSingletons();
        System.out.println("初始化结束.");
        System.out.println("最终版本：" + beanFactory.getBean("testBean"));
    }

    private static class TestBean implements InitializingBean, SmartInitializingSingleton {
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
        public void afterPropertiesSet() {
            //初始化
            System.out.println("初始化 -> " + this);
            this.name = "Tom_V3";
        }

        @Override
        public void afterSingletonsInstantiated() {
            System.out.println("初始化完成 -> " + this);
            this.name = "Tom_V5";
        }
    }
}
