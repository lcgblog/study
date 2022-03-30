package com.lcgblog.study.spring.lifecycle.a0330;

import com.lcgblog.study.spring.lifecycle.beans.User;
import org.springframework.beans.*;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.config.Scope;
import org.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.support.BeanDefinitionDsl;

import java.util.List;

//验证InstantiationAware接口的属性后处理功能
//该功能可以覆盖已有的属性
public class InstantiationPostProcessDemo2 {

    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory =
                new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("user",BeanDefinitionBuilder.rootBeanDefinition(User.class)
                .addPropertyValue("id",1L)
                .addPropertyValue("name", "Tom")
                .setScope("prototype")
                .getBeanDefinition());
        System.out.println(beanFactory.getBean("user"));
        beanFactory.addBeanPostProcessor(new InstantiationAwareBeanPostProcessor() {
            //返回空表示不做任何处理，返回值不为空则将覆盖原来的PV
            @Override
            public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
                final MutablePropertyValues mpv = new MutablePropertyValues(pvs);
                //测试覆盖name
                if(pvs.contains("name")){
                    mpv.removePropertyValue("name");
                    mpv.addPropertyValue(new PropertyValue("name","Tom V2"));
                }
                return mpv;
            }
        });
        System.out.println(beanFactory.getBean("user"));
    }
}
