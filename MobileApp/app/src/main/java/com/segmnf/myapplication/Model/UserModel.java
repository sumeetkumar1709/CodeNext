package com.segmnf.myapplication.Model;

public class UserModel {
    String uid;
    String name;
    String bio;
    String image;
    String role;

    public UserModel(String uid ,String name, String bio, String image, String role) {
        this.name = name;
        this.uid= uid;
        this.bio = bio;
        this.image = image;
        this.role = role;
    }

    public UserModel() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
