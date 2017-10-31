package com.sportspartner.models;

import java.util.ArrayList;

/**
 * Created by yujiaxiao on 10/21/17.
 */

public class Profile {
    private String userId;
    private String userName;
    private String iconUUID;
    private String iconPath;
    private String gender;
    private String age;
    private String address;
    private double punctuality;
    private double participation;

    public Profile(){}


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

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
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
