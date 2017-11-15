package com.sportspartner.dao;

import com.sportspartner.model.User;

import java.util.List;

public interface FriendDao {
    public List<User> getAllFriends(String user);
    public boolean getFriend(String user1, String user2);
    public boolean newFriend(String user1, String user2);
    public boolean deleteFriend(String user1, String user2);


}