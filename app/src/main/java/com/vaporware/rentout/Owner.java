package com.vaporware.rentout;

/**
 * Created by ReverendCode on 2/1/16.
 */
public class Owner {
    int id;
    String name;
    String phone;
    String other;

    public Owner() {} //empty Constructor

    public Owner(int id, String name, String phone, String other) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.other = other;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
