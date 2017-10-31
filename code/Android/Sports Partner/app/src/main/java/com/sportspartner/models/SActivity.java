package com.sportspartner.models;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by yujiaxiao on 10/24/17.
 */

public class SActivity {
    private String id;
    private String status;
    private String sportIconUUID;
    private String sportIconPath;
    private String sport;
    private Date starttime;
    private Date endtime;
    private String place;
    private int capacity;
    private int size;
    private String creator;
    //TODO more to add


    public SActivity() {}

    public SActivity(String id, String status, String sportIconUUID, String sportIconPath, String sport, Date starttime, Date endtime, String place, int capacity, int size, String creator) {
        this.id = id;
        this.status = status;
        this.sportIconUUID = sportIconUUID;
        this.sportIconPath = sportIconPath;
        this.sport = sport;
        this.starttime = starttime;
        this.endtime = endtime;
        this.place = place;
        this.capacity = capacity;
        this.size = size;
        this.creator = creator;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
