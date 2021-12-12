package com.lcgblog.study.spring.ioc.overview.domain;

public class Like {

    private String like;

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    @Override
    public String toString() {
        return "Like{" +
                "like='" + like + '\'' +
                '}';
    }
}
