package com.sportspartner.models;

/**
 * @author Xiaochen Li
 */

public class FacilityOutline {
    private String facilityId;
    private String facilityName;
    private String sportId;
    private String sportName;
    private String sportUUID;
    private double longitude;
    private double latitude;
    private String address;
    private double score;

    public String getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(String facility) {
        this.facilityId = facility;
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

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public String getSportUUID() {
        return sportUUID;
    }

    public void setSportUUID(String sportUUID) {
        this.sportUUID = sportUUID;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
