package com.lcgblog.study.springboot.repository.jpa;

import com.lcgblog.study.springboot.domain.entity.UserEntity;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

@ConditionalOnProperty(prefix = "lcgblog.study.data.jpa", name = "active", havingValue = "true")
public interface UserJpaRepository extends JpaRepository<UserEntity,Long> {

}
