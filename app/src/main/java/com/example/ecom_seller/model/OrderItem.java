package com.example.ecom_seller.model;

import java.io.Serializable;
import java.util.Date;

public class OrderItem implements Serializable {
    private String id;
    private Order order;
    private Product product;
    private Integer count;
    private Date createat;
    private Date updateat;

    public OrderItem(String id, Order order, Product product, Integer count, Date createat, Date updateat) {
        this.id = id;
        this.order = order;
        this.product = product;
        this.count = count;
        this.createat = createat;
        this.updateat = updateat;
    }

    public OrderItem() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
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
