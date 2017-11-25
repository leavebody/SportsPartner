package com.sportspartner.model;

public class Person extends User {
    private String userId;
    private String UserName;
    private String address;
    private String gender;
    private int age;
    private double punctuality;
    private int punctualityCount;
    private double participation;
    private int participationCount;
    private String iconUUID;


    public Person(String userId){
        this.userId = userId;
    }

    public Person(String userId, String userName, String address, String gender, int age, double punctuality, int punctualityCount, double participation, int participationCount, String iconUUID) {
        this.userId = userId;
        this.UserName = userName;
        this.address = address;
        this.gender = gender;
        this.age = age;
        this.punctuality = punctuality;
        this.punctualityCount = punctualityCount;
        this.participation = participation;
        this.participationCount = participationCount;
        this.iconUUID = iconUUID;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public double getPunctuality() {
        return punctuality;
    }

    public void setPunctuality(double punctuality) {
        this.punctuality = punctuality;
    }

    public int getPunctualityCount() {
        return punctualityCount;
    }

    public void setPunctualityCount(int punctualityCount) {
        this.punctualityCount = punctualityCount;
    }

    public double getParticipation() {
        return participation;
    }

    public void setParticipation(double participation) {
        this.participation = participation;
    }

    public int getParticipationCount() {
        return participationCount;
    }

    public void setParticipationCount(int participationCount) {
        this.participationCount = participationCount;
    }

    public String getIconUUID() {
        return iconUUID;
    }

    public void setIconUUID(String iconUUID) {
        this.iconUUID = iconUUID;
    }
}
