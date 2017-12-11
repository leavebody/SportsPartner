package com.sportspartner.model;

public class ActivityMember {
    private String activityId;
    private String userId;

    public ActivityMember() {
    }

    public ActivityMember(String activityId, String userId){
        this.activityId = activityId;
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    @Override
    public String toString() {
        return "ActivityMember{" +
                "activityId='" + activityId + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
