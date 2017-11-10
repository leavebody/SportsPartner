package com.sportspartner.dao.impl;

import com.sportspartner.dao.PendingJoinActivityRequestDao;
import com.sportspartner.model.PendingJoinActivityRequest;
import com.sportspartner.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PendingJoinActivityRequestDaoImpl implements PendingJoinActivityRequestDao {
    /**
     * Get all of the pending join activity requests of creator.
     * @param creatorId email of the creator
     * @return List of PendingJoinActivityRequest
     */
    @Override
    public List<PendingJoinActivityRequest> getAllPendingRequests(String creatorId) {
        Connection c = new ConnectionUtil().connectDB();
        List<PendingJoinActivityRequest> requests = new ArrayList<PendingJoinActivityRequest>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = c.prepareStatement("SELECT * FROM \"Pending_Join_Activity_Request\" WHERE \"creatorId\" = ? ;");
            stmt.setString(1, creatorId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String requestorId = rs.getString("requestorId");
                String activityId = rs.getString("activityId");
                requests.add(new PendingJoinActivityRequest(activityId, requestorId, creatorId));
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
        return requests;

    }

    /**
     *  Judge whether a pendingJoinActivityRequest exists in the database
     * @param pendingJoinActivityRequest pendingJoinActivityRequest Object
     * @return true if the pendingJoinActivityRequest exists ; false if not
     */
    @Override
    public boolean hasPendingRequest(PendingJoinActivityRequest pendingJoinActivityRequest) {
        Connection c = new ConnectionUtil().connectDB();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        boolean hasPendingRequest = false;
        String activityId = pendingJoinActivityRequest.getActivityId();
        String requestorId = pendingJoinActivityRequest.getRequestorId();

        try {
            stmt = c.prepareStatement("SELECT * FROM \"Pending_Join_Activity_Request\" WHERE \"activityId\" = ? AND \"requestorId\" = ? ;");
            stmt.setString(1, activityId);
            stmt.setString(2, requestorId);
            rs = stmt.executeQuery();
            if (rs.next()) {
                hasPendingRequest = true;
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
        return hasPendingRequest;
    }

    /**
     *  Create a new pendingJoinActivityRequest in the database
     * @param pendingJoinActivityRequest  pendingJoinActivityRequest Object
     * @return true if the process succeeds; false if not
     */
    @Override
    public boolean newPendingRequest(PendingJoinActivityRequest pendingJoinActivityRequest) {
        Connection c = new ConnectionUtil().connectDB();

        PreparedStatement stmt = null;
        int rs;
        String activityId = pendingJoinActivityRequest.getActivityId();
        String requestorId = pendingJoinActivityRequest.getRequestorId();
        String creatorId = pendingJoinActivityRequest.getCreatorId();
        boolean result = false;

        try {
            stmt = c.prepareStatement("INSERT INTO \"Pending_Join_Activity_Request\" (\"activityId\", \"requestorId\", \"creatorId\")"+
                    "VALUES (?, ?, ?)");
            stmt.setString(1, activityId);
            stmt.setString(2, requestorId);
            stmt.setString(3, creatorId);
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
    /**
     *  Delete a pendingJoinActivityRequest in the database
     * @param pendingJoinActivityRequest  pendingJoinActivityRequest Object
     * @return true if the process succeeds; false if not
     */
    @Override
    public boolean deletePendingRequest(PendingJoinActivityRequest pendingJoinActivityRequest) {
        Connection c = new ConnectionUtil().connectDB();

        PreparedStatement stmt = null;
        int rs;
        String activityId = pendingJoinActivityRequest.getActivityId();
        String requestorId = pendingJoinActivityRequest.getRequestorId();

        boolean result = false;
        try {
            stmt = c.prepareStatement("DELETE FROM \"Pending_Join_Activity_Request\" WHERE \"activityId\"=? " + "AND \"requestorId\" = ?;");
            stmt.setString(1, activityId);
            stmt.setString(2, requestorId);
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
