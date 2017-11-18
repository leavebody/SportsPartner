package com.sportspartner.modelvo;

import com.sportspartner.model.Facility;

public class FacilityMarkerVO {
    private String facilityId;
    private String facilityName;
    private double longitude;
    private double latitude;

    public FacilityMarkerVO(){}


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

    public void setFromFacility(Facility facility){
        this.facilityId = facility.getFacilityId();
        this.facilityName = facility.getFacilityName();
        this.longitude = facility.getLongitude();
        this.latitude = facility.getLatitude();
    }
}
