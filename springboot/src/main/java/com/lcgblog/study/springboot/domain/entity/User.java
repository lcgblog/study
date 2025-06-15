package com.lcgblog.study.springboot.domain.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Builder
@Table("register_user")
public class User {

    @Builder.Default
    @Id
    private long id = 0;
    private String username;
    private String password;
    private String comment;
    @Column("created_time")
    private LocalDateTime createdTime;
    @Column("updated_time")
    private LocalDateTime updatedTime;
    @Column("last_login_time")
    private LocalDateTime lastLoginTime;

}
