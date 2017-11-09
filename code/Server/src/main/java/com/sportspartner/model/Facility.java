package com.sportspartner.model;

public class Facility {
    private String facilityId;
    private String facilityName;
    private String iconUUID;
    private String sportId;
    private double longitude;
    private double latitude;
    private String providerId;
    private double score;
    private int scoreCount;
    private String openTime;
    private String description;

    public Facility(){}

    public Facility(String facilityId, String facilityName, String iconUUID, String sportId, double longitude, double latitude, String providerId, double score, int scoreCount, String openTime, String description) {
        this.facilityId = facilityId;
        this.facilityName = facilityName;
        this.iconUUID = iconUUID;
        this.sportId = sportId;
        this.longitude = longitude;
        this.latitude = latitude;
        this.providerId = providerId;
        this.score = score;
        this.scoreCount = scoreCount;
        this.openTime = openTime;
        this.description = description;
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

    public String getIconUUID() {
        return iconUUID;
    }

    public void setIconUUID(String iconUUID) {
        this.iconUUID = iconUUID;
    }

    public String getSportId() {
        return sportId;
    }

    public void setSportId(String sportId) {
        this.sportId = sportId;
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

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
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
}