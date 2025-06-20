package com.lcgblog.study.springboot.repository.jpa;

import com.lcgblog.study.springboot.domain.entity.UserEntity;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

@ConditionalOnProperty(prefix = "lcgblog.study.data.jpa", name = "active", havingValue = "true")
public interface UserJpaRepository extends JpaRepository<UserEntity,Long> {

    @Modifying
    @Query("update register_user set comment=?2,updatedTime=?3 where id=?1")
    int updateById(Long id, String comment, LocalDateTime updatedTime);

}
