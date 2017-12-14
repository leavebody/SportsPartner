package com.sportspartner.modelvo;


import java.util.Date;
import com.sportspartner.model.*;

public class ActivityOutlineVO {
    private String activityId;
    private String creatorId;
    private String status;
    private String sportIconUUID;
    private String sportName;
    private Date startTime;
    private Date endTime;
    private String facilityId;
    private double longitude;
    private double latitude;
    private String address;
    private int capacity;
    private int size;

    public ActivityOutlineVO(){}

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

    public String getSportIconUUID() {
        return sportIconUUID;
    }

    public void setSportIconUUID(String sportIconUUID) {
        this.sportIconUUID = sportIconUUID;
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

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public ActivityOutlineVO setFromActivity(Activity activity){
        this.activityId = activity.getActivityId();
        this.creatorId = activity.getCreatorId();
        this.status = activity.getStatus();
        this.startTime = activity.getStartTime();
        this.endTime = activity.getEndTime();
        this.facilityId = activity.getFacilityId();
        this.longitude = activity.getLongitude();
        this.latitude = activity.getLatitude();
        this.address = activity.getAddress();
        this.capacity = activity.getCapacity();
        this.size = activity.getSize();
        return this;
    }

    public ActivityOutlineVO setFromSport(Sport sport){
        this.sportName = sport.getSportName();
        this.sportIconUUID = sport.getSportIconUUID();
        return this;
    }
}
