package com.example.saiful.contactbackup;


public class Contact {
    String id, title, mobile, desk, email;

    public Contact() {

    }

    public Contact(String id, String title, String mobile, String desk, String email) {
        this.id = id;
        this.title = title;
        this.mobile = mobile;
        this.desk = desk;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getMobile() {
        return mobile;
    }

    public String getDesk() {
        return desk;
    }

    public String getEmail() {
        return email;
    }
}
