package com.example.ecom_seller.model;

import java.io.Serializable;
import java.util.Date;

public class Delivery implements Serializable {
    private String id;
    private String name;
    private Float price;
    private String desciption;
    private Date createat;
    private Date updateat;

    public Delivery() {
    }

    public Delivery(String id, String name, Float price, String desciption, Date createat, Date updateat) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.desciption = desciption;
        this.createat = createat;
        this.updateat = updateat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
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
