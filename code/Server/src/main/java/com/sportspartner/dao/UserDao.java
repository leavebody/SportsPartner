package com.sportspartner.dao;

import java.sql.SQLException;
import java.util.List;
import com.sportspartner.model.User;

public interface UserDao {
    //public List<User> getAllUsers();
    public User getUser(String userId) throws SQLException;
    public boolean newUser(User user) throws SQLException;
    //public boolean updateUser(User user) throws SQLException;
    public boolean deleteUser(String userId) throws SQLException;
}
