package com.lcgblog.opentelemetry.service.b.controller;

import com.lcgblog.opentelemetry.service.b.sink.KafkaSink;
import io.opentelemetry.api.OpenTelemetry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BController {

    private final KafkaSink kafkaSink;

    private final OpenTelemetry openTelemetry;

    @GetMapping("/b/case1")
    public String serviceB(@RequestHeader MultiValueMap<String,String> headers){
        log.info("header:{}",headers);
        log.info("B is invoked");
        kafkaSink.sinkMessage("invoke c");
        return "B is invoked";
    }
}
