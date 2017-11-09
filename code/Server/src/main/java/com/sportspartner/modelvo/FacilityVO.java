package com.sportspartner.modelvo;

import com.sportspartner.model.Facility;
import com.sportspartner.model.Provider;
import com.sportspartner.model.Sport;

public class FacilityVO {
    private String facilityId;
    private String facilityName;
    private String providerId;
    private String providerName;
    private String iconUUID;
    private String sportId;
    private String sportName;
    private String sportIconUUID;
    private double longitude;
    private double latitude;
    private double score;
    private String openTime;
    private String description;

    public FacilityVO(){}

    public FacilityVO(String facilityId, String facilityName, String providerId, String providerName, String iconUUID, String sportId, String sportName, String sportIconUUID, double longitude, double latitude, double score, String openTime, String description) {
        this.facilityId = facilityId;
        this.facilityName = facilityName;
        this.providerId = providerId;
        this.providerName = providerName;
        this.iconUUID = iconUUID;
        this.sportId = sportId;
        this.sportName = sportName;
        this.sportIconUUID = sportIconUUID;
        this.longitude = longitude;
        this.latitude = latitude;
        this.score = score;
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

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
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

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public String getSportIconUUID() {
        return sportIconUUID;
    }

    public void setSportIconUUID(String sportIconUUID) {
        this.sportIconUUID = sportIconUUID;
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

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
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

    public void setFromFacility(Facility facility){
        this.facilityId= facility.getFacilityId();
        this.facilityName= facility.getFacilityName();
        this.providerId= facility.getProviderId();
        this.iconUUID= facility.getIconUUID();
        this.sportId = facility.getSportId();
        this.longitude= facility.getLongitude();
        this.latitude= facility.getLatitude();
        this.score= facility.getScore();
        this.openTime= facility.getOpenTime();
        this.description= facility.getDescription();
    }

    public void setFromSport(Sport sport){
        this.sportName = sport.getSportName();
        this.sportIconUUID = sport.getSportIconUUID();
    }

    public void setFromProvider(Provider provider){
        this.providerName = provider.getProviderName();
    }
}
