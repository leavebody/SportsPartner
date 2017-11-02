package com.sportspartner.dao;
import com.sportspartner.model.PendingFriendRequest;
import java.util.List;

public interface PendingFriendRequestDao {
    public List<PendingFriendRequest> getAllPendingRequests(String receiverId);
    public boolean hasPendingRequest(PendingFriendRequest pendingFriendRequest);
    public boolean newPendingRequest(PendingFriendRequest pendingFriendRequest);
    public boolean deletePendingRequest(PendingFriendRequest pendingFriendRequest);
}

