package com.sportspartner.dao.impl;

import com.sportspartner.dao.SportDao;
import com.sportspartner.model.Sport;
import com.sportspartner.util.ConnectionUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class SportDaoImpl implements SportDao {


    /**
     * Get all the sports from the database
     * @return list of sports
     */
    public List<Sport> getAllSports() {
        Connection c = new ConnectionUtil().connectDB();
        List<Sport> sports = new ArrayList<Sport>();
        Statement stmt = null;
        ResultSet rs = null;
        try {

            stmt = c.createStatement();
            rs = stmt.executeQuery("SELECT * FROM \"Sport\";");

            while (rs.next()) {
                String sportId = rs.getString("sportId");
                String sportName = rs.getString("sportName");
                String sportIconUUID = rs.getString("sportIconUUID");

                sports.add(new Sport(sportId, sportName, sportIconUUID));
                //TODO delete Get Print
                //System.out.println("All: " + sportId + " " + sportName + " " + sportIcon);
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": getAllSport " + e.getMessage());
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
        return sports;
    }

    /**
     *  Search the sport Object by sportId
     * @param sportId Id of sport
     * @return Sport Object
     */
    public Sport getSport(String sportId) {
        Connection c = new ConnectionUtil().connectDB();
        Sport sport = new Sport();
        ResultSet rs = null;
        PreparedStatement statement = null;
        try {

            statement = c.prepareStatement("SELECT * FROM \"Sport\" WHERE \"sportId\" = ?;");
            statement.setString(1, sportId);
            rs = statement.executeQuery();

            rs.next();
            sportId = rs.getString("sportId");
            String sportName = rs.getString("sportName");
            String sportIconUUID = rs.getString("sportIconUUID");

            sport = new Sport(sportId, sportName,sportIconUUID);
            //TODO delete get print

    } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": getSport " + e.getMessage());
            System.exit(0);
    }finally {
            try {
                rs.close();
                statement.close();
                c.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return sport;
}

    /**
     *  Create a new Sports in the database
     * @param sport Sport Object
     * @return true if the process succeeds, false if not
     */
    public boolean newSport(Sport sport){
        Connection c = new ConnectionUtil().connectDB();
        PreparedStatement statement = null;
        int numUpdated = 0;
        try {
            String sql = "INSERT INTO \"Sport\" (\"sportId\", \"sportName\",\"sportIconUUID\") " +
                    "VALUES(?, ?, ?)";
            statement = c.prepareStatement(sql);
            statement.setString(1, sport.getSportId());
            statement.setString(2, sport.getSportName());
            statement.setString(3, sport.getSportIconUUID());
            numUpdated = statement.executeUpdate();

            if(numUpdated <= 0){
                return false;
            }

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": Insert" + e.getMessage());
            System.exit(0);
            return false;
        }finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (c != null) {
                    c.close();
                }
                return true;

            } catch (SQLException ex) {
                //TODO throw exception
                return false;
            }
        }
    }

    /**
     *  Update a new Sports in the database
     * @param sport Sport Object
     * @return true if the process succeeds, false if not
     */
    public boolean updateSport(Sport sport){
        Connection c = new ConnectionUtil().connectDB();
        PreparedStatement statement = null;
        int numUpdated = 0;
        try {
            String sql = "UPDATE \"Sport\" set \"sportName\" = ?,  \"sportIconUUID\" = ? WHERE \"sportId\" = ?";
            statement = c.prepareStatement(sql);
            statement.setString(3, sport.getSportId());
            statement.setString(1, sport.getSportName());
            statement.setString(2, sport.getSportIconUUID());
            numUpdated = statement.executeUpdate();

            if(numUpdated <= 0){
                return false;
            }

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": Update " + e.getMessage());
            System.exit(0);
            return false;
        }finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (c != null) {
                    c.close();
                }
                //TODO Update Print
                return true;

            } catch (SQLException ex) {
                //TODO throw exception
                return false;
            }
        }
    }
    /**
     *  Delete a new Sports in the database
     * @param sportId Id of sport
     * @return true if the process succeeds, false if not
     */
    public boolean deleteSport(String sportId){
        Connection c = new ConnectionUtil().connectDB();
        PreparedStatement statement = null;
        int numUpdated;
        try {
            String sql = "DELETE FROM \"Sport\" WHERE \"sportId\" = ?";
            statement = c.prepareStatement(sql);
            statement.setString(1, sportId);
            numUpdated = statement.executeUpdate();

            if(numUpdated <= 0){
                return false;
            }

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": Delete " + e.getMessage());
            System.exit(0);
            return false;
        }finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (c != null) {
                    c.close();
                }
                //TODO Delete Print
                return true;

            } catch (SQLException ex) {
                //TODO throw exception
                return false;
            }
        }
    }
}
