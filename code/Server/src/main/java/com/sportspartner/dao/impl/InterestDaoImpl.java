package com.sportspartner.dao.impl;


import com.sportspartner.model.Interest;
import com.sportspartner.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InterestDaoImpl{


    public List<Interest> getAllInterests(){

        Connection c = new ConnectionUtil().connectDB();
        Statement stmt = null;
        ResultSet rs = null;
        List<Interest> interests = new ArrayList<Interest>();

        try {
            stmt = c.createStatement();
            //Table : Interest
            rs = stmt.executeQuery("SELECT * FROM \"Interest\";");

            while (rs.next()) {
                String userId = rs.getString("userId");
                String sportId = rs.getString("sportId");

                interests.add(new Interest(userId, sportId));
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
        //System.out.println(interests.get(0));
        return interests;
    }

    public List<Interest> getInterest(String userId) {

        Connection c = new ConnectionUtil().connectDB();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Interest> interests = new ArrayList<Interest>();

        try {
            String sportId;
            stmt = c.prepareStatement("SELECT * FROM \"Interest\" WHERE \"userId\" = ?;");
            stmt.setString(1, userId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                userId = rs.getString("userId");
                sportId = rs.getString("sportId");

                interests.add(new Interest(userId, sportId));
            }
        } catch (Exception e) {
            e.printStackTrace();
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
        return interests;
    }

    public boolean newInterest(Interest interest){

        Connection c = new ConnectionUtil().connectDB();
        PreparedStatement stmt = null;
        int rs;
        String userId = interest.getUserId();
        String sportId = interest.getSportId();
        try {
            stmt = c.prepareStatement("INSERT INTO \"Interest\" (\"userId\", \"sportId\")"+
                    "VALUES (?, ?)");
            stmt.setString(1, userId);
            stmt.setString(2, sportId);
            rs = stmt.executeUpdate();
            return true;
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
        return false;
    }

    public boolean updateInterest(Interest interest, String newInterest){
        Connection c = new ConnectionUtil().connectDB();
        PreparedStatement stmt = null;
        int rs;
        String userId = interest.getUserId();
        String sportId = interest.getSportId();
        try {
            stmt = c.prepareStatement("UPDATE \"Interest\" SET \"sportId\" = ? WHERE \"userId\"=? AND \"sportId\" = ?;");
            stmt.setString(1, newInterest);
            stmt.setString(2, userId);
            stmt.setString(3, sportId);
            rs = stmt.executeUpdate();
            if(rs>0){
                return true;
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
        return false;
    }

    public boolean deleteInterest(Interest interest){
        Connection c = new ConnectionUtil().connectDB();
        PreparedStatement stmt = null;
        String userId = interest.getUserId();
        String sportId = interest.getSportId();
        int rs;
        try {
            stmt = c.prepareStatement("DELETE FROM \"Interest\" WHERE \"userId\"=? AND \"sportId\"=?;");
            stmt.setString(1, userId);
            stmt.setString(2, sportId);
            rs = stmt.executeUpdate();
            if(rs>0){
                return true;
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
        return false;
    }
}