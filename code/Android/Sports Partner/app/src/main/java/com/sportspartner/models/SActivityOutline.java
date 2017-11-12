package com.sportspartner.models;


import java.util.Date;
import java.util.List;
import 	java.util.GregorianCalendar;

/**
 * Created by yujiaxiao on 10/21/17.
 */

public class SActivityOutline {
    private String activityId;
    private String status;
    private String sportIconUUID;
    private String sportIconPath;
    private String sportName;
    private Date startTime;
    private Date endTime;
    private String facilityId;
    private int capacity;
    private int size;

    public SActivityOutline(String activityId, String status, String sportIconUUID, String sportIconPath, String sportName, Date startTime, Date endTime, String facilityId, int capacity, int size) {
        this.activityId = activityId;
        this.status = status;
        this.sportIconUUID = sportIconUUID;
        this.sportIconPath = sportIconPath;
        this.sportName = sportName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.facilityId = facilityId;
        this.capacity = capacity;
        this.size = size;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSportIconUUID() {
        return sportIconUUID;
    }

    public void setSportIconUUID(String sportIconUUID) {
        this.sportIconUUID = sportIconUUID;
    }

    public String getSportIconPath() {
        return sportIconPath;
    }

    public void setSportIconPath(String sportIconPath) {
        this.sportIconPath = sportIconPath;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
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

    public String getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
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
}
