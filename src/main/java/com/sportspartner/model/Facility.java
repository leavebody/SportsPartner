package com.sportspartner.model;

public class Facility{
    private String facilityId;
    private String providerId;
    private String facilityName;
    private String sportId;
    private String address;
    private String placeId;
    private String openTime;
    private String description;
    private String icon;
    private double score;
    private int scoreCount;

    public Facility(){}

    public Facility(String facilityId, String providerId, String facilityName, String sportId, String address, String placeId, String openTime, String description, String icon, double score, int scoreCount) {
        this.facilityId = facilityId;
        this.providerId = providerId;
        this.facilityName = facilityName;
        this.sportId = sportId;
        this.address = address;
        this.placeId = placeId;
        this.openTime = openTime;
        this.description = description;
        this.icon = icon;
        this.score = score;
        this.scoreCount = scoreCount;
    }

    public String getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public String getSportId() {
        return sportId;
    }

    public void setSportId(String sportId) {
        this.sportId = sportId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getScoreCount() {
        return scoreCount;
    }

    public void setScoreCount(int scoreCount) {
        this.scoreCount = scoreCount;
    }
}
