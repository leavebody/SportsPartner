package com.sportspartner.models;

import java.io.Serializable;

/**
 * Created by yujiaxiao on 10/21/17.
 */

public class Profile implements Serializable {
    private String userId;
    private String userName;
    private String iconUUID;
    private String gender;
    private int age;
    private String address;
    private double punctuality;
    private double participation;

    public Profile(){}

    public Profile(String userId) {
        this.userId = userId;
    }

    public Profile(String userId, String userName, String iconUUID, String gender, int age, String address, double punctuality, double participation) {
        this.userId = userId;
        this.userName = userName;
        this.iconUUID = iconUUID;
        this.gender = gender;
        this.age = age;
        this.address = address;
        this.punctuality = punctuality;
        this.participation = participation;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIconUUID() {
        return iconUUID;
    }

    public void setIconUUID(String iconUUID) {
        this.iconUUID = iconUUID;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getPunctuality() {
        return punctuality;
    }

    public void setPunctuality(double punctuality) {
        this.punctuality = punctuality;
    }

    public double getParticipation() {
        return participation;
    }

    public void setParticipation(double participation) {
        this.participation = participation;
    }
}
