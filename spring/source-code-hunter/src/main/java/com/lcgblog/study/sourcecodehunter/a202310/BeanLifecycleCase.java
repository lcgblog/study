package com.lcgblog.study.sourcecodehunter.a202310;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

public class BeanLifecycleCase {
    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        //1. 注册元信息
        BeanDefinitionBuilder beanDefinitionBuilder1 = BeanDefinitionBuilder.rootBeanDefinition(User.class);
        beanDefinitionBuilder1.addPropertyValue("name","张三");
        BeanDefinitionBuilder beanDefinitionBuilder2 = BeanDefinitionBuilder.rootBeanDefinition(User.class);
        beanDefinitionBuilder2.addPropertyValue("name","李四");
        beanFactory.registerBeanDefinition("testBean1", beanDefinitionBuilder1.getBeanDefinition());
        beanFactory.registerBeanDefinition("testBean2", beanDefinitionBuilder2.getBeanDefinition());

        System.out.println("Registered bean");
        beanFactory.addBeanPostProcessor(new UserInstantiationBeanPostProcessor());
        System.out.println("Added UserInstantiationBeanPostProcessor");
        System.out.println("--------------");

        //提前初始化所有beanDefinition
        beanFactory.preInstantiateSingletons();
        System.out.println("preInstantiateSingletons");
        System.out.println("--------------");

        //2. 实例化
        //3. 赋值
        //4. 初始化
        Object bean = beanFactory.getBean("testBean1");
        System.out.println("Got bean " + bean);
        System.out.println("--------------");
        Object bean2 = beanFactory.getBean("testBean2");
        System.out.println("Got bean " + bean2);
        System.out.println("--------------");

        //5. 销毁
        beanFactory.removeBeanDefinition("testBean1");
        System.out.println("removed bean " + bean);
        System.out.println("--------------");
        beanFactory.removeBeanDefinition("testBean2");
        System.out.println("removed bean " + bean2);
    }

    @Data
    static class User implements InitializingBean, DisposableBean,
            BeanFactoryAware,SmartInitializingSingleton {
        private String name;

        static {
            System.out.println("实例化 - 类加载");
        }

        public User() {
            System.out.println("实例化");
        }

        public void setName(String name) {
            System.out.println("赋值 - 属性注入 SetName " + name);
            this.name = name;
        }

        @Override
        public void afterPropertiesSet() throws Exception {
            System.out.println("初始化回调 - afterPropertiesSet");
        }

        @Override
        public void destroy() throws Exception {
            System.out.println("销毁 - destroy");
        }

        @Override
        public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
            System.out.println("初始化 - BeanFactory Aware 回调");
        }

        @Override
        public void afterSingletonsInstantiated() {
            System.out.println("初始化完成 - afterSingletonsInstantiated " + this);
        }
    }

    static class UserInstantiationBeanPostProcessor implements InstantiationAwareBeanPostProcessor,
            DestructionAwareBeanPostProcessor {
        @Override
        public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
            System.out.println("实例化前 - postProcessBeforeInstantiation");
            return InstantiationAwareBeanPostProcessor.super.postProcessBeforeInstantiation(beanClass, beanName);
        }

        @Override
        public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
            System.out.println("实例化后 - postProcessAfterInstantiation");
            return InstantiationAwareBeanPostProcessor.super.postProcessAfterInstantiation(bean, beanName);
        }

        @Override
        public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
            System.out.println("赋值 - 属性处理 postProcessProperties");
            //该步骤之后有一个隐藏步骤会将返回的PV进行赋值处理
            return InstantiationAwareBeanPostProcessor.super.postProcessProperties(pvs, bean, beanName);
        }

        @Override
        public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
            System.out.println("初始化前 - postProcessBeforeInitialization");
            return InstantiationAwareBeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
        }

        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            System.out.println("初始化后 - postProcessAfterInitialization");
            return InstantiationAwareBeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
        }

        @Override
        public void postProcessBeforeDestruction(Object bean, String beanName) throws BeansException {
            System.out.println("销毁前 - postProcessBeforeDestruction");
        }
    }
}
