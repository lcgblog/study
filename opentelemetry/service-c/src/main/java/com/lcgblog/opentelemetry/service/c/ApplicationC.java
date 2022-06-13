package com.lcgblog.opentelemetry.service.c;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableKafka
public class ApplicationC {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationC.class);
    }

    @Bean
    public NewTopic topic() {
        return new NewTopic("open.telemetry.topic", 1, (short) 1);
    }

}
