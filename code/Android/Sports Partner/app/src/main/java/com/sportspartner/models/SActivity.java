package com.sportspartner.models;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by yujiaxiao on 10/24/17.
 */

public class SActivity {
    private String activityId;
    private String status;
    private String sportIconUUID;
    private String sportName;
    private String sportId;
    private Date startTime;
    private Date endTime;
    private String facilityId;
    private String facilitiName;
    private Double longitude;
    private Double latitude;
    private String zipcode;
    private int capacity;
    private int size;
    private String creatorId;
    private ArrayList<UserOutline> members;
    private String detail;
    private String description;
    private ArrayList<SactivityComment> discussion;

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

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
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

    public String getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
    }

    public String getFacilitiName() {
        return facilitiName;
    }

    public void setFacilitiName(String facilitiName) {
        this.facilitiName = facilitiName;
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

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
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

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<SactivityComment> getDiscussion() {
        return discussion;
    }

    public void setDiscussion(ArrayList<SactivityComment> discussion) {
        this.discussion = discussion;
    }
}
