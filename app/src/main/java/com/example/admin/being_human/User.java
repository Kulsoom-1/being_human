package com.example.admin.being_human;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kulsoom on 12-Jan-18.
 */

public class User {
    String email;
    String pass;
    String name;
    String location;
    String address;
    String DOB ;
    String BG;
    String last_donation;
    String donar_status;
    String gender;
    String contact;

    public User() {
    }

    public User(String name, String BG) {
        this.name = name;
        this.BG = BG;
    }

    public User(String email, String pass, String name, String location, String address, String DOB, String BG, String last_donation, String donar_status, String gender, String contact) {
        this.email = email;
        this.pass = pass;
        this.name = name;
        this.location = location;
        this.address = address;
        this.DOB = DOB;
        this.BG = BG;
        this.last_donation = last_donation;
        this.donar_status = donar_status;
        this.gender = gender;
        this.contact = contact;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getBG() {
        return BG;
    }

    public void setBG(String BG) {
        this.BG = BG;
    }

    public String getLast_donation() {
        return last_donation;
    }

    public void setLast_donation(String last_donation) {
        this.last_donation = last_donation;
    }

    public String getDonar_status() {
        return donar_status;
    }

    public void setDonar_status(String donar_status) {
        this.donar_status = donar_status;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

 /*   public List<User> getDonar() {
        List<User> list = new ArrayList<>();
        list.add(new User( "Zunaira", "B+"));
        list.add(new User( "Ali", "A+"));
        list.add(new User( "Bilal", "AB+"));
        return list;
    }*/
}
