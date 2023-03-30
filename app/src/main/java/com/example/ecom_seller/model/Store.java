package com.example.ecom_seller.model;

import java.io.Serializable;
import java.util.Date;

public class Store implements Serializable {
    private String id;
    private String name;
    private String bio;// mô tả
    private String about;
    private User user;
    private Boolean isactive;// được duyệt đưa vào bán
    private String avatar;// ảnh của shop
    private String featuredimages;
    private Integer rating;
    private Date createat;
    private Date updaeat;

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

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getIsactive() {
        return isactive;
    }

    public void setIsactive(Boolean isactive) {
        this.isactive = isactive;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFeaturedimages() {
        return featuredimages;
    }

    public void setFeaturedimages(String featuredimages) {
        this.featuredimages = featuredimages;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Date getCreateat() {
        return createat;
    }

    public void setCreateat(Date createat) {
        this.createat = createat;
    }

    public Date getUpdaeat() {
        return updaeat;
    }

    public void setUpdaeat(Date updaeat) {
        this.updaeat = updaeat;
    }
}
