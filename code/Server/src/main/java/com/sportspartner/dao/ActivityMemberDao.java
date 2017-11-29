package com.sportspartner.dao;

import com.sportspartner.model.ActivityMember;

import java.sql.SQLException;
import java.util.List;

public interface ActivityMemberDao {
    public List<ActivityMember> getAllActivitymembers(String activityId) throws SQLException;
    public boolean hasActivityMember(ActivityMember activityMember) throws SQLException;
    public boolean newActivityMember(ActivityMember activityMember) throws SQLException;
    public boolean deleteActivityMember(ActivityMember activityMember) throws SQLException;
    public boolean deleteAllActivityMembers(String activityId) throws SQLException;
}
