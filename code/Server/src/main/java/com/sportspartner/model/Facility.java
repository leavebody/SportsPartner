package com.sportspartner.model;

public class Facility{
    private String facilityId;
    private String providerId;
    private String facilityName;
    private String sport;
    private String address;
    private String placeId;
    private String openTime;
    private String description;
    private String icon;
    private double score;
    private int scoreCount;

    public Facility(String facilityId, String providerId, String facilityName, String sport, String address, String placeId, String openTime, String description, String icon, double score, int scoreCount) {
        this.facilityId = facilityId;
        this.providerId = providerId;
        this.facilityName = facilityName;
        this.sport = sport;
        this.address = address;
        this.placeId = placeId;
        this.openTime = openTime;
        this.description = description;
        this.icon = icon;
        this.score = score;
        this.scoreCount = scoreCount;
    }

    public Facility(){}

    public String getFacilityId() {
        return facilityId;
    }

    public String getProviderId() {
        return providerId;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public String getSport() {
        return sport;
    }

    public String getAddress() {
        return address;
    }

    public String getPlaceId() {
        return placeId;
    }

    public String getOpenTime() {
        return openTime;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }

    public double getScore() {
        return score;
    }

    public int getScoreCount() {
        return scoreCount;
    }

}
