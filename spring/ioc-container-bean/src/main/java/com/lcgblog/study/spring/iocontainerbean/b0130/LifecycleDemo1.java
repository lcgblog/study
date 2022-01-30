package com.lcgblog.study.spring.iocontainerbean.b0130;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class LifecycleDemo1 {

    @Autowired
    private LifecycleBean bean1;

    @Autowired
    private LifecycleBean bean2;

//    @Autowired
//    private LifecycleBean bean3;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext();
        BeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(LifecycleBean.class)
                .addPropertyValue("id",1L)
                .getBeanDefinition();
        applicationContext.registerBeanDefinition("bean1", beanDefinition);
        applicationContext.getBeanFactory().registerSingleton("bean2", new LifecycleBean(2L));
//        applicationContext.getBeanFactory().registerResolvableDependency(LifecycleBean.class, new LifecycleBean(3L));
        applicationContext.register(LifecycleDemo1.class);
        applicationContext.refresh();
        System.out.println(applicationContext.getBean(LifecycleDemo1.class).bean1);
        System.out.println(applicationContext.getBean(LifecycleDemo1.class).bean2);
//        System.out.println(applicationContext.getBean(LifecycleDemo1.class).bean3);
        applicationContext.close();
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
            System.out.println("destroy - " + getId());
        }

        @Override
        public void afterPropertiesSet() {
            System.out.println("init - " + getId());
        }
    }
}
