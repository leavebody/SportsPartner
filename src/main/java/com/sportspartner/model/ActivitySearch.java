package com.sportspartner.model;

public class ActivitySearch {
    private String sport;
    private String starttime;
    private String endtime;
    private String place;
    private int capacity;
    private int size;
    private String detail;

    public ActivitySearch() {
    }

    public ActivitySearch(String sport, String starttime, String endtime, String place, int capacity, int size, String detail) {
        this.sport = sport;
        this.starttime = starttime;
        this.endtime = endtime;
        this.place = place;
        this.capacity = capacity;
        this.size = size;
        this.detail = detail;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
