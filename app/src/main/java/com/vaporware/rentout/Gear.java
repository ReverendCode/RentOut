package com.vaporware.rentout;

/**
 * Created by ReverendCode on 1/29/16.
 */
public class Gear {
    private int id;
    private String owner;
    private String type;
    private String size;
    private String serial;
    private String stockNum;
    private String other;

    public Gear() {} //empty constructor
    public Gear(int id, String type, String size, String serial,
                String stockNum, String owner, String other) {
        this.type = type;
        this.size = size;
        this.serial = serial;
        this.stockNum = stockNum;
        this.id = id;
        this.owner = owner; //this may want to be a specific value for creation. E.G. "shop"
        this.other = other;
    }
    public String getStockNum() {
        return stockNum;
    }
    public String getSize() {
        return size;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setStockNum(String stockNum) {
        this.stockNum = stockNum; }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) { //this is the only one you should need after creation
        this.owner = owner;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }


}
