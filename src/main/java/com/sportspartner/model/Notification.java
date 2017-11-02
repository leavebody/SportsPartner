package com.sportspartner.model;

public class Notification {
    private String userId;
    private String notificationId;
    private String notificationTitle;
    private String notificationDetail;
    private String notificationStates;
    private int notificationPriority;

    public Notification(){}

    public Notification(String userId, String notificationId, String notificationTitle, String notificationDetail, String notificationStates, int notificationPriority) {
        this.userId = userId;
        this.notificationId = notificationId;
        this.notificationTitle = notificationTitle;
        this.notificationDetail = notificationDetail;
        this.notificationStates = notificationStates;
        this.notificationPriority = notificationPriority;
    }

    public String getUserId() { return userId; }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }

    public String getNotificationDetail() {
        return notificationDetail;
    }

    public void setNotificationDetail(String notificationDetail) {
        this.notificationDetail = notificationDetail;
    }

    public String getNotificationStates() {
        return notificationStates;
    }

    public void setNotificationStates(String notificationStates) {
        this.notificationStates = notificationStates;
    }

    public int getNotificationPriority() {
        return notificationPriority;
    }

    public void setNotificationPriority(int notificationPriority) {
        this.notificationPriority = notificationPriority;
    }
}
