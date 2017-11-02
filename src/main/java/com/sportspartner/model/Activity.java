package com.sportspartner.model;

import java.util.Date;

public class Activity {
    private String activityId;
    private String creatorId;
    private String facilityId;
    private String status;
    private String sportId;
    private Date startTime;
    private Date endTime;
    private int capacity;
    private int size;
    private String description;

    public Activity() {
    }

    public Activity(String activityId, String creatorId, String facilityId, String status, String sportId, Date startTime, Date endTime, int capacity, int size, String description) {
        this.activityId = activityId;
        this.creatorId = creatorId;
        this.facilityId = facilityId;
        this.status = status;
        this.sportId = sportId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.capacity = capacity;
        this.size = size;
        this.description = description;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSportId() {
        return sportId;
    }

    public void setSportId(String sportId) {
        this.sportId = sportId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        description = description;
    }
}

