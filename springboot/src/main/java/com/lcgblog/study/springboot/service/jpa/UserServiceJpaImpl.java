package com.lcgblog.study.springboot.service.jpa;

import com.lcgblog.study.springboot.domain.entity.User;
import com.lcgblog.study.springboot.domain.entity.UserEntity;
import com.lcgblog.study.springboot.domain.mapper.UserMapper;
import com.lcgblog.study.springboot.repository.jpa.UserJpaRepository;
import com.lcgblog.study.springboot.service.UserServiceI;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

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
        userJpaRepository.save(userEntity);
        userJpaRepository.flush();
    }

    @Override
    public void deleteUser(long id) {
        userJpaRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void updateUserComment(long id, String comment) {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));
        userJpaRepository.findById(id).ifPresent(user -> {
            user.setComment(comment);
            user.setUpdatedTime(now);
            userJpaRepository.save(user);
        });
        userJpaRepository.flush();
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
