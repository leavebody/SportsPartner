package com.sportspartner.Models;

import java.util.List;

public class FacilityProvider extends User{
    private String location;
    private String openingTime;
    //TODO String PHOTO?
    private String photo;
    private List<Facility> facilities;
    private List<Activity> pendingActivities;

    public FacilityProvider(String location, String openingTime, String photo, List<Facility> facilities, List<Activity> pendingActivities) {
        this.location = location;
        this.openingTime = openingTime;
        this.photo = photo;
        this.facilities = facilities;
        this.pendingActivities = pendingActivities;
    }

    public FacilityProvider(String userId, String userName, String password, String email, String address, String location, String openingTime, String photo, List<Facility> facilities, List<Activity> pendingActivities) {
        super(userId, userName, password, email, address);
        this.location = location;
        this.openingTime = openingTime;
        this.photo = photo;
        this.facilities = facilities;
        this.pendingActivities = pendingActivities;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public List<Facility> getFacilities() {
        return facilities;
    }

    public void setFacilities(List<Facility> facilities) {
        this.facilities = facilities;
    }

    public List<Activity> getPendingActivities() {
        return pendingActivities;
    }

    public void setPendingActivities(List<Activity> pendingActivities) {
        this.pendingActivities = pendingActivities;
    }
}
