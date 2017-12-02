package com.sportspartner.model;

public class ActivitySearch {
    private String sportId;
    private String starttime;
    private String endtime;
    private String place;
    private double latitude;
    private double longitude;
    private int capacity;

    public ActivitySearch() {
    }

    public ActivitySearch(String sport, String starttime, String endtime, String place, double latitude, double longitude, int capacity) {
        this.sportId = sport;
        this.starttime = starttime;
        this.endtime = endtime;
        this.place = place;
        this.latitude = latitude;
        this.longitude = longitude;
        this.capacity = capacity;
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

    public String getSport() {
        return sportId;
    }

    public void setSport(String sport) {
        this.sportId = sport;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
