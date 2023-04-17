package com.example.ecom_seller.model;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {
    private String id;
    private User user;
    private Delivery delivery;
    private String address;
    private String phone;
    private String status; // Trang thai don hang
    private Float price;
    private Date createat;
    private Date updateat;
    private Boolean isactive;

    public Order() {
    }

    public Order(String id, User user,Delivery delivery, String address, String phone, String status, Float price, Date createat, Date updateat, Boolean isactive) {
        this.id = id;
        this.user = user;
        this.delivery = delivery;
        this.address = address;
        this.phone = phone;
        this.status = status;
        this.price = price;
        this.createat = createat;
        this.updateat = updateat;
        this.isactive = isactive;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
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

    public Boolean getIsactive() {
        return isactive;
    }

    public void setIsactive(Boolean isactive) {
        this.isactive = isactive;
    }
}
