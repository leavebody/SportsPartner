package com.sportspartner.Models;

public class Facility{
    private String facilityId;
    private String sportsName;

    public Facility(){}

    public Facility(String facilityId, String sportsName) {
        this.facilityId = facilityId;
        this.sportsName = sportsName;
    }

    public String getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
    }

    public String getSportsName() {
        return sportsName;
    }

    public void setSportsName(String sportsName) {
        this.sportsName = sportsName;
    }
}
