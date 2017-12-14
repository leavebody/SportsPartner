package com.sportspartner.dao;

import com.sportspartner.model.Activity;
import com.sportspartner.modelvo.ActivitySearchVO;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public interface ActivityDao {
    public static final double FALL_OFF_DISTANCE = 5; // in km
    public static final double FRIEND_WEIGHT = 1;
    public static final double DISTANCE_WEIGHT = 2;
    public static final double INTEREST_WEIGHT = 1;
    public static final double DEFAULT_DISTANCE = 20000; // in km

    public List<Activity> getAllActivities() throws SQLException;
    public Activity getActivity(String activityId) throws SQLException;
    public boolean newActivity(Activity activity) throws SQLException;
    public boolean updateActivity(Activity activity) throws SQLException;
    public boolean updateCapacityById(String activityId) throws SQLException;
    public boolean deleteActivity(String activityId) throws SQLException;
    public List<Activity> getUpcomingActivities(String userId) throws SQLException;
    public List<Activity> getPastActivities(String userId) throws SQLException;
    public List<Activity> searchActivity(ActivitySearchVO activitySearchVO) throws SQLException, ParseException;
    public List<Activity> getRecommendActivities(String userId, double langitudeUser, double latitudeUser, int limit, int offset) throws SQLException;
}
