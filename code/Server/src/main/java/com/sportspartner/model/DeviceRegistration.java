package com.sportspartner.model;

public class DeviceRegistration {
    private String userId;
    private String registrationId;

    public DeviceRegistration() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public  DeviceRegistration (String userId, String registrationId)
    {
        this.userId = userId;
        this.registrationId = registrationId;


    }
}
