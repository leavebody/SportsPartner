package com.sportspartner.models;

/**
 * @author Xiaochen Li
 */

public class FacilityMarker {
    private double latitude;
    private double longtitude;
    private String name;
    private String id;

    public FacilityMarker(double latitude, double longtitude, String name, String id) {
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.name = name;
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
