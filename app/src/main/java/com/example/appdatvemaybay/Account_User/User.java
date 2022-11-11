package com.example.appdatvemaybay.Account_User;

import android.net.Uri;

public class User {
    String UID,Name,Email,Phone, imgUrl;

    public User(String UID, String email,String name, String phone, String imgUrl) {
        this.UID = UID;
        this.Name=name;
        this.Email = email;
        this.Phone=phone;
        this.imgUrl=imgUrl;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
