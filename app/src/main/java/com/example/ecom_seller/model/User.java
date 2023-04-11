package com.example.ecom_seller.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "User")
public class User implements Serializable {
    @PrimaryKey
    @NonNull
    private String  id;

    private String fullName;
    private String email;
    private String phone;
    private String username;
    private String password;// mật khẩu mã hóa
    private String address;
    private String avatar;
    private String role;
    private Boolean isActive;
    private String resetpasswordtoken;

//    private Date createat;
//    private Date updaeat;

   // private Store stores;

//    public Date getCreateat() {
//        return createat;
//    }
//
//    public void setCreateat(Date createat) {
//        this.createat = createat;
//    }
//
//    public Date getUpdaeat() {
//        return updaeat;
//    }
//
//    public void setUpdaeat(Date updaeat) {
//        this.updaeat = updaeat;
//    }

//    public Store getStores() {
//        return stores;
//    }
//
//    public void setStores(Store stores) {
//        this.stores = stores;
//    }


    public User() {
    }

    public User(@NonNull String id, String fullName, String email, String phone, String username, String password, String address, String avatar, String role, Boolean isActive, String resetpasswordtoken) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.username = username;
        this.password = password;
        this.address = address;
        this.avatar = avatar;
        this.role = role;
        this.isActive = isActive;
        this.resetpasswordtoken = resetpasswordtoken;
        //this.createat = createat;
        //this.updaeat = updaeat;
       // this.stores = stores;
    }

    public User(String id, String username, String pass) {
        this.id = id;
        this.username = username;
        this.password = pass;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getResetpasswordtoken() {
        return resetpasswordtoken;
    }

    public void setResetpasswordtoken(String resetpasswordtoken) {
        this.resetpasswordtoken = resetpasswordtoken;
    }

}
