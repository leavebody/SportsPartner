package com.sportspartner.models;

/**
 * Created by yujiaxiao on 12/9/17.
 */

public class ActivitySearch {
    private String sportId;
    private String startTime;
    private String endTime;
    private String facilityId;
    private double longitude;
    private double latitude;
    private int capacity;

    public ActivitySearch() {
        this.sportId = "NULL";
        this.capacity = -1;
        this.longitude = 1000;
        this.latitude = 1000;
        this.facilityId = "NULL";
        this.startTime = "NULL";
        this.endTime = "NULL";
    }

    public ActivitySearch(String sportId, String startTime, String endTime, String facilityId, double longitude, double latitude, int capacity) {
        this.sportId = sportId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.facilityId = facilityId;
        this.longitude = longitude;
        this.latitude = latitude;
        this.capacity = capacity;
    }

    public String getSportId() {
        return sportId;
    }

    public void setSportId(String sportId) {
        this.sportId = sportId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
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
}
