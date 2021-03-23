package com.alzheimerapp.pojo.auth;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

public class User implements Serializable {

    private String uid;

    private String image;

    private String username;

    private String password;

    private String phone;

    private String email;

    private String location;

    private String relation_type;


    public User() {
    }


    public User(String uid, String image, String username, String password, String phone, String email, String location, String relation_type) {
        this.uid = uid;
        this.image = image;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.location = location;
        this.relation_type = relation_type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRelation_type() {
        return relation_type;
    }

    public void setRelation_type(String relation_type) {
        this.relation_type = relation_type;
    }


}
