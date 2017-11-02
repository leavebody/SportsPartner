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
                String providerId = rs.getString("providerId");
                String facilityName = rs.getString("facilityName");
                String sportId = rs.getString("sportId");
                String address = rs.getString("address");
                String placeId = rs.getString("placeId");
                String openTime = rs.getString("openTime");
                String description = rs.getString("description");
                String icon = rs.getString("icon");
                double score = rs.getDouble("score");
                int scoreCount = rs.getInt("scoreCount");

                facilities.add(new Facility(facilityId, providerId, facilityName, sportId, address,
                        placeId, openTime, description, icon, score, scoreCount));
            }
        }catch( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
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


            while (rs.next()) {
                String providerId = rs.getString("providerId");
                String facilityName = rs.getString("facilityName");
                String sportId = rs.getString("sportId");
                String address = rs.getString("address");
                String placeId = rs.getString("placeId");
                String openTime = rs.getString("openTime");
                String description = rs.getString("description");
                String icon = rs.getString("icon");
                double score = rs.getDouble("score");
                int scoreCount = rs.getInt("scoreCount");

                facility = new Facility(facilityId, providerId, facilityName, sportId, address,
                        placeId, openTime, description, icon, score, scoreCount);
            }
        }catch( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
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

//
//    public boolean newFacility(Facility facility){return true;}
//    public boolean updateFacility(Facility facility){return true;}
//    public boolean deleteFacility(String facilityId){return true;}

}
