package com.example.admin.being_human;

import java.security.PublicKey;

/**
 * Created by zunaira on 02-Jan-18.
 */

public class UserSignup  {
    public String bloodGroup;
    public String name;
    public String email;
    public String pass;
    public String age;
    public String city;
    public String lastDonation;
    public String phoneNumber;
    public String address;
    public boolean active;
    public double latitude;
    public double longitude;
    public String gender;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
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

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLastDonation() {
        return lastDonation;
    }

    public void setLastDonation(String lastDonation) {
        this.lastDonation = lastDonation;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public UserSignup() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public UserSignup(String password, String email,String name, String bloodGroup,String age, String city,String lastDonation, String phoneNumber,String address,boolean active) {
        this.bloodGroup=bloodGroup;
        this.name=name;
        this.age=age;
        this.city=city;
        this.address=address;
        this.lastDonation=lastDonation;
        this.phoneNumber=phoneNumber;
        this.email = email;
        this.pass = password;
        this.active=active;
    }
}
