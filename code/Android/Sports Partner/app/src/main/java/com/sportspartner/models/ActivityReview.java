package com.sportspartner.models;

import java.util.ArrayList;

/**
 * @author Xiaochen Li
 */

public class ActivityReview {
    private String userId;
    private ArrayList<UserReview> userreviews;
    private FacilityReview facilityreview;
    private String key;

    @Override
    public String toString() {
        return "ReviewActivity{" +
                "userId='" + userId + '\'' +
                ", userreviews=" + userreviews +
                ", facilityReview=" + facilityreview +
                ", key='" + key + '\'' +
                '}';
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {

        this.key = key;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ArrayList<UserReview> getUserreviews() {
        return userreviews;
    }

    public void setUserreviews(ArrayList<UserReview> userreviews) {
        this.userreviews = userreviews;
    }

    public FacilityReview getFacilityreview() {
        return facilityreview;
    }

    public void setFacilityreview(FacilityReview facilityreview) {
        this.facilityreview = facilityreview;
    }
}
