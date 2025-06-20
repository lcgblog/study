package com.lcgblog.study.springboot.service.jpa;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import com.lcgblog.study.springboot.domain.entity.User;
import com.lcgblog.study.springboot.domain.entity.UserEntity;
import com.lcgblog.study.springboot.domain.mapper.UserMapper;
import com.lcgblog.study.springboot.repository.jpa.UserJpaRepository;
import com.lcgblog.study.springboot.service.UserServiceI;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@ConditionalOnProperty(prefix = "lcgblog.study.data.jpa", name = "active", havingValue = "true")
@Slf4j
public class UserServiceJpaImpl implements UserServiceI {

    @Autowired
    private UserJpaRepository userJpaRepository;
    @Autowired
    private UserMapper userMapper;

    @Override
    public void registerUser(User user) {
        UserEntity userEntity = UserEntity.convert(user);
        log.info("register user:{}", userEntity);
        
        log.info("开始执行 save() 操作...");
        userEntity = userJpaRepository.save(userEntity);
        log.info("save() 操作完成，数据已添加到缓存 entity:{}", userEntity);
        
        log.info("开始执行 flush() 操作...");
        userJpaRepository.flush();
        log.info("flush() 操作完成，数据已真正落库 entity:{}", userEntity);
    }

    @Override
    public void deleteUser(long id) {
        userJpaRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void updateUserComment(long id, String comment) {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));
        log.debug("开始更新用户评论，用户ID: {}", id);
        
        userJpaRepository.findById(id).ifPresent(user -> {
            log.info("找到用户: {}", user.getUsername());
            user.setComment(comment);
            user.setUpdatedTime(now);
            
            log.info("开始执行 save() 操作...");
            userJpaRepository.save(user);
            log.info("save() 操作完成，更新已添加到缓存");
        });
        
        log.info("开始执行 flush() 操作...");
        userJpaRepository.flush();
        log.info("flush() 操作完成，用户评论更新已落库");
    }

    @Override
    public List<Map<String, Object>> listUsers() {
        return
                userJpaRepository.findAll()
                        .stream()
                        .map(UserEntity::convert)
                        .map(userMapper.dtoMapper).
                        toList();
    }

    @Override
    public User getUser(long id) {
        return UserEntity.convert(userJpaRepository.findById(id).orElse(null));
    }
}
