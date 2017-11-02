package com.sportspartner.dao;

import java.util.List;
import com.sportspartner.model.User;

public interface UserDao {
    public List<User> getAllUsers();
    public User getUser(String userId);
    public boolean newUser(User user);
    public boolean updateUser(User user);
    public boolean deleteUser(String userId);
}
