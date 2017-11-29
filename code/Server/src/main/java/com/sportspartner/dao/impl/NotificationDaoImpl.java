package com.sportspartner.dao.impl;

import com.sportspartner.dao.NotificationDao;
import com.sportspartner.model.Activity;
import com.sportspartner.model.Notification;
import com.sportspartner.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotificationDaoImpl implements NotificationDao {

    @Override
    public List<Notification> getUnsentNotification(String receiverId) throws SQLException {
        Connection c = new ConnectionUtil().connectDB();
        List<Notification> notifications = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = c.prepareStatement("SELECT * FROM \"Notification\" WHERE \"receiverId\" = ? AND  \"notificationState\" = ?;");
            stmt.setString(1, receiverId);
            stmt.setInt(2, 0);
            rs = stmt.executeQuery();
            while (rs.next()) {
                receiverId = rs.getString("receiverId");
                String notificationId = rs.getString("notificationId");
                String notificationTitle = rs.getString("notificationTitle");
                String notificationDetail = rs.getString("notificationDetail");
                String notificationType = rs.getString("notificationType");
                String notificationSender = rs.getString("notificationSender");
                Timestamp timeStamp = rs.getTimestamp("time");
                Date time = new Date(timeStamp.getTime());
                int notificationState = rs.getInt("notificationState");
                int notificationPriority = rs.getInt("notificationPriority");
                Notification notification = new Notification(receiverId, notificationId, notificationTitle, notificationDetail, notificationType, notificationSender, time, notificationState, notificationPriority);
                notifications.add(notification);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            rs.close();
            stmt.close();
            c.close();
        }
        return notifications;
    }

    @Override
    public boolean setNotificationSent(String receiverId, String notificationId) throws SQLException {
        Connection c = new ConnectionUtil().connectDB();
        PreparedStatement stmt = null;
        int rs;
        boolean result = false;
        try {
            stmt = c.prepareStatement("UPDATE \"Notification\" SET \"notificationState\" = ? WHERE \"receiverId\"=? AND  \"notificationId\"=?;");
            stmt.setInt(1, 1);
            stmt.setString(2, receiverId);
            stmt.setString(3, notificationId);
            rs = stmt.executeUpdate();
            if (rs > 0)
                result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            stmt.close();
            c.close();
        }
        return result;
    }

    @Override
    public boolean newNotification(com.sportspartner.model.Notification notification) throws SQLException {
        Connection c = new ConnectionUtil().connectDB();
        PreparedStatement stmt = null;
        int rs;
        boolean result = false;
        String receiverId = notification.getReceiverId();
        String notificationId = notification.getNotificationId();
        String notificationTitle = notification.getNotificationTitle();
        String notificationDetail = notification.getNotificationDetail();
        String notificationType = notification.getNotificationType();
        String notificationSender = notification.getNotificationSender();
        Date time = notification.getTime();
        Timestamp timeStamp = new Timestamp(time.getTime());
        int notificationState = notification.getNotificationState();
        int notificationPriority = notification.getNotificationPriority();
        try {
            stmt = c.prepareStatement("INSERT INTO \"Notification\" (\"receiverId\", \"notificationId\",\"notificationTitle\", \"notificationDetail\", \"notificationType\", \"notificationSender\", \"time\" , \"notificationState\",\"notificationPriority\")" +
                    "VALUES (?, ?, ?, ?, ?,?, ?, ?, ?)");
            stmt.setString(1, receiverId);
            stmt.setString(2, notificationId);
            stmt.setString(3, notificationTitle);
            stmt.setString(4, notificationDetail);
            stmt.setString(5, notificationType);
            stmt.setString(6, notificationSender);
            stmt.setTimestamp(7, timeStamp);
            stmt.setInt(8, notificationState);
            stmt.setInt(9, notificationPriority);
            rs = stmt.executeUpdate();
            if (rs > 0)
                result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            stmt.close();
            c.close();
        }
        return result;
    }

    @Override
    public boolean deleteNotification(com.sportspartner.model.Notification notification) throws SQLException {
        Connection c = new ConnectionUtil().connectDB();

        PreparedStatement stmt = null;
        int rs;
        String receiverId = notification.getReceiverId();
        String notificationId = notification.getNotificationId();
        String notificationTitle = notification.getNotificationTitle();
        String notificationDetail = notification.getNotificationDetail();
        String notificationType = notification.getNotificationType();
        String notificationSender = notification.getNotificationSender();
        Date time = notification.getTime();
        Timestamp timeStamp = new Timestamp(time.getTime());
        int notificationState = notification.getNotificationState();
        int notificationPriority = notification.getNotificationPriority();
        boolean result = false;
        try {
            stmt = c.prepareStatement("DELETE FROM \"Notification\" WHERE \"receiverId\"=? AND \"notificationId\" = ? AND \"notificationTitle\" = ? AND \"notificationDetail\" = ? AND " +
                    " \"notificationType\" = ? AND \"notificationSender\" = ? AND \"time\"= ? AND \"notificationState\"= ? AND \"notificationPriority\"= ? ;");
            stmt.setString(1, receiverId);
            stmt.setString(2, notificationId);
            stmt.setString(3, notificationTitle);
            stmt.setString(4, notificationDetail);
            stmt.setString(5, notificationType);
            stmt.setString(6, notificationSender);
            stmt.setTimestamp(7, timeStamp);
            stmt.setInt(8, notificationState);
            stmt.setInt(9, notificationPriority);
            rs = stmt.executeUpdate();
            if (rs > 0) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            stmt.close();
            c.close();
        }
        return result;
    }
}
