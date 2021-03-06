package com.sportspartner.dao.impl;

import com.sportspartner.dao.PendingFriendRequestDao;
import com.sportspartner.util.ConnectionUtil;
import com.sportspartner.model.PendingFriendRequest;
import com.sportspartner.util.DaoUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PendingFriendRequestDaoImpl implements PendingFriendRequestDao {
    /**
     * Get all of the pending requestes of receiver
     * @param receiverId Id of receiver
     * @return List of PendingFriendRequest
     */
    @Override
    public List<PendingFriendRequest> getAllPendingRequests(String receiverId) throws SQLException {
        Connection c = new ConnectionUtil().connectDB();
        List<PendingFriendRequest> requests = new ArrayList<PendingFriendRequest>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = c.prepareStatement("SELECT * FROM \"Pending_Friend_Request\" WHERE \"receiverId\" = ? ;");
            stmt.setString(1, receiverId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                receiverId = rs.getString("receiverId");
                String senderId = rs.getString("senderId");
                requests.add(new PendingFriendRequest(receiverId,senderId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DaoUtil.CloseDao(rs,stmt,c);
        }
        return requests;

    }

    /**
     *  Judge whethe a pendingFriendRequest exists in the database
     * @param pendingFriendRequest pendingFriendRequest Object
     * @return true if the pendingFriendRequest exists ; false if not
     */
    @Override
    public boolean hasPendingRequest(PendingFriendRequest pendingFriendRequest) throws SQLException {
        Connection c = new ConnectionUtil().connectDB();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        boolean hasPendingRequest = false;
        String receiverId = pendingFriendRequest.getReceiverId();
        String senderId = pendingFriendRequest.getSenderId();

        try {
            stmt = c.prepareStatement("SELECT * FROM \"Pending_Friend_Request\" WHERE \"receiverId\" = ? AND \"senderId\" = ? ;");
            stmt.setString(1, receiverId);
            stmt.setString(2, senderId);
            rs = stmt.executeQuery();
            if (rs.next()) {
                hasPendingRequest = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DaoUtil.CloseDao(rs,stmt,c);
        }
        return hasPendingRequest;
    }

    /**
     *  Create a new pendingFriendRequest in the database
     * @param pendingFriendRequest  pendingFriendRequest Object
     * @return true if the process succeeds; false if not
     */
    @Override
    public boolean newPendingRequest(PendingFriendRequest pendingFriendRequest) throws SQLException {
        Connection c = new ConnectionUtil().connectDB();

        PreparedStatement stmt = null;
        int rs;
        String receiverId = pendingFriendRequest.getReceiverId();
        String senderId = pendingFriendRequest.getSenderId();
        boolean result = false;

        try {
            stmt = c.prepareStatement("INSERT INTO \"Pending_Friend_Request\" (\"receiverId\", \"senderId\")"+
                    "VALUES (?, ?)");
            stmt.setString(1, receiverId);
            stmt.setString(2, senderId);
            rs = stmt.executeUpdate();
            if(rs>0)
                result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DaoUtil.CloseDaoNoRs(stmt,c);
        }
        return result;

    }
    /**
     *  Delete a new pendingFriendRequest in the database
     * @param pendingFriendRequest  pendingFriendRequest Object
     * @return true if the process succeeds; false if not
     */
    @Override
    public boolean deletePendingRequest(PendingFriendRequest pendingFriendRequest) throws SQLException {
        Connection c = new ConnectionUtil().connectDB();

        PreparedStatement stmt = null;
        int rs;
        String receiverId = pendingFriendRequest.getReceiverId();
        String senderId = pendingFriendRequest.getSenderId();

        boolean result = false;
        try {
            stmt = c.prepareStatement("DELETE FROM \"Pending_Friend_Request\" WHERE \"receiverId\"=? " + "AND \"senderId\" = ?;");
            stmt.setString(1, receiverId);
            stmt.setString(2, senderId);
            rs = stmt.executeUpdate();
            if(rs>0)
                result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DaoUtil.CloseDaoNoRs(stmt,c);
        }
        return result;
    }
}
