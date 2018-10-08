package com.example.admin.being_human;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kulsoom on 07-Jan-18.
 */

public class Requests {
    String userName;
    String phoneNumber;
    String gender;
    String id;
    String afd;

    public Requests(String userName, String phoneNumber, String gender, String id, String afd) {
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.id = id;
        this.afd = afd;
    }

    public String getAfd() {
        return afd;
    }

    public void setAfd(String afd) {
        this.afd = afd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {

        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
