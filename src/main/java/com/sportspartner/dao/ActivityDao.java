package com.sportspartner.dao;

import com.sportspartner.model.Activity;

import java.sql.SQLException;
import java.util.List;

public interface ActivityDao {
    public List<Activity> getAllActivities() throws SQLException;
    public Activity getActivity(String activityId) throws SQLException;
    public boolean newActivity(Activity activity) throws SQLException;
    public boolean updateActivity(Activity activity) throws SQLException;
    public boolean deleteActivity(String activityId) throws SQLException;
    public List<Activity> getUpcomingActivities(String userId) throws SQLException;
    public List<Activity> getPastActivities(String userId) throws SQLException;
}
