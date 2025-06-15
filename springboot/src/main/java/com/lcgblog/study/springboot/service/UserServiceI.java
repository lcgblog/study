package com.lcgblog.study.springboot.service;

import com.lcgblog.study.springboot.domain.entity.User;

import java.util.List;
import java.util.Map;

public interface UserServiceI {

    void registerUser(User user);

    void deleteUser(long id);

    void updateUserComment(long id, String comment);

    List<Map<String, Object>> listUsers();

    User getUser(long id);

}
