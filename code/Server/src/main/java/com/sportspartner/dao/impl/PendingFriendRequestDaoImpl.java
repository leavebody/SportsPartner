package com.sportspartner.dao.impl;

import com.sportspartner.dao.PendingFriendRequestDao;
import com.sportspartner.util.ConnectionUtil;
import com.sportspartner.model.PendingFriendRequest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PendingFriendRequestDaoImpl implements PendingFriendRequestDao {

    @Override
    public List<PendingFriendRequest> getAllPendingRequests(String receiverId) {
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

    @Override
    public boolean hasPendingRequest(PendingFriendRequest pendingFriendRequest) {
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

    @Override
    public boolean newPendingRequest(PendingFriendRequest pendingFriendRequest) {
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

    @Override
    public boolean deletePendingRequest(PendingFriendRequest pendingFriendRequest) {
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
