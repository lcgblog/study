package com.lcgblog.study.springboot.service.data;

import com.lcgblog.study.springboot.domain.entity.User;
import com.lcgblog.study.springboot.domain.error.ServiceException;
import com.lcgblog.study.springboot.domain.mapper.UserMapper;
import com.lcgblog.study.springboot.repository.data.UserRepository;
import com.lcgblog.study.springboot.service.UserServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@ConditionalOnProperty(prefix = "lcgblog.study.data.jdbc", name = "active", havingValue = "true")
public class UserServieDataJdbcImpl implements UserServiceI {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    @Override
    public void registerUser(User user) {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));
        user.setCreatedTime(now);
        user.setUpdatedTime(now);
        user.setLastLoginTime(now);
        userRepository.save(user);
    }

    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void updateUserComment(long id, String comment) {
        User user = userRepository.findById(id).orElse(null);
        if(user == null){
            throw new ServiceException("User not found!");
        }
        user.setComment(comment);
        userRepository.save(user);
    }

    @Override
    public List<Map<String, Object>> listUsers() {
        var result = new ArrayList<Map<String, Object>>();
        userRepository.findAll().forEach(user -> {
            var map = userMapper.dtoMapper.apply(user);
            result.add(map);
        });
        return result;
    }

    @Override
    public User getUser(long id) {
        return userRepository.findById(id).orElse(null);
    }
}
