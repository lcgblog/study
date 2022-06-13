package com.lcgblog.opentelemetry.service.c.source;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.extension.annotations.SpanAttribute;
import io.opentelemetry.extension.annotations.WithSpan;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaConsumer {

    @WithSpan
    @KafkaListener(topics={"open.telemetry.topic"})
    public void consumeMessage(String message, @SpanAttribute("test")String test){
        log.info("Received message {}", message);
        log.info("test {}", test);
    }


}
