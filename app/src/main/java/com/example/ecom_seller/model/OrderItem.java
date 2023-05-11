package com.example.ecom_seller.model;

import java.io.Serializable;
import java.util.Date;

public class OrderItem implements Serializable {
    private String id;
    private Order order;
    private Product product;
    private Integer count;

    public OrderItem(String id, Order order, Product product, Integer count) {
        this.id = id;
        this.order = order;
        this.product = product;
        this.count = count;
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

}
