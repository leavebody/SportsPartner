package com.sportspartner.dao.impl;

import com.sportspartner.dao.DeviceRegistrationDao;
import com.sportspartner.model.DeviceRegistration;
import com.sportspartner.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeviceRegistrationDaoImpl implements DeviceRegistrationDao{

    /**
     * Get all device registrations of a user
     * @param userId Id of User
     * @return List of DeviceRegistration
     */
    @Override
    public List<DeviceRegistration> getAllDeviceRegistrations(String userId) {
        Connection c = new ConnectionUtil().connectDB();
        List<DeviceRegistration> deviceRegistrations = new ArrayList<DeviceRegistration>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = c.prepareStatement("SELECT * FROM \"Device_Registration\" WHERE \"userId\" = ? ;");
            stmt.setString(1, userId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                userId = rs.getString("userId");
                String registrationId = rs.getString("registrationId");
                deviceRegistrations.add(new DeviceRegistration(userId, registrationId));
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
        return deviceRegistrations;
    }

    /**
     * Check whether the authorization item is in database.
     * @param authorization
     * @return "true" or "false" for whether the database has the item
     */
    @Override
    public boolean hasDeviceRegistration(DeviceRegistration deviceRegistration) {
        Connection c = new ConnectionUtil().connectDB();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        boolean hasDeviceRegistration = false;
        String userId = deviceRegistration.getUserId();
        String registrationId = deviceRegistration.getRegistrationId();
        try {
            stmt = c.prepareStatement("SELECT * FROM \"Device_Registration\" WHERE \"userId\" = ? AND \"registrationId\" = ? ;");
            stmt.setString(1, userId);
            stmt.setString(2, registrationId);
            rs = stmt.executeQuery();
            if (rs.next()) {
                hasDeviceRegistration = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            //System.exit(0);
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
        return hasDeviceRegistration;
    }

    /**
     * Create a new authorization item.
     * @param authorization
     * @return "true" or "false" for whether successfully created a new authorization
     */
    @Override
    public boolean newDeviceRegistration(DeviceRegistration deviceRegistration) {
        Connection c = new ConnectionUtil().connectDB();
        PreparedStatement statement = null;
        boolean indicator = false;
        String userId = deviceRegistration.getUserId();
        String registrationId = deviceRegistration.getRegistrationId();
        try {
            statement =c.prepareStatement("INSERT INTO \"Device_Registration\" (\"userId\", \"registrationId\") VALUES (?, ?)");
            statement.setString(1, userId);
            statement.setString(2, registrationId);
            int rs = statement.executeUpdate();

            if (rs != 0) {
                indicator = true;
            }
        }catch( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);

        }finally{
            try {
                statement.close();
                c.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return indicator;
    }

    /**
     * Delete an authorization item from database.
     * @param authorization
     * @return "true" or "false" for whether successfully deleted the authorization item.
     */
    @Override
    public boolean deleteDeviceRegistration(DeviceRegistration deviceRegistration) {
        Connection c = new ConnectionUtil().connectDB();

        PreparedStatement stmt = null;
        int rs;
        String userId = deviceRegistration.getUserId();
        String registrationId = deviceRegistration.getRegistrationId();

        boolean result = false;
        try {
            stmt = c.prepareStatement("DELETE FROM \"Device_Registration\" WHERE \"userId\"=? " + "AND \"registrationId\" = ?;");
            stmt.setString(1, userId);
            stmt.setString(2, registrationId);
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
}
