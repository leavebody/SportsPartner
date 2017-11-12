package com.sportspartner.dao;

import com.sportspartner.model.Notification;

import java.util.List;
public interface NotificationDao {
    public List <Notification>getUnsentNotification(String receiverId);
    public boolean setNotificationSent(String receiverId, String notificationId);
    public boolean newNotification(Notification notification);
    public boolean deleteNotification (Notification notification);
}
