package com.sportspartner.dao;
import com.sportspartner.model.PendingFriendRequest;

import java.sql.SQLException;
import java.util.List;

public interface PendingFriendRequestDao {
    public List<PendingFriendRequest> getAllPendingRequests(String receiverId) throws SQLException;
    public boolean hasPendingRequest(PendingFriendRequest pendingFriendRequest) throws SQLException;
    public boolean newPendingRequest(PendingFriendRequest pendingFriendRequest) throws SQLException;
    public boolean deletePendingRequest(PendingFriendRequest pendingFriendRequest) throws SQLException;
}

