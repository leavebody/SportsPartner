package com.sportspartner.dao;

import com.sportspartner.model.Notification;

import java.sql.SQLException;
import java.util.List;
public interface NotificationDao {
    public List <Notification>getUnsentNotification(String receiverId) throws SQLException;
    public boolean setNotificationSent(String receiverId, String notificationId) throws SQLException;
    public boolean newNotification(Notification notification) throws SQLException;
    public boolean deleteNotification (Notification notification) throws SQLException;
}
