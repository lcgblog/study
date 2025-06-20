package com.lcgblog.study.springboot.domain.entity;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.beans.BeanUtils;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "register_user")
@Data
@NoArgsConstructor
public class UserEntity {

    @Id
    private Long id;
    private String username;
    private String password;
    private String comment;
    @Column(name = "created_time")
    private LocalDateTime createdTime;
    @Column(name = "updated_time")
    private LocalDateTime updatedTime;
    @Column(name = "last_login_time")
    private LocalDateTime lastLoginTime;

    @Version
    private Long version = 0L;

    public static UserEntity convert(User user) {
        if(user == null) return null;
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);
        LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));
        userEntity.setCreatedTime(now);
        userEntity.setLastLoginTime(now);
        userEntity.setUpdatedTime(now);
        return userEntity;
    }

    public static User convert(UserEntity userEntity) {
        if(userEntity == null) return null;
        User user = User.builder().build();
        BeanUtils.copyProperties(userEntity, user);
        return user;
    }
}
