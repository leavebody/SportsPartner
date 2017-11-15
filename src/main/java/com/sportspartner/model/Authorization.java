package com.sportspartner.model;

public class Authorization {
    private String userId;
    private String key;

    public Authorization(String userId,String key){
        this.userId = userId;
        this.key = key;
    }

    public String getUserId() {
        return userId;
    }

    public String getKey() {
        return key;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
