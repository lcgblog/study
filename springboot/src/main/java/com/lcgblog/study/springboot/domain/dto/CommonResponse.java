package com.lcgblog.study.springboot.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class CommonResponse {

    private int code;
    private String message;
    private Map<String, Object> data;

    public static CommonResponse ok(String message){
        return CommonResponse.builder()
                .code(200)
                .message(message)
                .build();
    }

    public static CommonResponse alert(String message){
        return CommonResponse.builder()
                .code(400)
                .message(message)
                .build();
    }

    public static CommonResponse ok(){
        return CommonResponse.builder()
                .code(200)
                .message("ok")
                .build();
    }

    public static CommonResponse ok(Map<String, Object> data){
        return CommonResponse.builder()
                .code(200)
                .data(data)
                .build();
    }

}
