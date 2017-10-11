package com.sportspartner.Models;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class Activity {
    private String activityId;
    private String activitySate;
    private String sport;
    private Time time;
    private String location;
    private int capacity;
    private int numMember;
    private String creatorId;
    private List<String> memberList;
    private String description;
    private List<String> discussion;

    public Activity(){}
    public Activity(String activityId, String activitySate, String sport, Time time, String location, int capacity, int numMember, String creatorId, List<String> memberList, String description, List<String> discussion){
        this.activityId = activityId;
        this.activitySate = activitySate;
        this.sport = sport;
        this.time = time;
        this.location =location;
        this.capacity = capacity;
        this.numMember = numMember;
        this.creatorId = creatorId;
        this.memberList = new ArrayList<>(memberList);
        this.description = description;
        this.discussion = new ArrayList<>(discussion);
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getActivitySate() {
        return activitySate;
    }

    public void setActivitySate(String activitySate) {
        this.activitySate = activitySate;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getNumMember() {
        return numMember;
    }

    public void setNumMember(int numMember) {
        this.numMember = numMember;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public List<String> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<String> memberList) {
        this.memberList = memberList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getDiscussion() {
        return discussion;
    }

    public void setDiscussion(List<String> discussion) {
        this.discussion = discussion;
    }
}
