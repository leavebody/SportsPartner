package com.sportspartner.dao;

import com.sportspartner.model.ActivityComment;

import java.sql.SQLException;
import java.util.List;

public interface ActivityCommentDao {
    public List<ActivityComment> getAllActivityComments(String activityId)  throws SQLException;
//    public boolean hasActivityComment(ActivityComment activityMember);
//    public boolean newActivityComment(ActivityComment activityMember);
//    public boolean updateActivityComment(ActivityComment activityMember);
//    public boolean deleteActivityComment(ActivityComment activityMember);
}
