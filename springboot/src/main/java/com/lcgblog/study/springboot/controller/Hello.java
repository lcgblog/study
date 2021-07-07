package com.lcgblog.study.springboot.controller;

import javax.annotation.Resource;
import org.apache.ignite.Ignite;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Hello {

  @Resource
  private Ignite ignite;

  @GetMapping("/hello")
  public String hello(){
    System.out.println(ignite.plugin("Security Plugin").toString());
    return "Hello";
  }

  @PostMapping("/post")
  public String post(){
    ignite.getOrCreateCache("myCache").put("1","aaa");
    return "Done";
  }

  @GetMapping("/get")
  public String get(){
    return ignite.getOrCreateCache("myCache").get("1") + "";
  }
}
