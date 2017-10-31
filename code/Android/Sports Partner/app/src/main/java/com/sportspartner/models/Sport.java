package com.sportspartner.models;

/**
 * Created by xc on 10/28/17.
 */

public class Sport {
    private String sportId;
    private String sportName;
    private String sportIconPath;
    private String sportIconUUID;

    public Sport(String sportId, String sportName, String sportIconPath, String sportIconUUID) {
        this.sportId = sportId;
        this.sportName = sportName;
        this.sportIconPath = sportIconPath;
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

    public String getSportIconPath() {
        return sportIconPath;
    }

    public void setSportIconPath(String sportIconPath) {
        this.sportIconPath = sportIconPath;
    }

    public String getSportIconUUID() {
        return sportIconUUID;
    }

    public void setSportIconUUID(String sportIconUUID) {
        this.sportIconUUID = sportIconUUID;
    }
}

