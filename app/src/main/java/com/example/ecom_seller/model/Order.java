package com.example.ecom_seller.model;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {
    private String id;
    private User user;
    private String address;
    private String phone;
    private StatusOrder statusOrder; // Trang thai don hang
    private Double price;
    private String createat;
    private String updateat;
    private Boolean isactive;

    public Order() {
    }

    public Order(String id, User user, String address, String phone, StatusOrder status, Double price, String createat, String updateat, Boolean isactive) {
        this.id = id;
        this.user = user;
        this.address = address;
        this.phone = phone;
        this.statusOrder = status;
        this.price = price;
        this.createat = createat;
        this.updateat = updateat;
        this.isactive = isactive;
    }

    public String getId() {
        return id;
    }

    public StatusOrder getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(StatusOrder statusOrder) {
        this.statusOrder = statusOrder;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCreateat() {
        return createat;
    }

    public void setCreateat(String createat) {
        this.createat = createat;
    }

    public String getUpdateat() {
        return updateat;
    }

    public void setUpdateat(String updateat) {
        this.updateat = updateat;
    }

    public Boolean getIsactive() {
        return isactive;
    }

    public void setIsactive(Boolean isactive) {
        this.isactive = isactive;
    }
}
