package com.lcgblog.study.springboot.repository.data;

import com.lcgblog.study.springboot.domain.entity.User;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.repository.CrudRepository;

@ConditionalOnProperty(prefix = "lcgblog.study.data.jdbc", name = "active", havingValue = "true")
public interface UserRepository extends CrudRepository<User, Long> {
}
