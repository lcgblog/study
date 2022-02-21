package com.lcgblog.study.spring.iocontainerbean.b0130;


import com.lcgblog.study.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.Scope;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//实现自定义作用域
@Configuration
public class ScopeDemo2 {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext();
        CustomScope customScope = new CustomScope();
        applicationContext.getBeanFactory().registerScope(CustomScope.SCOPE_NAME, customScope);
        applicationContext.register(ScopeDemo2.class);
        applicationContext.refresh();
        System.out.println(applicationContext.getBean("user"));
        System.out.println(applicationContext.getBean("user"));
        System.out.println("---invoke destroy method---");
        customScope.destroyObject("user");
        System.out.println(applicationContext.getBean("user"));
        System.out.println(applicationContext.getBean("user"));

    }

    @org.springframework.context.annotation.Scope(value = CustomScope.SCOPE_NAME)
    @Bean
    public User user(){
        User user = new DisposableUser();
        user.setId(1L);
        user.setName("Tom");
        return user;
    }
}

class DisposableUser extends User implements DisposableBean{
    @Override
    public void destroy() throws Exception {
        System.out.println("destroy " + this);
    }
}

class CustomScope implements Scope{

    public static final String SCOPE_NAME = "CustomScope";

    private final Map<String, Object> context = new HashMap<>();

    private final Map<String, Runnable> disposableMap = new HashMap<>();

    public synchronized void destroyObject(String name){
        context.remove(name);
        Runnable runnable = disposableMap.remove(name);
        if(runnable != null){
            runnable.run();
        }
    }

    @Override
    public synchronized Object get(String name, ObjectFactory<?> objectFactory) {
        if(context.containsKey(name)){
            System.out.println("return object " + name);
            return context.get(name);
        }
        System.out.println("create object " + name);
        Object obj = objectFactory.getObject();
        context.put(name, obj);
        return obj;
    }

    @Override
    public synchronized Object remove(String name) {
        System.out.println("remove " + name);
        return context.remove(name);
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback) {
        System.out.println("register destruction callback for - " + name);
        disposableMap.put(name, callback);
    }

    @Override
    public Object resolveContextualObject(String key) {
        return null;
    }

    @Override
    public String getConversationId() {
        System.out.println("getId");
        return SCOPE_NAME;
    }
}