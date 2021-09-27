package com.lcgblog.study.spring.iocontainerbean.a0927;

import com.lcgblog.study.spring.ioc.overview.domain.User;
import com.lcgblog.study.spring.iocontainerbean.a0927.serviceloader.UserFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ServiceLoader;

import static org.springframework.beans.factory.support.AbstractBeanDefinition.AUTOWIRE_NO;

//bean实例化
public class BeanInitiationDemo1 {
    public static void main(String[] args) {
        //20210927
        //常规方式
        //通过构造器（XML,Java注解,Java API）
        // <bean class=""/>; @Componenent-@Bean-@Import , @Bean-@Configuration ; BeanDefinitionBuilder, BeanDefinitionReaderUtil, BeanDefinitionRegistry;

        //通过Bean工厂方法（XML,Java API）
        ClassPathXmlApplicationContext applicationContext1 = new ClassPathXmlApplicationContext("classpath:bean-initiation.xml");
        System.out.println(applicationContext1.getBeansOfType(User.class));
        applicationContext1.close();

        AnnotationConfigApplicationContext applicationContext2 = new AnnotationConfigApplicationContext();
        applicationContext2.registerBeanDefinition("UserFactoryBean",
                BeanDefinitionBuilder.genericBeanDefinition("com.lcgblog.study.spring.iocontainerbean.a0927.BeanInitiationDemo1.UserFactoryBean").getBeanDefinition());
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition()
                .setFactoryMethodOnBean("createUser", "UserFactoryBean")
                .getBeanDefinition();
        BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinition, applicationContext2);
        applicationContext2.refresh();
        System.out.println(applicationContext2.getBeansOfType(User.class));
        applicationContext2.close();


        //通过静态工厂方法（XML,Java API）
        //<class="UserFactory" factory-method="createUser" />
        ClassPathXmlApplicationContext applicationContext3 = new ClassPathXmlApplicationContext("classpath:bean-initiation.xml");
        System.out.println(applicationContext3.getBeansOfType(User.class));
        applicationContext1.close();


        //通过FactoryBean（XML,Java注解,Java API）
        ClassPathXmlApplicationContext applicationContext4 = new ClassPathXmlApplicationContext("classpath:bean-initiation.xml");
        ObjectFactory<User> objectFactory = applicationContext4.getBean("UserObjectFactory", ObjectFactory.class);
        System.out.println(objectFactory.getObject());
        System.out.println(objectFactory.getObject() == applicationContext4.getBean("User2"));
        System.out.println(applicationContext4.getBean("User3"));

        //特殊方式
        //通过ServiceLoaderFactoryBean
        //Java API
        ServiceLoader<UserFactory> serviceLoader = ServiceLoader.load(UserFactory.class,Thread.currentThread().getContextClassLoader());
        for (UserFactory bean : serviceLoader){
            System.out.println(bean.createUser());
        }
        //Spring API
        ServiceLoader<UserFactory> serviceLoader2 = (ServiceLoader<UserFactory>)applicationContext4.getBean("UserServiceLoader");
        for (UserFactory bean : serviceLoader2){
            System.out.println(bean.createUser());
        }


        //通过AutowireCapableBeanFactory#createBean(class,int,bool)
        AutowireCapableBeanFactory beanFactory = applicationContext4.getBeanFactory();
        User user = (User) beanFactory.createBean(User.class, AUTOWIRE_NO, false);
        user.setId(3L);
        user.setName("3333");
        System.out.println(user);

        //通过BeanDefinitionRegistry#registerBeanDefinition(string, beanDefinition)
        BeanDefinitionRegistry registry = (BeanDefinitionRegistry)applicationContext4.getBeanFactory();
        registry.registerBeanDefinition("user5", BeanDefinitionBuilder.genericBeanDefinition(User.class).addPropertyValue("name","444").addPropertyValue("id",4L).getBeanDefinition());
        System.out.println(applicationContext4.getBeansOfType(User.class));

    }

    public static class UserFactoryBean{

        public User createUser(){
            User user = new User();
            user.setName("zs");
            user.setId(1L);
            return user;
        }

    }

    public static class UserFactoryBean2{

        public static User createUser(){
            User user = new User();
            user.setName("ls");
            user.setId(2L);
            return user;
        }

    }


    public static class User2ObjectFactory implements FactoryBean<User> {

        @Override
        public User getObject() throws Exception {
            return UserFactoryBean2.createUser();
        }

        @Override
        public Class<?> getObjectType() {
            return User.class;
        }
    }
}
