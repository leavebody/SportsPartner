package com.sportspartner.model;

public class Interest {
    private String userId;
    private String sportId;

    public Interest() {
    }

    public Interest(String userId, String sportId){
        this.userId = userId;
        this.sportId= sportId;
    }
    public String getUserId() {
        return userId;
    }

    public String getSportId() {
        return sportId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setSportId(String sportId) {
        this.sportId = sportId;
    }
}
