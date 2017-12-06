package com.sportspartner.modelvo;

public class ActivitySearchVO {
    private String sportId;
    private String startTime;
    private String endTime;
    private String place;
    private double latitude;
    private double longitude;
    private int capacity;

    public ActivitySearchVO() {
    }

    public ActivitySearchVO(String sportId, String starttime, String endtime, String place, double latitude, double longitude, int capacity) {
        this.sportId = sportId;
        this.startTime = starttime;
        this.endTime = endtime;
        this.place = place;
        this.latitude = latitude;
        this.longitude = longitude;
        this.capacity = capacity;
    }

    public String getSportId() {
        return sportId;
    }

    public void setSportId(String sportId) {
        this.sportId = sportId;
    }

    public String getStarttime() {
        return startTime;
    }

    public void setStarttime(String starttime) {
        this.startTime = starttime;
    }

    public String getEndtime() {
        return endTime;
    }

    public void setEndtime(String endtime) {
        this.endTime = endtime;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
