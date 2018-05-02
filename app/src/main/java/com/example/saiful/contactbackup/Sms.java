package com.example.saiful.contactbackup;

/**
 * Created by saiful on 19/1/2018.
 */
public class Sms {
    String number,body;

    public Sms(String number, String body) {
        this.number = number;
        this.body = body;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getNumber() {
        return number;
    }

    public String getBody() {
        return body;
    }
}
