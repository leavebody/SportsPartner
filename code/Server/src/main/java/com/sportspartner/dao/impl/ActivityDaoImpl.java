package com.sportspartner.dao.impl;

import com.sportspartner.dao.ActivityDao;
import com.sportspartner.model.Activity;
import com.sportspartner.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActivityDaoImpl implements ActivityDao {
    @Override
    public List<Activity> getAllActivities() {
        Connection c = new ConnectionUtil().connectDB();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Activity> activities = new ArrayList<Activity>();
        try {
            stmt = c.prepareStatement("SELECT * FROM \"Activity\";");
            rs = stmt.executeQuery();
            while (rs.next()) {
                String activityId = rs.getString("activityId");
                String creatorId = rs.getString("creatorId");
                String facilityId = rs.getString("facilityId");
                String status = rs.getString("status");
                String sportId = rs.getString("sportId");
                Timestamp startTimeStamp = rs.getTimestamp("startTime");
                Date startTime = new Date(startTimeStamp.getTime());
                Timestamp endTimeStamp = rs.getTimestamp("endTime");
                Date endTime = new Date(endTimeStamp.getTime());
                int capacity = rs.getInt("capacity");
                int size = rs.getInt("size");
                String description = rs.getString("description");

                activities.add(new Activity(activityId, creatorId,facilityId, status,sportId, startTime, endTime, capacity, size,description));
        }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        } finally {
            try {
                rs.close();
                stmt.close();
                c.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return activities;
    }

    @Override
    public Activity getActivity(String activityId) {
        Connection c = new ConnectionUtil().connectDB();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Activity activity = null;
        try {
            stmt = c.prepareStatement("SELECT * FROM \"Activity\" WHERE \"activityId\" = ?;");
            stmt.setString(1, activityId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                activityId = rs.getString("activityId");
                String creatorId = rs.getString("creatorId");
                String facilityId = rs.getString("facilityId");
                String status = rs.getString("status");
                String sportId = rs.getString("sportId");
                Timestamp startTimeStamp = rs.getTimestamp("startTime");
                Date startTime = new Date(startTimeStamp.getTime());
                Timestamp endTimeStamp = rs.getTimestamp("endTime");
                Date endTime = new Date(endTimeStamp.getTime());
                int capacity = rs.getInt("capacity");
                int size = rs.getInt("size");
                String description = rs.getString("description");
                activity = new Activity(activityId, creatorId,facilityId, status,sportId, startTime, endTime, capacity, size,description);
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        } finally {
            try {
                rs.close();
                stmt.close();
                c.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return activity;
    }

    @Override
    public boolean newActivity(Activity activity) {
        Connection c = new ConnectionUtil().connectDB();

        PreparedStatement stmt = null;
        int rs;
        String activityId = activity.getActivityId();
        String creatorId = activity.getCreatorId();
        String facilityId = activity.getFacilityId();
        String status = activity.getStatus();
        String sportId = activity.getSportId();
        Date startTime = activity.getStartTime();
        Timestamp startTimeStamp = new Timestamp(startTime.getTime());
        Date endTime = activity.getEndTime();
        Timestamp endTimeStamp = new Timestamp(endTime.getTime());
        int capacity = activity.getCapacity();
        int size = activity.getSize();
        String description = activity.getDescription();
        boolean result = false;

        try {
            stmt = c.prepareStatement("INSERT INTO \"Activity\" (\"activityId\", \"creatorId\",\"facilityId\", \"status\", \"sportId\", \"startTime\", \"endTime\" , \"capacity\",\"size\",\"description\")"+
                    "VALUES (?, ?, ?, ?, ?,?, ?, ?, ?, ?)");
            stmt.setString(1, activityId);
            stmt.setString(2, creatorId);
            stmt.setString(3, facilityId);
            stmt.setString(4, status);
            stmt.setString(5, sportId);
            stmt.setTimestamp(6,startTimeStamp);
            stmt.setTimestamp(7,endTimeStamp);
            stmt.setInt(8,capacity);
            stmt.setInt(9,size);
            stmt.setString(10,description);

            rs = stmt.executeUpdate();
            if(rs>0)
                result = true;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        } finally {
            try {
                stmt.close();
                c.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return result;

    }

    @Override
    public boolean updateActivity(Activity activity) {
        Connection c = new ConnectionUtil().connectDB();

        PreparedStatement stmt = null;
        int rs;
        String activityId = activity.getActivityId();
        String creatorId = activity.getCreatorId();
        String facilityId = activity.getFacilityId();
        String status = activity.getStatus();
        String sportId = activity.getSportId();
        Date startTime = activity.getStartTime();
        Timestamp startTimeStamp = new Timestamp(startTime.getTime());
        Date endTime = activity.getEndTime();
        Timestamp endTimeStamp = new Timestamp(endTime.getTime());
        int capacity = activity.getCapacity();
        int size = activity.getSize();
        String description = activity.getDescription();

        boolean result = false;
        try {
            stmt = c.prepareStatement("UPDATE \"Activity\" SET \"creatorId\" = ? , \"facilityId\" = ? , \"status\" = ?,\"sportId\" = ? ," +
                    " \"startTime\" = ?,\"endTime\" = ? , \"capacity\" = ? , \"size\" = ?, \"description\" = ?WHERE \"activityId\"=? ;");
            stmt.setString(1, creatorId);
            stmt.setString(2, facilityId);
            stmt.setString(3, status);
            stmt.setString(4, sportId);
            stmt.setTimestamp(5,startTimeStamp);
            stmt.setTimestamp(6,endTimeStamp);
            stmt.setInt(7,capacity);
            stmt.setInt(8,size);
            stmt.setString(9,description);
            stmt.setString(10, activityId);
            rs = stmt.executeUpdate();
            if(rs>0)
                result = true;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        } finally {
            try {
                stmt.close();
                c.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public boolean deleteActivity(Activity activity) {
        Connection c = new ConnectionUtil().connectDB();

        PreparedStatement stmt = null;
        int rs;
        String activityId = activity.getActivityId();
        String creatorId = activity.getCreatorId();
        String facilityId = activity.getFacilityId();
        String status = activity.getStatus();
        String sportId = activity.getSportId();
        Date startTime = activity.getStartTime();
        Timestamp startTimeStamp = new Timestamp(startTime.getTime());
        Date endTime = activity.getEndTime();
        Timestamp endTimeStamp = new Timestamp(endTime.getTime());
        int capacity = activity.getCapacity();
        int size = activity.getSize();
        String description = activity.getDescription();
        boolean result = false;

        try {
            stmt = c.prepareStatement("DELETE FROM \"Activity\" WHERE \"activityId\"=? AND \"creatorId\" = ? AND \"facilityId\" = ? AND \"status\" = ? AND " +
                    " \"sportId\" = ? AND \"startTime\" = ? AND \"endTime\"= ? AND \"capacity\"= ? AND \"size\"= ? AND \"description\"= ?;");
            stmt.setString(1, activityId);
            stmt.setString(2, creatorId);
            stmt.setString(3, facilityId);
            stmt.setString(4, status);
            stmt.setString(5, sportId);
            stmt.setTimestamp(6,startTimeStamp);
            stmt.setTimestamp(7,endTimeStamp);
            stmt.setInt(8,capacity);
            stmt.setInt(9,size);
            stmt.setString(10,description);
            rs = stmt.executeUpdate();
            if(rs>0){
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        } finally {
            try {
                stmt.close();
                c.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return result;
    }

     public List<Activity> getUpcomingActivities(String userId){
         Connection c = new ConnectionUtil().connectDB();
         PreparedStatement stmt = null;
         ResultSet rs = null;
         List<Activity> activities = new ArrayList<Activity>();
         try {
             stmt = c.prepareStatement("SELECT * FROM \"Activity\", \"Activity_Member\" WHERE \"Activity\".\"activityId\"=\"Activity_Member\".\"activityId\" " +
                     "AND \"Activity_Member\".\"userId\"=? AND \"startTime\" > CURRENT_TIMESTAMP;");
             stmt.setString(1, userId);
             rs = stmt.executeQuery();
             while (rs.next()) {
                 String activityId = rs.getString("activityId");
                 String creatorId = rs.getString("creatorId");
                 String facilityId = rs.getString("facilityId");
                 String status = rs.getString("status");
                 String sportId = rs.getString("sportId");
                 Timestamp startTimeStamp = rs.getTimestamp("startTime");
                 Date startTime = new Date(startTimeStamp.getTime());
                 Timestamp endTimeStamp = rs.getTimestamp("endTime");
                 Date endTime = new Date(endTimeStamp.getTime());
                 int capacity = rs.getInt("capacity");
                 int size = rs.getInt("size");
                 String description = rs.getString("description");

                 activities.add(new Activity(activityId, creatorId,facilityId, status,sportId, startTime, endTime, capacity, size,description));
             }
         } catch (Exception e) {
             System.err.println(e.getClass().getName() + ": " + e.getMessage());
             System.exit(0);
         } finally {
             try {
                 rs.close();
                 stmt.close();
                 c.close();
             } catch (SQLException e) {
                 // TODO Auto-generated catch block
                 e.printStackTrace();
             }
         }
         return activities;
     }

     public List<Activity> getPastActivities(String userId){
         Connection c = new ConnectionUtil().connectDB();
         PreparedStatement stmt = null;
         ResultSet rs = null;
         List<Activity> activities = new ArrayList<Activity>();
         try {
             stmt = c.prepareStatement("SELECT * FROM \"Activity\", \"Activity_Member\" WHERE \"Activity_Member\".\"userId\"=? AND \"Activity\".\"activityId\"=\"Activity_Member\".\"activityId\" " +
                     "AND  \"startTime\" < CURRENT_TIMESTAMP;");
             stmt.setString(1, userId);
             rs = stmt.executeQuery();
             while (rs.next()) {
                 String activityId = rs.getString("activityId");
                 String creatorId = rs.getString("creatorId");
                 String facilityId = rs.getString("facilityId");
                 String status = rs.getString("status");
                 String sportId = rs.getString("sportId");
                 Timestamp startTimeStamp = rs.getTimestamp("startTime");
                 Date startTime = new Date(startTimeStamp.getTime());
                 Timestamp endTimeStamp = rs.getTimestamp("endTime");
                 Date endTime = new Date(endTimeStamp.getTime());
                 int capacity = rs.getInt("capacity");
                 int size = rs.getInt("size");
                 String description = rs.getString("description");

                 activities.add(new Activity(activityId, creatorId,facilityId, status,sportId, startTime, endTime, capacity, size,description));
             }
         } catch (Exception e) {
             System.err.println(e.getClass().getName() + ": " + e.getMessage());
             System.exit(0);
         } finally {
             try {
                 rs.close();
                 stmt.close();
                 c.close();
             } catch (SQLException e) {
                 // TODO Auto-generated catch block
                 e.printStackTrace();
             }
         }
         return activities;
     }
}
