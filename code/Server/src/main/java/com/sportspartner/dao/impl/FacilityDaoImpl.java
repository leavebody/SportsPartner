package com.sportspartner.dao.impl;

import com.sportspartner.dao.FacilityDao;
import com.sportspartner.model.Facility;
import com.sportspartner.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FacilityDaoImpl implements FacilityDao {
    Connection c = new ConnectionUtil().connectDB();

    /**
     * Get all facilities in the database.
     * @return a list of Facility objects.
     */
    public List<Facility> getAllFacilities(){
        List<Facility> facilities = new ArrayList<Facility>();

        ResultSet rs = null;
        PreparedStatement statement;
        try {
            statement = c.prepareStatement("SELECT * from \"Facility\"");

            rs = statement.executeQuery();

            while (rs.next()) {
                String facilityId = rs.getString("facilityId");
                String facilityName = rs.getString("facilityName");
                String iconUUID = rs.getString("iconUUID");
                String sportId = rs.getString("sportId");
                double longitude = rs.getDouble("longitude");
                double latitude = rs.getDouble("latitude");
                String zipcode = rs.getString("zipcode");
                String address = rs.getString("address");
                String providerId = rs.getString("providerId");
                double score = rs.getDouble("score");
                int scoreCount = rs.getInt("scoreCount");
                String openTime = rs.getString("openTime");
                String description = rs.getString("description");

                facilities.add(new Facility(facilityId, facilityName, iconUUID, sportId, longitude, latitude, zipcode, address, providerId, score, scoreCount, openTime, description));
            }
        }catch( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            
        }finally{
            try {
                rs.close();
                c.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return facilities;

    }

    /**
     * Get a facility details specified by facilityId.
     * @param facilityId
     * @return Facility object
     */
    public Facility getFacility(String facilityId){
        ResultSet rs = null;
        PreparedStatement statement;
        Facility facility = null;
        try {
            statement = c.prepareStatement("SELECT * from \"Facility\" WHERE \"facilityId\" = ?");
            statement.setString(1, facilityId);
            rs = statement.executeQuery();

            if(rs.next()) {
                String facilityName = rs.getString("facilityName");
                String iconUUID = rs.getString("iconUUID");
                String sportId = rs.getString("sportId");
                double longitude = rs.getDouble("longitude");
                double latitude = rs.getDouble("latitude");
                String zipcode = rs.getString("zipcode");
                String address = rs.getString("address");
                String providerId = rs.getString("providerId");
                double score = rs.getDouble("score");
                int scoreCount = rs.getInt("scoreCount");
                String openTime = rs.getString("openTime");
                String description = rs.getString("description");

                facility = new Facility(facilityId, facilityName, iconUUID, sportId, longitude, latitude, zipcode, address, providerId, score, scoreCount, openTime, description);
            }
        }catch( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            
        }finally{
            try {
                rs.close();
                c.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return facility;
    }


      public boolean newFacility(Facility facility){
          Connection c = new ConnectionUtil().connectDB();

          PreparedStatement stmt = null;
          int rs;
          String facilityId = facility.getFacilityId();
          String facilityName = facility.getFacilityName();
          String iconUUID = facility.getIconUUID();
          String sportId = facility.getSportId();
          double longitude = facility.getLongitude();
          double latitude = facility.getLatitude();
          String zipcode = facility.getZipcode();
          String address = facility.getAddress();
          String providerId = facility.getProviderId();
          double score = facility.getScore();
          int scoreCount = facility.getScoreCount();
          String openTime = facility.getOpenTime();
          String description = facility.getDescription();
          boolean result = false;

          try {
              stmt = c.prepareStatement("INSERT INTO \"Facility\" (\"facilityId\", \"facilityName\",\"iconUUID\", \"sportId\", \"longitude\", \"latitude\", \"zipcode\", \"address\", \"providerId\", \"score\", \"scoreCount\", \"openTime\" , \"description\")"+
                      "VALUES (?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?)");
              stmt.setString(1, facilityId);
              stmt.setString(2, facilityName);
              stmt.setString(3, iconUUID);
              stmt.setString(4, sportId);
              stmt.setDouble(5, longitude);
              stmt.setDouble(6, latitude);
              stmt.setString(7, zipcode);
              stmt.setString(8, address);
              stmt.setString(9, providerId);
              stmt.setDouble(10, score);
              stmt.setInt(11, scoreCount);
              stmt.setString(12,openTime);
              stmt.setString(13,description);
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


      public boolean updateFacility(Facility facility){
          Connection c = new ConnectionUtil().connectDB();

          PreparedStatement stmt = null;
          int rs;
          String facilityId = facility.getFacilityId();
          String facilityName = facility.getFacilityName();
          String iconUUID = facility.getIconUUID();
          String sportId = facility.getSportId();
          double longitude = facility.getLongitude();
          double latitude = facility.getLatitude();
          String zipcode = facility.getZipcode();
          String address = facility.getAddress();
          String providerId = facility.getProviderId();
          double score = facility.getScore();
          int scoreCount = facility.getScoreCount();
          String openTime = facility.getOpenTime();
          String description = facility.getDescription();
          boolean result = false;

          try {
              stmt = c.prepareStatement("UPDATE \"Facility\" SET \"facilityId\" = ? , \"facilityName\" = ? , \"iconUUID\" = ?,\"sportId\" = ? , \"longitude\" = ? , \"latitude\" = ?, " +
                      " \"zipcode\"=?,\"address\"=?, \"providerId\"=?, \"score\" = ?,\"scoreCount\" = ? , \"openTime\" = ? , \"description\" = ? WHERE \"facilityId\"=? ;");
              stmt.setString(1, facilityId);
              stmt.setString(2, facilityName);
              stmt.setString(3, iconUUID);
              stmt.setString(4, sportId);
              stmt.setDouble(5, longitude);
              stmt.setDouble(6, latitude);
              stmt.setString(7, zipcode);
              stmt.setString(8, address);
              stmt.setString(9, providerId);
              stmt.setDouble(10, score);
              stmt.setInt(11, scoreCount);
              stmt.setString(12,openTime);
              stmt.setString(13,description);
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

     public boolean deleteFacility(String facilityId){
         Connection c = new ConnectionUtil().connectDB();

         PreparedStatement stmt = null;
         int rs;
         boolean result = false;

         try {
             stmt = c.prepareStatement("DELETE FROM \"Facility\" WHERE \"facilityId\"=? ");
             stmt.setString(1, facilityId);

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

}
