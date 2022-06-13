package com.lcgblog.opentelemetry.service.b.sink;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaSink {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sinkMessage(String message){
        log.info("sink message {}", message);
        kafkaTemplate.send("open.telemetry.topic", "key1", message).addCallback(result -> {
            log.info("Succeed to publish {}", message);
        }, ex ->{});
    }

}
