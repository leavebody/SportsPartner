package com.sportspartner.util;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * @author Xiaochen Li
 */

public class PickPlaceResult implements Serializable {
    private double latitude;
    private double longtitude;

    public PickPlaceResult(double latitude, double longtitude) {
        this.latitude = latitude;
        this.longtitude = longtitude;

    }

    public PickPlaceResult() {
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public LatLng getLatLng() {
        return new LatLng(latitude, longtitude);
    }

    public void setLatLng(LatLng latLng) {
        latitude = latLng.latitude;
        longtitude = latLng.longitude;
    }
}
