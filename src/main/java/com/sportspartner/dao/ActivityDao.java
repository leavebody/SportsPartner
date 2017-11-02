package com.sportspartner.dao;

import com.sportspartner.model.Activity;

import java.util.List;

public interface ActivityDao {
    public List<Activity> getAllActivities();
    public Activity getActivity(String activityId);
    public boolean newActivity(Activity activity);
    public boolean updateActivity(Activity activity);
    public boolean deleteActivity(Activity activity);
    public List<Activity> getUpcomingActivities(String userId);
    public List<Activity> getPastActivities(String userId);
}
