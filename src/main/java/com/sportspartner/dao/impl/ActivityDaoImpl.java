package com.sportspartner.dao.impl;

import com.sportspartner.dao.ActivityDao;
import com.sportspartner.model.Activity;
import com.sportspartner.modelvo.ActivitySearchVO;
import com.sportspartner.util.ConnectionUtil;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class ActivityDaoImpl implements ActivityDao {
    /**
     * Get all activities in the databases.
     * @return list of Activity objects
     */
    @Override
    public List<Activity> getAllActivities() throws SQLException{
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
        } catch (SQLException e) {
            e.printStackTrace();
            
        } finally {

                rs.close();
                stmt.close();
                c.close();

        }
        return activities;
    }

    /**
     * Get an activity by activityId.
     * @param activityId
     * @return Activity object
     */
    @Override
    public Activity getActivity(String activityId)  throws SQLException{
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
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

                rs.close();
                stmt.close();
                c.close();

        }
        return activity;
    }

    /**
     * Create a new activity.
     * @param activity
     * @return "true" or "false" for whether successfully created a new activity.
     */
    @Override
    public boolean newActivity(Activity activity) throws SQLException {
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
        } catch (SQLException e) {
            e.printStackTrace();
            
        } finally {

                stmt.close();
                c.close();

        }
        return result;

    }

    /**
     * Update an activity.
     * @param activity
     * @return "true" or "false" for whether successfully updated an activity.
     */
    @Override
    public boolean updateActivity(Activity activity) throws SQLException {
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
                    " \"zipcode\"=?, \"address\"=?, \"startTime\" = ?,\"endTime\" = ? , \"capacity\" = ? , \"size\" = ?, \"description\" = ? WHERE \"activityId\"=? ;");
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
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

                stmt.close();
                c.close();

        }
        return result;
    }

    /**
     * Delete an activity from database by activityId.
     * @param activityId The UUID for the activity.
     * @param activityId The UUID for the activity.
     * @return "true" or "false" for whether successfully delete the activity.
     */
    @Override
    public boolean deleteActivity(String activityId) throws SQLException {
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
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

                stmt.close();
                c.close();

        }
        return result;
    }

    /**
     * Get a user's upcoming activities whose startTime is later than current time.
     * @param userId
     * @return a list of Activity objects
     */
    @Override
     public List<Activity> getUpcomingActivities(String userId) throws SQLException{
         Connection c = new ConnectionUtil().connectDB();
         PreparedStatement stmt = null;
         ResultSet rs = null;
         List<Activity> activities = new ArrayList<Activity>();
         try {
             stmt = c.prepareStatement("SELECT * FROM \"Activity\", \"Activity_Member\" WHERE \"Activity\".\"activityId\"=\"Activity_Member\".\"activityId\" " +
                     "AND \"Activity_Member\".\"userId\"=? AND \"endTime\" > CURRENT_TIMESTAMP ORDER BY \"startTime\" ASC;");
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
         } catch (SQLException e) {
             e.printStackTrace();
         } finally {

                 rs.close();
                 stmt.close();
                 c.close();

         }
         return activities;
     }

    /**
     * Get a user's upcoming activities whose endTime is earlier than current time.
     * @param userId The UUID for the user.
     * @return a list of Activity objects
     */
     @Override
     public List<Activity> getPastActivities(String userId) throws SQLException{
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
         } catch (SQLException e) {
             e.printStackTrace();
         } finally {

                 rs.close();
                 stmt.close();
                 c.close();

         }
         return activities;
     }

    /**
     *
     * @param userId
     * @return
     * @throws SQLException
     */
     public List<Activity> getRecommendActivities(String userId, double longitudeUser, double latitudeUser, int limit, int offset) throws SQLException{
         Connection c = new ConnectionUtil().connectDB();
         PreparedStatement stmt = null;
         ResultSet rs = null;
         List<Activity> activities = new ArrayList<Activity>();

         try {
             String queryString = this.getRecommendQueryString( userId,  longitudeUser,  latitudeUser,  limit,  offset);
             //System.out.println(queryString);
             stmt = c.prepareStatement(queryString);
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
         } catch (SQLException e) {
             e.printStackTrace();
         } finally {

                 rs.close();
                 stmt.close();
                 c.close();

         }
         return activities;
     }

    private String getRecommendQueryString(String userId, double longitude, double latitude, int limit, int offset){
         return String.format("\n" +
                 "select Activity.*, %f/(%f+Distance.distance) as disScore, /*fall off distance 01*/\n" +
                 "    (1-power(0.5,Friend_Count.friendcount)) as friScore, \n" +
                 "    Interest.interest_match as intScore,\n" +
                 "    %f*%f/(%f+Distance.distance)+%f*(1-power(0.5,Friend_Count.friendcount))+%f*Interest.interest_match as score \n" +
                 "    /*fall off distance, weight of three 23456*/\n" +
                 "from (select * from \"Activity\" as Act\n" +
                 "    where Act.\"status\"='OPEN' and Act.\"startTime\" > CURRENT_TIMESTAMP \n" +
                 "    and not exists(\n" +
                 "    select * from \"Activity_Member\" as Mem\n" +
                 "    where Mem.\"userId\"='%s' /*user id 7*/\n" +
                 "    and Mem.\"activityId\"=Act.\"activityId\")) as Activity\n" +
                 "\n" +
                 "join (\n" +
                 "    select \"activityId\", case when B.distance is null then %f else B.distance end as distance/*default distance 8*/\n" +
                 "    from\n" +
                 "    (select \n" +
                 "      \"activityId\", (point(longitude,latitude)  <@>\n" +
                 "      point(%f,%f))* 1.609344 as distance /*point latlng 9 10*/\n" +
                 "    from \"Activity\") as B\n" +
                 ") as Distance\n" +
                 "on Activity.\"activityId\"=Distance.\"activityId\" \n" +
                 "\n" +
                 "join (\n" +
                 "    select distinct A.\"activityId\",  case when B.friendcount is null then 0 else B.friendcount end as friendcount \n" +
                 "\tfrom \"Activity_Member\" as A left outer join (\n" +
                 "\t\tselect \"Activity_Member\".\"activityId\", count(\"Activity_Member\".\"activityId\") as friendcount \n" +
                 "        from \"Activity_Member\" join \"Friend\" on \n" +
                 "  \t\t\t((\"Activity_Member\".\"userId\"=\"Friend\".\"userId\" and \"Friend\".\"friendId\"='%s') /*user id 11*/\n" +
                 "   \t\t\tor(\"Activity_Member\".\"userId\"=\"Friend\".\"friendId\" and \"Friend\".\"userId\"='%s')) /*user id 12*/\n" +
                 "    \tgroup by \"Activity_Member\".\"activityId\"\n" +
                 "    ) as B ON A.\"activityId\" = B.\"activityId\"\n" +
                 ") as Friend_Count\n" +
                 "on Activity.\"activityId\"=Friend_Count.\"activityId\"\n" +
                 "\n" +
                 "join (\n" +
                 "    select A.\"activityId\", case when B.interest_match is null then 0 else (1) end as interest_match \n" +
                 "    from \"Activity\" A left outer join \n" +
                 "    (\n" +
                 "     select \"Activity\".\"activityId\", (1) as interest_match from \"Activity\" join \"Interest\" on \n" +
                 "      \"Interest\".\"userId\"='%s' and \"Activity\".\"sportId\"=\"Interest\".\"sportId\" /*user id 13*/\n" +
                 "        group by \"Activity\".\"activityId\"\n" +
                 "    ) B on A.\"activityId\" = B.\"activityId\"\n" +
                 ") as Interest\n" +
                 "on Activity.\"activityId\"=Interest.\"activityId\"\n" +
                 "order by score desc\n" +
                 "OFFSET %d LIMIT %d /*limit and offset 14 15*/\n",
                 ActivityDao.FALL_OFF_DISTANCE, ActivityDao.FALL_OFF_DISTANCE,
                 ActivityDao.DISTANCE_WEIGHT, ActivityDao.FALL_OFF_DISTANCE, ActivityDao.FALL_OFF_DISTANCE, ActivityDao.FRIEND_WEIGHT, ActivityDao.INTEREST_WEIGHT,
                 userId,
                 ActivityDao.DEFAULT_DISTANCE,
                 longitude, latitude,
                 userId,
                 userId,
                 userId,
                 offset, limit);
    }


    /**
     * Generate SQL to search activities
     * @param activitySearchVO
     * @return
     * @throws SQLException
     * @throws ParseException
     */
    @Override
    public List<Activity> searchActivity(ActivitySearchVO activitySearchVO) throws SQLException, ParseException {
        Connection c = new ConnectionUtil().connectDB();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Activity> activities = new ArrayList<Activity>();
        String sql = "SELECT * FROM \"Activity\"";
        ArrayList<ArrayList<String>> stmtPara = new ArrayList<ArrayList<String>>();

        if (activitySearchVO == null){
            return activities;
        }

        if (!activitySearchVO.getSportId().equals("NULL") || activitySearchVO.getCapacity() != -1
                || !activitySearchVO.getStarttime().equals("NULL") || !activitySearchVO.getEndtime().equals("NULL")
                || activitySearchVO.getLatitude() != 1000 || activitySearchVO.getLongitude() != 1000){
            sql += " WHERE ";

            //1. search sportId
            if (!activitySearchVO.getSportId().equals("NULL")){
                sql += "\"sportId\"=? AND ";
                ArrayList<String> parameter= new ArrayList<String>();
                parameter.add("String");
                parameter.add(activitySearchVO.getSportId());
                stmtPara.add(parameter);
            }

            //2.search capacity
            if (activitySearchVO.getCapacity() != -1){
                sql += "\"capacity\"=? AND ";
                ArrayList<String> parameter= new ArrayList<String>();
                parameter.add("Int");
                parameter.add(String.valueOf(activitySearchVO.getCapacity()));
                stmtPara.add(parameter);
            }

            //3.search by time
            //parse string to Date. if only date, if start and end date: compare startDate to the existing endDate
                                                  // if only start/end date, find the activity after/before 1 hour of this activity
                                    //if only time, default year is 1990.
            //                                      if start and end date: compare startDate to the existing endDate
                                                  // if only start/end date, find the activity after/before 1 hour of this activity
            if (!activitySearchVO.getStarttime().equals("NULL") && !activitySearchVO.getEndtime().equals("NULL")){
                //both start and end
                DateFormat df = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
                Date start = df.parse(activitySearchVO.getStarttime());
                Date end = df.parse(activitySearchVO.getEndtime());
                Date date = new Date(1234);
                String SD = df.format(date);
                System.out.println(SD);

                if (start.getYear()!= 0 && end.getYear() != 1990){
                    //date and time
                    sql += "\"startTime\">=? AND \"startTime\"<=? AND ";

                    ArrayList<String> parameter1= new ArrayList<String>();
                    parameter1.add("TimeStamp");
                    parameter1.add(String.valueOf(start.getTime()));

                    ArrayList<String> parameter2= new ArrayList<String>();
                    parameter2.add("TimeStamp");
                    parameter2.add(String.valueOf(end.getTime()));

                    stmtPara.add(parameter1);
                    stmtPara.add(parameter2);
                }
                else {
                    //only time
                    sql += "EXTRACT(HOUR from \"startTime\")>? AND EXTRACT(HOUR from \"endTime\")<? AND";

                    //sql += "\"startTime\" BETWEEN ? AND ? AND ";
                    ArrayList<String> parameter1= new ArrayList<String>();
                    parameter1.add("Int");
                    parameter1.add(String.valueOf(start.getHours()));

                    ArrayList<String> parameter2= new ArrayList<String>();
                    parameter2.add("Int");
                    parameter2.add(String.valueOf(end.getHours()));

                    stmtPara.add(parameter1);
                    stmtPara.add(parameter2);
                }
            }
            else if (!activitySearchVO.getStarttime().equals("NULL")) {
                //only start
                DateFormat df = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z", Locale.US);
                DateFormat dfTime = new SimpleDateFormat("HH-mm-ss");
                DateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd");

                Date start = df.parse(activitySearchVO.getStarttime());
                if (start.getYear()!= 0){
                    //date and time
                    sql += "EXTRACT(YEAR from \"startTime\")=? AND EXTRACT(MONTH from \"startTime\") =? AND EXTRACT(DAY from \"startTime\") =? AND "
                            + "EXTRACT(HOUR from \"startTime\")>? AND EXTRACT(HOUR from \"startTime\")<? AND ";

                    ArrayList<String> parameter1= new ArrayList<String>();
                    parameter1.add("Int");
                    parameter1.add(String.valueOf(start.getYear() + 1900));

                    ArrayList<String> parameter2= new ArrayList<String>();
                    parameter2.add("Int");
                    parameter2.add(String.valueOf(start.getMonth() + 1));

                    ArrayList<String> parameter3= new ArrayList<String>();
                    parameter3.add("Int");
                    parameter3.add(String.valueOf(start.getDate()));

                    ArrayList<String> parameter4= new ArrayList<String>();
                    parameter4.add("Int");
                    parameter4.add(String.valueOf(start.getHours() - 1));

                    ArrayList<String> parameter5= new ArrayList<String>();
                    parameter5.add("Int");
                    parameter5.add(String.valueOf(start.getHours() + 1));

                    stmtPara.add(parameter1);
                    stmtPara.add(parameter2);
                    stmtPara.add(parameter3);
                    stmtPara.add(parameter4);
                    stmtPara.add(parameter5);
                }
                else {
                    //only time
                    sql += "EXTRACT(HOUR from \"startTime\")>? AND EXTRACT(HOUR from \"startTime\")<? AND ";

                    ArrayList<String> parameter1= new ArrayList<String>();
                    parameter1.add("Int");
                    parameter1.add(String.valueOf(start.getHours() - 1));

                    ArrayList<String> parameter2= new ArrayList<String>();
                    parameter2.add("Int");
                    parameter2.add(String.valueOf(start.getHours() + 1));
                }
            }
            else if (!activitySearchVO.getEndtime().equals("NULL")) {
                //only end
                DateFormat df = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
                Date end = df.parse(activitySearchVO.getEndtime());

                if (end.getYear() != 1990){
                    //date and time
                    sql += "EXTRACT(YEAR from \"endTime\")=? AND EXTRACT(MONTH from \"endTime\") =? AND EXTRACT(DAY from \"endTime\") =? AND "
                            + "EXTRACT(HOUR from \"endTime\")>? AND EXTRACT(HOUR from \"endTime\")<? AND ";

                    ArrayList<String> parameter1= new ArrayList<String>();
                    parameter1.add("Int");
                    parameter1.add(String.valueOf(end.getYear() + 1900));

                    ArrayList<String> parameter2= new ArrayList<String>();
                    parameter2.add("Int");
                    parameter2.add(String.valueOf(end.getMonth() + 1));

                    ArrayList<String> parameter3= new ArrayList<String>();
                    parameter3.add("Int");
                    parameter3.add(String.valueOf(end.getDate()));

                    ArrayList<String> parameter4= new ArrayList<String>();
                    parameter4.add("Int");
                    parameter4.add(String.valueOf(end.getHours() - 1));

                    ArrayList<String> parameter5= new ArrayList<String>();
                    parameter5.add("Int");
                    parameter5.add(String.valueOf(end.getHours() + 1));


                    stmtPara.add(parameter1);
                    stmtPara.add(parameter2);
                    stmtPara.add(parameter3);
                    stmtPara.add(parameter4);
                    stmtPara.add(parameter5);
                }
                else {
                    //only time
                    sql += "EXTRACT(HOUR from \"endTime\")>? AND EXTRACT(HOUR from \"endTime\")<? AND ";

                    ArrayList<String> parameter1= new ArrayList<String>();
                    parameter1.add("Int");
                    parameter1.add(String.valueOf(end.getHours() - 1));

                    ArrayList<String> parameter2= new ArrayList<String>();
                    parameter2.add("Int");
                    parameter2.add(String.valueOf(end.getHours() + 1));
                }
            }

            //4.search by location
            if (activitySearchVO.getLongitude() != 1000 && activitySearchVO.getLatitude() != 1000){
                sql += "\"longitude\" BETWEEN ? AND ? AND \"latitude\" BETWEEN ? AND ? AND ";

                ArrayList<String> parameter1= new ArrayList<String>();
                parameter1.add("Double");
                parameter1.add(String.valueOf(activitySearchVO.getLongitude() - 5));
                stmtPara.add(parameter1);

                ArrayList<String> parameter2= new ArrayList<String>();
                parameter2.add("Double");
                parameter2.add(String.valueOf(activitySearchVO.getLongitude() + 5));
                stmtPara.add(parameter2);

                ArrayList<String> parameter3= new ArrayList<String>();
                parameter3.add("Double");
                parameter3.add(String.valueOf(activitySearchVO.getLatitude() - 5));
                stmtPara.add(parameter3);

                ArrayList<String> parameter4= new ArrayList<String>();
                parameter4.add("Double");
                parameter4.add(String.valueOf(activitySearchVO.getLatitude() + 5));
                stmtPara.add(parameter4);
            }
        }
        else{
            return activities;
        }

        try{
            //sql += "\"status\" = 'OPEN' ORDER BY \"startTime\" ASC";
            stmt = c.prepareStatement(sql.substring(0, sql.length() - 5));
            int index = 0;
            for (ArrayList<String> array : stmtPara){
                switch (array.get(0)){
                    case "String":
                        stmt.setString(++index, array.get(1));
                        break;
                    case "Int":
                        stmt.setInt(++index, Integer.parseInt(array.get(1)));
                        break;
                    case "Date":
                        stmt.setDate(++index, new java.sql.Date(Long.parseLong(array.get(1))));
                        break;
                    case "Double":
                        stmt.setDouble(++index, Double.parseDouble(array.get(1)));
                        break;
                    case "TimeStamp":
                        Date date = new Date(Long.parseLong(array.get(1)));
                        stmt.setTimestamp(++index, new Timestamp(date.getYear(), date.getMonth(), date.getDate(), date.getHours(), date.getMinutes(), 0,0));
                        break;
                    default:
                        break;
                }
            }
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
        }
        finally {
            if (rs != null)
                rs.close();
            stmt.close();
            c.close();
        }

        return activities;
    }

}
