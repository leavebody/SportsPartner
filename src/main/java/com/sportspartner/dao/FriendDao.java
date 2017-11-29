package com.sportspartner.dao;

import com.sportspartner.model.User;

import java.sql.SQLException;
import java.util.List;

public interface FriendDao {
    public List<User> getAllFriends(String user) throws SQLException;
    public boolean getFriend(String user1, String user2) throws SQLException;
    public boolean newFriend(String user1, String user2) throws SQLException;
    public boolean deleteFriend(String user1, String user2) throws SQLException;


}