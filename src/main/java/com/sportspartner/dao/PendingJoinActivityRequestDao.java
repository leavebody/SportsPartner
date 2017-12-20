package com.sportspartner.dao;

import com.sportspartner.model.PendingJoinActivityRequest;

import java.sql.SQLException;
import java.util.List;

public interface PendingJoinActivityRequestDao {
    public List<PendingJoinActivityRequest> getAllPendingRequests(String creatorId)throws SQLException;
    public boolean hasPendingRequest(PendingJoinActivityRequest pendingJoinActivityRequest)throws SQLException;
    public boolean newPendingRequest(PendingJoinActivityRequest pendingJoinActivityRequest)throws SQLException;
    public boolean deletePendingRequest(PendingJoinActivityRequest pendingJoinActivityRequest)throws SQLException;
}
