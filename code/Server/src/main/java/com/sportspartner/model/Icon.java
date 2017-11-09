package com.sportspartner.model;


public class Icon {
    private String spId;
    private String iconUUID;
    private String smallPath; // The path for the small version of the icon
    private String originPath; // The path for the original image
    private String object; // "USER" or "SPORT"

    public Icon(){}

    public Icon(String spId,String object){
        this.spId = spId;
        this.object = object;
    }
    public Icon(String spId, String iconUUID, String smallPath, String originPath, String object) {
        this.spId = spId;
        this.iconUUID = iconUUID;
        this.smallPath = smallPath;
        this.originPath = originPath;
        this.object = object;
    }

    public String getSpId() {
        return spId;
    }

    public void setSpId(String spId) {
        this.spId = spId;
    }

    public String getIconUUID() {
        return iconUUID;
    }

    public void setIconUUID(String iconUUID) {
        this.iconUUID = iconUUID;
    }

    public String getSmallPath() {
        return smallPath;
    }

    public void setSmallPath(String smallPath) {
        this.smallPath = smallPath;
    }

    public String getOriginPath() {
        return originPath;
    }

    public void setOriginPath(String originPath) {
        this.originPath = originPath;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }
}
