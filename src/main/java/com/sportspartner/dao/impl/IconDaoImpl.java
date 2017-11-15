package com.sportspartner.dao.impl;

import com.sportspartner.dao.IconDao;
import com.sportspartner.model.Icon;
import com.sportspartner.util.ConnectionUtil;

import java.sql.*;

public class IconDaoImpl implements IconDao {
    /**
     * Get an icon from database.
     * @param iconUUID The UUID for the icon.
     * @return Icon object.
     */
    public Icon getIcon(String iconUUID){
        Connection c = new ConnectionUtil().connectDB();

        PreparedStatement stmt = null;
        ResultSet rs = null;

        Icon icon =null;

        try {
            stmt = c.prepareStatement("SELECT * FROM \"Icon\" WHERE  \"iconUUID\"=?;");
            stmt.setString(1, iconUUID);
            rs = stmt.executeQuery();
            if(rs.next()) {
                String spId = rs.getString("spId");
                String smallPath = rs.getString("small");
                String originPath = rs.getString("origin");
                String object = rs.getString("object");

                icon= new Icon(spId, iconUUID, smallPath, originPath, object);
            }
        } catch (Exception e) {
            e.printStackTrace();
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
        return icon;
    }

    /**
     * Create a new icon entry for database.
     * @param icon Icon object
     * @return true or false for whether it is successfully written to DB
     */
    public boolean updateIcon(Icon icon){

        Connection c = new ConnectionUtil().connectDB();

        PreparedStatement stmt = null;
        int rs;

        String spId = icon.getSpId();
        String iconUUID = icon.getIconUUID();
        String small = icon.getSmallPath();
        String origin = icon.getOriginPath();
        String object = icon.getObject();

        boolean result = false;

        try {
            stmt = c.prepareStatement("UPDATE \"Icon\" SET \"iconUUID\" = ?, \"small\" = ?, \"origin\" = ?" +
                    "WHERE \"spId\"=?;");
            stmt.setString(1, iconUUID);
            stmt.setString(2, small);
            stmt.setString(3, origin);
            stmt.setString(4, spId);
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
     * Add a new entry to Icon table.
     * @param icon Icon object.
     * @return true or false for whether successfully added the entry.
     */
    public boolean newIcon(Icon icon){
        Connection c = new ConnectionUtil().connectDB();

        PreparedStatement stmt = null;
        int rs;

        String spId = icon.getSpId();
        String object = icon.getObject();

        boolean result = false;

        try {
            stmt = c.prepareStatement("INSERT INTO \"Icon\" ( \"spId\",\"object\")" +
                    "VALUES (?, ?);");
            stmt.setString(1, spId);
            stmt.setString(2, object);
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
     * Delete an icon from database.
     * @param spId Sport Partner Id.
     * @return true or false whether successfully delete the icon.
     */
    public boolean deleteIcon(String spId) {
        Connection c = new ConnectionUtil().connectDB();
        PreparedStatement stmt = null;
        int rs;
        try {
            stmt = c.prepareStatement("DELETE FROM \"Icon\" WHERE \"spId\"=?");
            stmt.setString(1, spId);
            rs = stmt.executeUpdate();
            if(rs>0){
                return true;
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
        return false;

    }
}

