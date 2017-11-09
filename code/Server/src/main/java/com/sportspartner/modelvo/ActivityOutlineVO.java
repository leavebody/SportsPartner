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
    private int capacity;
    private int size;

    public ActivityOutlineVO(){}

    public ActivityOutlineVO(String activityId, String creatorId, String status, String sportIconUUID,  String sportName, Date startTime, Date endTime, String facilityId, double longitude, double latitude, int capacity, int size) {
        this.activityId = activityId;
        this.creatorId = creatorId;
        this.status = status;
        this.sportIconUUID = sportIconUUID;
        this.sportName = sportName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.facilityId = facilityId;
        this.longitude = longitude;
        this.latitude = latitude;
        this.capacity = capacity;
        this.size = size;
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

    public String getLocation() {
        return facilityId;
    }

    public void setLocation(String location) {
        this.facilityId = location;
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

    public void setFromActivity(Activity activity){
        this.activityId = activity.getActivityId();
        this.creatorId = activity.getCreatorId();
        this.status = activity.getStatus();
        this.startTime = activity.getStartTime();
        this.endTime = activity.getEndTime();
        this.facilityId = activity.getFacilityId();
        this.longitude = activity.getLongitude();
        this.latitude = activity.getLatitude();
        this.capacity = activity.getCapacity();
        this.size = activity.getSize();
    }

    public void setFromSport(Sport sport){
        this.sportName = sport.getSportName();
        this.sportIconUUID = sport.getSportIconUUID();
    }
}
