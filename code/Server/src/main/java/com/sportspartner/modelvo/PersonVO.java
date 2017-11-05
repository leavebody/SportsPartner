package com.sportspartner.modelvo;

import com.sportspartner.model.Person;

public class PersonVO {
    private String userId;
    private String userName;
    private String address;
    private String gender;
    private int age;
    private double punctuality;
    private double participation;
    private String iconUUID;

    public PersonVO(){}

    public PersonVO(String userId, String userName, String address, String gender, int age, double punctuality, double participation, String iconUUID) {
        this.userId = userId;
        this.userName = userName;
        this.address = address;
        this.gender = gender;
        this.age = age;
        this.punctuality = punctuality;
        this.participation = participation;
        this.iconUUID = iconUUID;
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

    public double getParticipation() {
        return participation;
    }

    public void setParticipation(double participation) {
        this.participation = participation;
    }

    public String getIconUUID() {
        return iconUUID;
    }

    public void setIconUUID(String iconUUID) {
        this.iconUUID = iconUUID;
    }

    public boolean isMissingField(){
        return this.userId.equals(null);
    }

    public void cast2Person(Person person){
        person.setUserName(this.userName);
        person.setAddress(this.address);
        person.setGender(this.gender);
        person.setAge(this.age);
        person.setPunctuality(this.punctuality);
        person.setParticipation(this.participation);
        person.setIconUUID(this.iconUUID);

    }

    public void setFromPerson(Person person){
        this.userId = person.getUserId();
        this.userName = person.getUserName();
        this.address = person.getAddress();
        this.gender = person.getGender();
        this.age = person.getAge();
        this.punctuality = person.getPunctuality();
        this.participation = person.getParticipation();
        this.iconUUID = person.getIconUUID();
    }
}
