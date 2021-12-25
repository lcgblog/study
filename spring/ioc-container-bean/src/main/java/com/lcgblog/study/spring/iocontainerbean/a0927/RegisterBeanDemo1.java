package com.lcgblog.study.spring.iocontainerbean.a0927;

import com.lcgblog.study.spring.ioc.overview.domain.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

//注册bean
//3.通过@Import导入Config,精确引入
@Import({RegisterBeanDemo1.Config.class})
//@Import({RegisterBeanDemo1.Config.class, RegisterBeanDemo1.Config2.class})
//4.全部引入
//@Configuration
public class RegisterBeanDemo1 {

    public static void main(String[] args) {
        //20210927
        //方式一
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(RegisterBeanDemo1.class);
        applicationContext.refresh();
        System.out.println(applicationContext.getBeansOfType(RegisterBeanDemo1.class));
        System.out.println(applicationContext.getBeansOfType(Config.class));
        System.out.println(applicationContext.getBeansOfType(Config2.class));
        System.out.println(applicationContext.getBeansOfType(User.class));




        //方式二
        //1.通过BeanDefinitionBuilder创建bean定义信息
        //2.通过BeanDefinitionRegistry注册bean
        //2.1 或者通过BeanDefinitionReaderUtils来注册一个无名字的bean

        applicationContext.close();
    }

    //2.通过@Component注册Config
    @Component
    public static class Config{
        //1.通过@Bean注册bean
        @Bean
        public User user(){
            User user = new User();
            user.setId(1L);
            user.setName("zs");
            return user;
        }
    }

    @Component
    public static class Config2{
        //1.通过@Bean注册bean
        @Bean
        public User user2(){
            User user = new User();
            user.setId(2L);
            user.setName("ls");
            return user;
        }
    }
}
