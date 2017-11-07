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
    private String sport;
    private Date startTime;
    private Date endTime;
    private String facilityId;
    private String facilityName;
    private Double longitude;
    private Double latitude;
    private int capacity;
    private int size;
    private String creator;
    private ArrayList<UserOutline> members;
    private String detail;
    private ArrayList<SactivityComment> discussion;
    //TODO more to add


    public SActivity() {}

    public SActivity(String id, String status, String sportIconUUID, String sport, Date startTime, Date endTime, String facilityId, String facilityName, Double longitude, Double latitude, int capacity, int size, String creator, ArrayList<UserOutline> members, String detail, ArrayList<SactivityComment> discussion) {
        this.id = id;
        this.status = status;
        this.sportIconUUID = sportIconUUID;
        this.sport = sport;
        this.startTime = startTime;
        this.endTime = endTime;
        this.facilityId = facilityId;
        this.facilityName = facilityName;
        this.longitude = longitude;
        this.latitude = latitude;
        this.capacity = capacity;
        this.size = size;
        this.creator = creator;
        this.members = members;
        this.detail = detail;
        this.discussion = discussion;
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

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
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

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public ArrayList<UserOutline> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<UserOutline> members) {
        this.members = members;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public ArrayList<SactivityComment> getDiscussion() {
        return discussion;
    }

    public void setDiscussion(ArrayList<SactivityComment> discussion) {
        this.discussion = discussion;
    }
}
