package com.lcgblog.study.spring.iocontainerbean.b0130;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.Map;

//prototype作用域对象的销毁
@Configuration
public class ScopeDemo1 implements DisposableBean{

    @Autowired
    private LifecycleBean bean1;

    @Autowired
    private LifecycleBean bean2;

    @Autowired
    private LifecycleBean bean3;

    @Autowired
    private Map<String, LifecycleBean> beans;

    @Autowired
    private BeanFactory beanFactory;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(ScopeDemo1.class);
        System.out.println(applicationContext.getBean(ScopeDemo1.class).bean1);
        System.out.println(applicationContext.getBean(ScopeDemo1.class).bean2);
        System.out.println(applicationContext.getBean(ScopeDemo1.class).bean3);
        System.out.println(applicationContext.getBean(ScopeDemo1.class).beans);
        applicationContext.close();
    }

    @Bean
    public LifecycleBean bean3(){
        return new LifecycleBean(3L);
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public LifecycleBean bean1(){
        return new LifecycleBean(1L);
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public LifecycleBean bean2(){
        return new LifecycleBean(2L);
    }

    @Override
    public void destroy() {
        //prototype需要手动销毁
        bean1.destroy();
        bean2.destroy();
        for(Map.Entry<String, LifecycleBean> bean : beans.entrySet()){
            BeanDefinition beanDefinition =
                    ((DefaultListableBeanFactory)beanFactory).getBeanDefinition(bean.getKey());
            if(beanDefinition.isPrototype()){
                bean.getValue().destroy();
            }
        }
    }

    static class LifecycleBean implements InitializingBean, DisposableBean {
        private long id;

        public LifecycleBean() {
        }

        public LifecycleBean(long id) {
            this.id = id;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "LifecycleBean{" +
                    "id=" + id +
                    '}';
        }

        @Override
        public void destroy() {
            System.out.println("destroy - " + getId() + " - " + hashCode());
        }

        @Override
        public void afterPropertiesSet() {
            System.out.println("init - " + getId() + " - " + hashCode());
        }
    }
}
