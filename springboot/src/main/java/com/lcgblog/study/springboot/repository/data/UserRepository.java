package com.lcgblog.study.springboot.repository.data;

import com.lcgblog.study.springboot.domain.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
