package com.sportspartner.util;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * @author Xiaochen Li
 */

public class PickPlaceResult implements Serializable {
    private double latitude;
    private double longitude;
    private boolean isFacility;
    private String name;
    private String zipCode;

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public PickPlaceResult() {
    }

    public boolean isFacility() {
        return isFacility;
    }

    public void setFacility(boolean facility) {
        isFacility = facility;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public LatLng getLatLng() {
        return new LatLng(latitude, longitude);
    }

    public void setLatLng(LatLng latLng) {
        latitude = latLng.latitude;
        longitude = latLng.longitude;
    }
}
