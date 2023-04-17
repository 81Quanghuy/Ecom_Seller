package com.example.ecom_seller.model;

import java.io.Serializable;
import java.util.Date;

public class Delivery implements Serializable {
    private String id;
    private String desciption;
    private Date createat;
    private Date updateat;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Delivery() {
    }

    public Delivery(String id, String desciption, Date createat, Date updateat,User user) {
        this.id = id;
        this.desciption = desciption;
        this.createat = createat;
        this.updateat = updateat;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public Date getCreateat() {
        return createat;
    }

    public void setCreateat(Date createat) {
        this.createat = createat;
    }

    public Date getUpdateat() {
        return updateat;
    }

    public void setUpdateat(Date updateat) {
        this.updateat = updateat;
    }
}
