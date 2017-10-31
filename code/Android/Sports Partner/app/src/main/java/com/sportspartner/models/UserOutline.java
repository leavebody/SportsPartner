package com.sportspartner.models;

/**
 * Created by xc on 10/26/17.
 */

public class UserOutline {
    private String userId;
    private String userName;
    private String iconUUID;
    private String iconPath;

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

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }
}
