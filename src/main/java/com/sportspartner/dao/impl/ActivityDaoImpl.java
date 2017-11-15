package com.sportspartner.dao.impl;

import com.sportspartner.dao.ActivityDao;
import com.sportspartner.model.Activity;
import com.sportspartner.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActivityDaoImpl implements ActivityDao {
    /**
     * Get all activities in the databases.
     * @return list of Activity objects
     */
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
                double longitude = rs.getDouble("longitude");
                double latitude = rs.getDouble("latitude");
                String zipcode = rs.getString("zipcode");
                String address = rs.getString("address");
                Timestamp startTimeStamp = rs.getTimestamp("startTime");
                Date startTime = new Date(startTimeStamp.getTime());
                Timestamp endTimeStamp = rs.getTimestamp("endTime");
                Date endTime = new Date(endTimeStamp.getTime());
                int capacity = rs.getInt("capacity");
                int size = rs.getInt("size");
                String description = rs.getString("description");

                activities.add(new Activity(activityId, creatorId,facilityId, status,sportId, longitude, latitude, zipcode, address, startTime, endTime, capacity, size,description));
        }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            
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

    /**
     * Get an activity by activityId.
     * @param activityId
     * @return Activity object
     */
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
                double longitude = rs.getDouble("longitude");
                double latitude = rs.getDouble("latitude");
                String zipcode = rs.getString("zipcode");
                String address = rs.getString("address");
                Timestamp startTimeStamp = rs.getTimestamp("startTime");
                Date startTime = new Date(startTimeStamp.getTime());
                Timestamp endTimeStamp = rs.getTimestamp("endTime");
                Date endTime = new Date(endTimeStamp.getTime());
                int capacity = rs.getInt("capacity");
                int size = rs.getInt("size");
                String description = rs.getString("description");
                activity = new Activity(activityId, creatorId,facilityId, status,sportId, longitude, latitude, zipcode, address, startTime, endTime, capacity, size,description);
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
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

    /**
     * Create a new activity.
     * @param activity
     * @return "true" or "false" for whether successfully created a new activity.
     */
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
        double longitude = activity.getLongitude();
        double latitude = activity.getLatitude();
        String zipcode = activity.getZipcode();
        String address = activity.getAddress();
        Date startTime = activity.getStartTime();
        Timestamp startTimeStamp = new Timestamp(startTime.getTime());
        Date endTime = activity.getEndTime();
        Timestamp endTimeStamp = new Timestamp(endTime.getTime());
        int capacity = activity.getCapacity();
        int size = activity.getSize();
        String description = activity.getDescription();
        boolean result = false;

        try {
            stmt = c.prepareStatement("INSERT INTO \"Activity\" (\"activityId\", \"creatorId\",\"facilityId\", \"status\", \"sportId\", \"longitude\", \"latitude\", \"zipcode\", \"address\", \"startTime\", \"endTime\" , \"capacity\",\"size\",\"description\")"+
                    "VALUES (?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?)");
            stmt.setString(1, activityId);
            stmt.setString(2, creatorId);
            stmt.setString(3, facilityId);
            stmt.setString(4, status);
            stmt.setString(5, sportId);
            stmt.setDouble(6, longitude);
            stmt.setDouble(7, latitude);
            stmt.setString(8, zipcode);
            stmt.setString(9, address);
            stmt.setTimestamp(10,startTimeStamp);
            stmt.setTimestamp(11,endTimeStamp);
            stmt.setInt(12,capacity);
            stmt.setInt(13,size);
            stmt.setString(14,description);

            rs = stmt.executeUpdate();
            if(rs>0)
                result = true;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            
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

    /**
     * Update an activity.
     * @param activity
     * @return "true" or "false" for whether successfully updated an activity.
     */
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
        double longitude = activity.getLongitude();
        double latitude = activity.getLatitude();
        String zipcode = activity.getZipcode();
        String address = activity.getAddress();
        Date startTime = activity.getStartTime();
        Timestamp startTimeStamp = new Timestamp(startTime.getTime());
        Date endTime = activity.getEndTime();
        Timestamp endTimeStamp = new Timestamp(endTime.getTime());
        int capacity = activity.getCapacity();
        int size = activity.getSize();
        String description = activity.getDescription();

        boolean result = false;
        try {
            stmt = c.prepareStatement("UPDATE \"Activity\" SET \"creatorId\" = ? , \"facilityId\" = ? , \"status\" = ?,\"sportId\" = ? , \"longitude\" = ? , \"latitude\" = ?, " +
                    " \"zipcode\", \"address\", \"startTime\" = ?,\"endTime\" = ? , \"capacity\" = ? , \"size\" = ?, \"description\" = ?WHERE \"activityId\"=? ;");
            stmt.setString(1, creatorId);
            stmt.setString(2, facilityId);
            stmt.setString(3, status);
            stmt.setString(4, sportId);
            stmt.setDouble(5,longitude);
            stmt.setDouble(6,latitude);
            stmt.setString(7, zipcode);
            stmt.setString(8, address);
            stmt.setTimestamp(9,startTimeStamp);
            stmt.setTimestamp(10,endTimeStamp);
            stmt.setInt(11,capacity);
            stmt.setInt(12,size);
            stmt.setString(13,description);
            stmt.setString(14, activityId);
            rs = stmt.executeUpdate();
            if(rs>0)
                result = true;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            
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

    /**
     * Delete an activity from database by activityId.
     * @param activityId The UUID for the activity.
     * @return "true" or "false" for whether successfully delete the activity.
     */
    @Override
    public boolean deleteActivity(String activityId) {
        Connection c = new ConnectionUtil().connectDB();

        PreparedStatement stmt = null;
        int rs;
        boolean result = false;

        try {
            stmt = c.prepareStatement("DELETE FROM \"Activity\" WHERE \"activityId\"=? ");
            stmt.setString(1, activityId);

            rs = stmt.executeUpdate();
            if(rs>0){
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            
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

    /**
     * Get a user's upcoming activities whose startTime is later than current time.
     * @param userId
     * @return a list of Activity objects
     */
    @Override
     public List<Activity> getUpcomingActivities(String userId){
         Connection c = new ConnectionUtil().connectDB();
         PreparedStatement stmt = null;
         ResultSet rs = null;
         List<Activity> activities = new ArrayList<Activity>();
         try {
             stmt = c.prepareStatement("SELECT * FROM \"Activity\", \"Activity_Member\" WHERE \"Activity\".\"activityId\"=\"Activity_Member\".\"activityId\" " +
                     "AND \"Activity_Member\".\"userId\"=? AND \"startTime\" > CURRENT_TIMESTAMP ORDER BY \"startTime\" ASC;");
             stmt.setString(1, userId);
             rs = stmt.executeQuery();
             while (rs.next()) {
                 String activityId = rs.getString("activityId");
                 String creatorId = rs.getString("creatorId");
                 String facilityId = rs.getString("facilityId");
                 String status = rs.getString("status");
                 String sportId = rs.getString("sportId");
                 double longitude = rs.getDouble("longitude");
                 double latitude = rs.getDouble("latitude");
                 String zipcode = rs.getString("zipcode");
                 String address = rs.getString("address");
                 Timestamp startTimeStamp = rs.getTimestamp("startTime");
                 Date startTime = new Date(startTimeStamp.getTime());
                 Timestamp endTimeStamp = rs.getTimestamp("endTime");
                 Date endTime = new Date(endTimeStamp.getTime());
                 int capacity = rs.getInt("capacity");
                 int size = rs.getInt("size");
                 String description = rs.getString("description");

                 activities.add(new Activity(activityId, creatorId,facilityId, status,sportId, longitude, latitude, zipcode, address, startTime, endTime, capacity, size,description));
             }
         } catch (Exception e) {
             System.err.println(e.getClass().getName() + ": " + e.getMessage());
             
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

    /**
     * Get a user's upcoming activities whose endTime is earlier than current time.
     * @param userId
     * @return a list of Activity objects
     */
     @Override
     public List<Activity> getPastActivities(String userId){
         Connection c = new ConnectionUtil().connectDB();
         PreparedStatement stmt = null;
         ResultSet rs = null;
         List<Activity> activities = new ArrayList<Activity>();
         try {
             stmt = c.prepareStatement("SELECT * FROM \"Activity\", \"Activity_Member\" WHERE \"Activity_Member\".\"userId\"=? AND \"Activity\".\"activityId\"=\"Activity_Member\".\"activityId\" " +
                     "AND  \"endTime\" < CURRENT_TIMESTAMP ORDER BY \"startTime\" DESC;");
             stmt.setString(1, userId);
             rs = stmt.executeQuery();
             while (rs.next()) {
                 String activityId = rs.getString("activityId");
                 String creatorId = rs.getString("creatorId");
                 String facilityId = rs.getString("facilityId");
                 String status = rs.getString("status");
                 String sportId = rs.getString("sportId");
                 double longitude = rs.getDouble("longitude");
                 double latitude = rs.getDouble("latitude");
                 String zipcode = rs.getString("zipcode");
                 String address = rs.getString("address");
                 Timestamp startTimeStamp = rs.getTimestamp("startTime");
                 Date startTime = new Date(startTimeStamp.getTime());
                 Timestamp endTimeStamp = rs.getTimestamp("endTime");
                 Date endTime = new Date(endTimeStamp.getTime());
                 int capacity = rs.getInt("capacity");
                 int size = rs.getInt("size");
                 String description = rs.getString("description");

                 activities.add(new Activity(activityId, creatorId,facilityId, status,sportId, longitude, latitude, zipcode, address, startTime, endTime, capacity, size,description));
             }
         } catch (Exception e) {
             System.err.println(e.getClass().getName() + ": " + e.getMessage());
             
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

     public List<Activity> getRecommendActivities(String userId){
         Connection c = new ConnectionUtil().connectDB();
         PreparedStatement stmt = null;
         ResultSet rs = null;
         List<Activity> activities = new ArrayList<Activity>();

         try {
             stmt = c.prepareStatement("SELECT * FROM \"Activity\", \"Activity_Member\" WHERE \"Activity_Member\".\"userId\"=? AND \"Activity\".\"activityId\"=\"Activity_Member\".\"activityId\" " +
                     "AND  \"endTime\" < CURRENT_TIMESTAMP ORDER BY \"startTime\" DESC;");
             stmt.setString(1, userId);
             rs = stmt.executeQuery();
             while (rs.next()) {
                 String activityId = rs.getString("activityId");
                 String creatorId = rs.getString("creatorId");
                 String facilityId = rs.getString("facilityId");
                 String status = rs.getString("status");
                 String sportId = rs.getString("sportId");
                 double longitude = rs.getDouble("longitude");
                 double latitude = rs.getDouble("latitude");
                 String zipcode = rs.getString("zipcode");
                 String address = rs.getString("address");
                 Timestamp startTimeStamp = rs.getTimestamp("startTime");
                 Date startTime = new Date(startTimeStamp.getTime());
                 Timestamp endTimeStamp = rs.getTimestamp("endTime");
                 Date endTime = new Date(endTimeStamp.getTime());
                 int capacity = rs.getInt("capacity");
                 int size = rs.getInt("size");
                 String description = rs.getString("description");

                 activities.add(new Activity(activityId, creatorId,facilityId, status,sportId, longitude, latitude, zipcode, address, startTime, endTime, capacity, size,description));
             }
         } catch (Exception e) {
             System.err.println(e.getClass().getName() + ": " + e.getMessage());

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
