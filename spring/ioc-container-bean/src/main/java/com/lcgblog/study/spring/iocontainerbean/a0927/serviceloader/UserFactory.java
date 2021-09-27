package com.lcgblog.study.spring.iocontainerbean.a0927.serviceloader;

import com.lcgblog.study.spring.ioc.overview.domain.User;

public interface UserFactory {

    default User createUser(){
        User user = new User();
        user.setId(111L);
        user.setName("llll");
        return user;
    }

}
