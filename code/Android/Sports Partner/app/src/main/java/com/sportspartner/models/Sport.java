package com.sportspartner.models;

import java.io.Serializable;

/**
 * @author Xiaochen Li
 */

public class Sport implements Serializable {
    private String sportId;
    private String sportName;
    private String sportIconUUID;
    private Boolean isSelected = false;

    public Sport() {
    }

    public Sport(String sportId, String sportName, String sportIconUUID) {
        this.sportId = sportId;
        this.sportName = sportName;
        this.sportIconUUID = sportIconUUID;
    }

    public Sport(String sportId, String sportName, String sportIconUUID, Boolean isSelected) {
        this.sportId = sportId;
        this.sportName = sportName;
        this.sportIconUUID = sportIconUUID;
        this.isSelected = isSelected;
    }

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

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }
}

