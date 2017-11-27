package com.sportspartner.model;

import java.util.Date;

public class Notification {
    private String receiverId;
    private String notificationId;
    private String notificationTitle;
    private String notificationDetail;
    private String notificationType;
    private String notificationSender;
    private Date time;
    private int notificationState;
    private int notificationPriority;


    public Notification(String receiverId, String notificationId, String notificationTitle, String notificationDetail, String notificationType, String notificationSender, Date time, int notificationState, int notificationPriority) {
        this.receiverId = receiverId;
        this.notificationId = notificationId;
        this.notificationTitle = notificationTitle;
        this.notificationDetail = notificationDetail;
        this.notificationType = notificationType;
        this.notificationSender = notificationSender;
        this.time = time;
        this.notificationState = notificationState;
        this.notificationPriority = notificationPriority;
    }

    public String getReceiverId() { return receiverId; }

    public void setReceiverId(String userId) {
        this.receiverId = userId;
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

    public int getNotificationState() { return notificationState; }

    public void setNotificationState(int notificationState) { this.notificationState = notificationState; }

    public int getNotificationPriority() {
        return notificationPriority;
    }

    public void setNotificationPriority(int notificationPriority) {
        this.notificationPriority = notificationPriority;
    }

    public String getNotificationType() { return notificationType; }

    public void setNotificationType(String notificationType) { this.notificationType = notificationType; }
    public String getNotificationSender() { return notificationSender; }

    public void setNotificationSender(String notificationSender) { this.notificationSender = notificationSender; }

    public Date getTime() { return time; }

    public void setTime(Date time) { this.time = time; }
}
