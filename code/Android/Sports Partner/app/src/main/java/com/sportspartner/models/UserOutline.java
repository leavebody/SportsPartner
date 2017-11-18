package com.sportspartner.models;

import java.io.Serializable;

/**
 * @author Xiaochen Li
 */

public class UserOutline implements Serializable{
    private String userId;
    private String userName;
    private String iconUUID;

    public UserOutline() {
    }

    public UserOutline(String userId, String userName, String iconUUID) {
        this.userId = userId;
        this.userName = userName;
        this.iconUUID = iconUUID;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIconUUID() {
        return iconUUID;
    }

    public void setIconUUID(String iconUUID) {
        this.iconUUID = iconUUID;
    }
}
