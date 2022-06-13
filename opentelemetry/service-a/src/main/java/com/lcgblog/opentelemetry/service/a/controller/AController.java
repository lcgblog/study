package com.lcgblog.opentelemetry.service.a.controller;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Span;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AController {

    private final RestTemplate restTemplate;

    @SneakyThrows
    @GetMapping("/a/case1")
    public String serviceA(){
        log.info("A is invoked");
        return CompletableFuture.supplyAsync(()->
                restTemplate.getForObject("http://localhost:8081/b/case1", String.class)).get();
    }
}
