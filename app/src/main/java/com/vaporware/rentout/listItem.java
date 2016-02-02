package com.vaporware.rentout;

/**
 * Created by ReverendCode on 1/29/16.
 */
public class listItem {
    private int id;
    private String owner;
    private String Type;
    private String other;

    public listItem() {} //empty constructor
    public listItem(int id, String owner, String other) {
        this.id = id;
        this.owner = owner; //this may want to be a specific value for creation. E.G. "shop"
        this.other = other;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
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
