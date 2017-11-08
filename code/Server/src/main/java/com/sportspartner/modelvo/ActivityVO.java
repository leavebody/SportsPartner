package com.sportspartner.modelvo;

import com.sportspartner.model.*;
import com.sportspartner.modelvo.UserOutlineVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class ActivityVO {
    private String id;
    private String status;
    private String sportIconUUID;
    private String sportIconPath;
    private String sportName;
    private Date startTime;
    private Date endTime;
    private String facilityId;
    private String facilityName;
    private double longitude;
    private double latitude;
    private int capacity;
    private int size;
    private String creatorId;
    private List <UserOutlineVO> members;
    private String detail;
    private List <ActivityComment> discussion;

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getSportIconUUID() {
        return sportIconUUID;
    }

    public String getSportIconPath() {
        return sportIconPath;
    }

    public String getSportName() {
        return sportName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public String getFacilityId() {
        return facilityId;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getSize() {
        return size;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public List<UserOutlineVO> getMembers() {
        return members;
    }
    public String getDetail(){
        return detail;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSportIconUUID(String sportIconUUID) {
        this.sportIconUUID = sportIconUUID;
    }

    public void setSportIconPath(String sportIconPath) {
        this.sportIconPath = sportIconPath;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public void setMembers(List<UserOutlineVO> members) {
        this.members = members;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public List<ActivityComment> getDiscussion() {
        return discussion;
    }

    public void setDiscussion(List<ActivityComment> discussion) {
        this.discussion = discussion;
    }

    public boolean isMissingField(){
        return this.id.equals(null);
    }

    public void setFromActivity(Activity activity){
        this.id = activity.getActivityId();
        this.status = activity.getStatus();
        this.startTime = activity.getStartTime();
        this.endTime = activity.getEndTime();
        this.facilityId = activity.getFacilityId();
        this.longitude = activity.getLongitude();
        this.latitude = activity.getLatitude();
        this.capacity = activity.getCapacity();
        this.size = activity.getSize();
        this.creatorId = activity.getCreatorId();
        this.detail = activity.getDescription();
    }
    public void setFromSport (Sport sport){
        this.sportName = sport.getSportName();
        this.sportIconUUID = sport.getSportIconUUID();
    }


    public void setFromFacility(Facility facility){
        this.facilityId = facility.getFacilityId();
        this.facilityName = facility.getFacilityName();
    }

    public void setFromComments (List<ActivityComment> comments){
        this.discussion = comments;
    }
}
