package com.lcgblog.study.flink.models;

public class Alert {

    private Long timestamp;
    private String message;

    private Alert(String message, Long timestamp) {
        this.timestamp = timestamp;
        this.message = message;
    }

    public static Alert of(String message, Long timestamp) {
        return new Alert(message, timestamp);
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Alert{" +
                "timestamp=" + timestamp +
                ", message='" + message + '\'' +
                '}';
    }
}
