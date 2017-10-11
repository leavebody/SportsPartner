package com.sportspartner.Models;

import java.util.ArrayList;
import java.util.List;

public class Person extends User {
    private String gender;
    private int age;
    private ArrayList<String> interests;
    private double punctuality;
    private double participation;
    private ArrayList<String> comments;
    //TODO Change this to method??
    private List<Activity> historyActivity;
    private List<Activity> upcommingActivity;

    public Person(){}
    public Person(String gender, int age, ArrayList<String> interests, double punctuality, double participation, ArrayList<String> comments){
        this.gender = gender;
        this.age = age;
        this.interests = new ArrayList<String>(interests);
        this.punctuality = punctuality;
        this.participation = participation;
        this.comments = new ArrayList<String>(comments);
    }
    public Person(String gender, int age, ArrayList<String> interests, double punctuality, double participation, ArrayList<String> comments, String userId, String userName, String password, String email, String address){
        super(userId, userName, password, email, address);
        //new Person(gender, age, interests, punctuality, participation, comments);
        this.gender = gender;
        this.age = age;
        this.interests = new ArrayList<String>(interests);
        this.punctuality = punctuality;
        this.participation = participation;
        this.comments = new ArrayList<String>(comments);
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

    public ArrayList<String> getInterests() {
        return interests;
    }

    public void setInterests(ArrayList<String> interests) {
        this.interests = interests;
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

    public ArrayList<String> getComments() {
        return comments;
    }

    public void setComments(ArrayList<String> comments) {
        this.comments = comments;
    }

    public List<Activity> getHistoryActivity() {
        return historyActivity;
    }

    public void setHistoryActivity(List<Activity> historyActivity) {
        this.historyActivity = historyActivity;
    }

    public List<Activity> getUpcommingActivity() {
        return upcommingActivity;
    }

    public void setUpcommingActivity(List<Activity> upcommingActivity) {
        this.upcommingActivity = upcommingActivity;
    }
}
