package com.lcgblog.study.springboot.domain.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class User {

    @Builder.Default
    private long id = 0;
    private String username;
    private String password;
    private String comment;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private LocalDateTime lastLoginTime;

}
