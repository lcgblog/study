package com.lcgblog.study.spring.iocontainerbean.a0927.serviceloader;

import com.lcgblog.study.spring.ioc.overview.domain.User;

public class DefaultUser2Factory implements UserFactory{

    @Override
    public User createUser() {
        User user = UserFactory.super.createUser();
        user.setId(222L);
        return user;
    }
}
