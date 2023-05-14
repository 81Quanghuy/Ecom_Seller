package com.example.ecom_seller.model;

public class ReponseThongKeProduct {
    Product product ;
    Integer count;
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
    public ReponseThongKeProduct(Product product, Integer count) {
        super();
        this.product = product;
        this.count = count;
    }
    public ReponseThongKeProduct() {
        super();
    }
}
