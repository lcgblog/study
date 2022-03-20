package com.lcgblog.study.spring.lifecycle.beans;

public class SuperUser extends User{

    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "SuperUser{" +
                "user='" + super.toString() + '\'' +
                "address='" + address + '\'' +
                '}';
    }
}
