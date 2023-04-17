package com.example.ecom_seller.model;

import java.io.Serializable;

public class Photo implements Serializable {
    private String resources;

    public Photo(String resources) {
        this.resources = resources;
    }

    public String getResources() {
        return resources;
    }

    public void setResources(String resources) {
        this.resources = resources;
    }
}