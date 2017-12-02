package com.sportspartner.modelvo;

import java.util.ArrayList;

public class ReviewActivityVO {
    private String userId;
    private ArrayList<UserReviewVO> userreviews;
    private FacilityReviewVO facilityreview;
    private String key;

    @Override
    public String toString() {
        return "ReviewActivityVO{" +
                "userId='" + userId + '\'' +
                ", userReviewVOs=" + userreviews +
                ", facilityReviewVO=" + facilityreview +
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

    public ArrayList<UserReviewVO> getUserReviewVOs() {
        return userreviews;
    }

    public void setUserReviewVOs(ArrayList<UserReviewVO> userReviewVOs) {
        this.userreviews = userReviewVOs;
    }

    public FacilityReviewVO getFacilityReviewVO() {
        return facilityreview;
    }

    public void setFacilityReviewVO(FacilityReviewVO facilityReviewVO) {
        this.facilityreview = facilityReviewVO;
    }
}
