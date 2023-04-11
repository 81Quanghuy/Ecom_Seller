package com.example.ecom_seller.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Product")
public class Product implements Serializable {
    @PrimaryKey
    @NonNull
    private String id;
    private String name;
    private String desciption;
    private Double price;
    private Double promotionaprice;
    private Integer quantity;
    private Integer sold;
    private Boolean isselling;
    private String listimage;
    private String categoryId;
    private Integer rating;
    private String createat;
    private String updateat;

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

    private String barcode;
    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getBarcode() {
        return barcode;
    }
    public void setBarcode(String barcode) {
        this.barcode = barcode;
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
    public String getDesciption() {
        return desciption;
    }
    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public Double getPromotionaprice() {
        return promotionaprice;
    }
    public void setPromotionaprice(Double promotionaprice) {
        this.promotionaprice = promotionaprice;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public Integer getSold() {
        return sold;
    }
    public void setSold(Integer sold) {
        this.sold = sold;
    }
    public Boolean getIsselling() {
        return isselling;
    }
    public void setIsselling(Boolean isselling) {
        this.isselling = isselling;
    }
    public String getListimage() {
        return listimage;
    }
    public void setListimage(String listimage) {
        this.listimage = listimage;
    }

    public Integer getRating() {
        return rating;
    }
    public void setRating(Integer rating) {
        this.rating = rating;
    }

}