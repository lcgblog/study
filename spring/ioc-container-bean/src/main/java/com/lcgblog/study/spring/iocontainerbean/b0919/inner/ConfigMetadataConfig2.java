package com.lcgblog.study.spring.iocontainerbean.b0919.inner;

import com.lcgblog.study.spring.ioc.overview.domain.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//注解配置元信息，测试@ComponentScan
@Configuration
public class ConfigMetadataConfig2 {

    @Bean
    public User user4(){
        User user = new User();
        user.setName("haha");
        user.setId(20L);
        return user;
    }

}
