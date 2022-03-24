package com.lcgblog.study.spring.lifecycle.beans;

import java.util.List;

public class User {

    private Long id;
    private String name;
    private List<String> list;

    public User() {
    }

    public User(Long id, String name, List<String> list) {
        this.id = id;
        this.name = name;
        this.list = list;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", list=" + list +
                '}';
    }
}
