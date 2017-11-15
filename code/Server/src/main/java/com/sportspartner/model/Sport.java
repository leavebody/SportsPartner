package com.sportspartner.model;

public class Sport {
    private String sportId;
    private String sportName;
    private String sportIconUUID;

    public Sport(String sportId, String sportName, String sportIconUUID) {
        this.sportId = sportId;
        this.sportName = sportName;
        this.sportIconUUID = sportIconUUID;
    }

    public Sport(){}

    public String getSportId() {
        return sportId;
    }

    public void setSportId(String sportId) {
        this.sportId = sportId;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public String getSportIconUUID() {
        return sportIconUUID;
    }

    public void setSportIconUUID(String sportIconUUID) {
        this.sportIconUUID = sportIconUUID;
    }
}

