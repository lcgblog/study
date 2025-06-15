package com.lcgblog.study.springboot.service.jdbc;

import com.lcgblog.study.springboot.domain.entity.User;
import com.lcgblog.study.springboot.domain.error.ServiceException;
import com.lcgblog.study.springboot.domain.mapper.UserMapper;
import com.lcgblog.study.springboot.service.UserServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@ConditionalOnProperty(prefix = "lcgblog.study.jdbc", name = "active", havingValue = "true")
public class UserServiceJdbcImpl implements UserServiceI {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserMapper userMapper;

    @Override
    public void registerUser(User user) {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));
        jdbcTemplate.update(
                userMapper.getInsertSql(),
                userMapper.getInsertParams(user, now)
        );
    }

    @Override
    public void deleteUser(long id) {
        jdbcTemplate.update(
                userMapper.getDeleteSql(),
                userMapper.getDeleteParams(id)
        );
    }

    @Override
    public void updateUserComment(long id, String comment) {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));
        User user = getUser(id);
        if (user == null) {
            throw new ServiceException("No such user");
        }
        user.setComment(comment);
        user.setUpdatedTime(now);
        jdbcTemplate.update(
                userMapper.getUpdateSql(),
                userMapper.getUpdateParams(user)
        );
    }

    @Override
    public List<Map<String, Object>> listUsers() {
        return jdbcTemplate.query(
                        userMapper.getSelectAllSql(),
                        userMapper.rowMapper
                ).stream()
                .map(userMapper.dtoMapper)
                .collect(Collectors.toList());
    }

    @Override
    public User getUser(long id) {
        return jdbcTemplate.query(
                userMapper.getSelectByIdSql(),
                userMapper.rowMapper,
                userMapper.getSelectByIdParams(id)
        ).stream().findFirst().orElse(null);
    }
}
