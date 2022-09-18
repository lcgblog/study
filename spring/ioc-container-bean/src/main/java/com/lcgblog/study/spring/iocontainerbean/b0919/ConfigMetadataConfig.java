package com.lcgblog.study.spring.iocontainerbean.b0919;

import com.lcgblog.study.spring.ioc.overview.domain.User;
import org.springframework.context.annotation.Bean;

//注解配置元信息，测试@Import注解
public class ConfigMetadataConfig {

    @Bean
    public User user(){
        User user = new User();
        user.setName("haha");
        user.setId(19L);
        return user;
    }

}
