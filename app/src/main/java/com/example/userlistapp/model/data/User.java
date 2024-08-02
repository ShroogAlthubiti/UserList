package com.example.userlistapp.model.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

public class User {
    private int id =0 ;
    private String name;
    private String email;
    private String status;
    private String gender;

    public User(String name,String email,String status,String gender){
        this.name = name;
        this.email = email;
        this.status = status;
        this.gender = gender;

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

}