package com.example.womeniya;

public class phone_number {
    private int id;
    private String phoneNum;

    public phone_number() {
    }

    public phone_number(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

}
