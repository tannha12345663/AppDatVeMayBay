package com.example.appdatvemaybay.Account_User;

public class GetUserFirebase {
    String UID,Name,Email,Phone;

    public GetUserFirebase(String UID, String email, String name, String phone) {
        this.UID = UID;
        this.Name=name;
        this.Email = email;
        this.Phone = phone;
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


}
