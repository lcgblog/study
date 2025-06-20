package com.lcgblog.study.springboot.controller;

import com.lcgblog.study.springboot.domain.dto.CommonResponse;
import com.lcgblog.study.springboot.domain.entity.User;
import com.lcgblog.study.springboot.domain.error.ServiceException;
import com.lcgblog.study.springboot.service.UserServiceI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserServiceI userService;

    @PutMapping("/register")
    public CommonResponse registerUser(String username, String password, String comment) {
        User user = User.builder()
                .username(username)
                .password(password)
                .comment(comment)
                .build();
        userService.registerUser(user);
        return CommonResponse.ok("用户注册成功!");
    }

    @GetMapping("/list")
    public CommonResponse list() {
        return CommonResponse.ok(Map.of("users",userService.listUsers()));
    }

    @DeleteMapping("/delete")
    public CommonResponse deleteUser(long id) {
        userService.deleteUser(id);
        return CommonResponse.ok();
    }

    @PostMapping("/updateUserComment")
    public CommonResponse updateUser(long id, String comment) {
        userService.updateUserComment(id, comment);
        return CommonResponse.ok();
    }

    @ExceptionHandler
    public CommonResponse handleException(ServiceException e) {
        return CommonResponse.alert(e.getMessage());
    }

    @ExceptionHandler
    public CommonResponse handleException(Throwable e) {
        log.error("Unexpected Exception",e);
        return CommonResponse.alert("Got error. Please contact support.");
    }
}
