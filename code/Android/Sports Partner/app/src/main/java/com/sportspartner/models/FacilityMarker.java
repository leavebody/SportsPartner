package com.sportspartner.models;

import com.google.android.gms.maps.model.LatLng;

/**
 * @author Xiaochen Li
 */

public class FacilityMarker {
    private double latitude;
    private double longitude;
    private String facilityName;
    private String facilityId;

    public FacilityMarker(double latitude, double longitude, String name, String id) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.facilityName = name;
        this.facilityId = id;
    }
    public FacilityMarker(LatLng latLng, String name, String id) {
        setLatLng(latLng);
        this.facilityName = name;
        this.facilityId = id;
    }
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public String getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
    }

    public LatLng getLatLng() {
        return new LatLng(latitude, longitude);
    }

    public void setLatLng(LatLng latLng) {
        latitude = latLng.latitude;
        longitude = latLng.longitude;
    }
}
