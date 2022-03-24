package com.lcgblog.study.spring.lifecycle.beans;

public class AutowiredUser {

    private User user;

    public AutowiredUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "AutowiredUser{" +
                "user=" + user +
                '}';
    }
}
