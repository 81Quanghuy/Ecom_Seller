package com.example.ecom_seller.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity(tableName = "product")
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

    private Category category;
    private Double rating;
    private String barcode;
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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

    public Double getRating() {
        return rating;
    }
    public void setRating(Double rating) {
        this.rating = rating;
    }



    public List<Photo> getListPhoto() {

        String listimg = getListimage();
        List<String> photos = Arrays.asList(listimg.split(","));
        List<Photo> photoList = new ArrayList<>();
        for(String w:photos){
            Photo photo = new Photo(w);
            photoList.add(photo);
        }
        return photoList;

    }
}