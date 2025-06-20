package com.lcgblog.study.springboot;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.lcgblog.study.springboot.domain.entity.User;
import com.lcgblog.study.springboot.domain.entity.UserEntity;
import com.lcgblog.study.springboot.repository.jpa.UserJpaRepository;
import com.lcgblog.study.springboot.service.UserServiceI;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@ActiveProfiles("data-jpa")
@Slf4j
class FlushTest {

    @Autowired
    private UserJpaRepository userJpaRepository;
    
    @Autowired
    private UserServiceI userService;

    @Test
    @Transactional
    void testSaveWithoutFlush() {
        log.info("=== 测试：只save不flush ===");
        
        // 创建用户
        User user = User.builder()
                .username("test_user")
                .password("password")
                .comment("测试用户")
                .build();
        
        UserEntity userEntity = UserEntity.convert(user);
        log.info("准备保存用户: {}", userEntity.getUsername());
        
        // 只save，不flush
        UserEntity savedEntity = userJpaRepository.save(userEntity);
        log.info("save() 执行完成，实体ID: {}", savedEntity.getId());
        log.info("注意：此时数据还在缓存中，没有真正落库");
        
        // 此时数据还在缓存中，没有落库
        // 可以通过查询验证（在同一个事务中）
        assertTrue(userJpaRepository.findById(savedEntity.getId()).isPresent());
        log.info("在同一个事务中查询成功，因为数据在缓存中");
    }

    @Test
    @Transactional
    void testSaveWithFlush() {
        log.info("=== 测试：save + flush ===");
        
        User user = User.builder()
                .username("flush_test_user")
                .password("password")
                .comment("flush测试用户")
                .build();
        
        UserEntity userEntity = UserEntity.convert(user);
        log.info("准备保存用户: {}", userEntity.getUsername());
        
        // save + flush
        UserEntity savedEntity = userJpaRepository.save(userEntity);
        log.info("save() 执行完成，实体ID: {}", savedEntity.getId());
        
        log.info("准备执行 flush() 操作...");
        userJpaRepository.flush();
        log.info("flush() 执行完成，数据已真正落库");
        
        // 此时数据已经落库
        assertTrue(userJpaRepository.findById(savedEntity.getId()).isPresent());
        log.info("验证成功：数据已落库并可查询");
    }

    @Test
    void testBatchOperation() {
        log.info("=== 测试：批量操作 ===");
        
        // 批量操作演示
        for (int i = 0; i < 5; i++) {
            User user = User.builder()
                    .username("batch_user_" + i)
                    .password("password")
                    .comment("批量用户" + i)
                    .build();
            
            UserEntity userEntity = UserEntity.convert(user);
            UserEntity savedEntity = userJpaRepository.save(userEntity);
            log.info("保存用户 {}: ID={}", i, savedEntity.getId());
            // 不立即flush，积累多个操作
        }
        
        log.info("所有用户已保存到缓存，准备执行 flush()...");
        // 最后统一flush，减少数据库交互次数
        userJpaRepository.flush();
        log.info("flush() 执行完成，所有数据已落库");
        
        // 验证所有数据都已保存
        long count = userJpaRepository.findAll().stream()
                .filter(entity -> entity.getUsername().contains("batch_user_"))
                .count();
        assertEquals(5, count);
        log.info("验证成功：共找到 {} 个批量用户", count);
    }


    @Test
    @Transactional
    void testSaveVsFlushDifference() {
        log.info("=== 详细对比：save vs flush ===");
        
        // 测试1：只save，不flush
        log.info("--- 测试1：只save，不flush ---");
        User user1 = User.builder()
                .username("save_only_user")
                .password("password")
                .comment("只save测试")
                .build();
        
        UserEntity entity1 = UserEntity.convert(user1);
        log.info("准备保存用户: {}", entity1.getUsername());
        
        long startTime = System.currentTimeMillis();
        entity1 = userJpaRepository.save(entity1);
        long saveTime = System.currentTimeMillis() - startTime;
        log.info("save() 耗时: {}ms", saveTime);
        log.info("注意：此时没有SQL执行到数据库");
        
        // 测试2：save + flush
        log.info("--- 测试2：save + flush ---");
        User user2 = User.builder()
                .username("save_flush_user")
                .password("password")
                .comment("save+flush测试")
                .build();
        
        UserEntity entity2 = UserEntity.convert(user2);
        log.info("准备保存用户: {}", entity2.getUsername());
        
        startTime = System.currentTimeMillis();
        entity2 = userJpaRepository.save(entity2);
        long saveTime2 = System.currentTimeMillis() - startTime;
        log.info("save() 耗时: {}ms", saveTime2);
        
        startTime = System.currentTimeMillis();
        userJpaRepository.flush();
        long flushTime = System.currentTimeMillis() - startTime;
        log.info("flush() 耗时: {}ms", flushTime);
        log.info("注意：flush() 会执行实际的SQL到数据库");
        
        // 验证结果
        assertNotNull(userJpaRepository.findById(entity1.getId()).orElse(null));
        assertNotNull(userJpaRepository.findById(entity2.getId()).orElse(null));
        log.info("两个用户都已成功保存");
    }

} 