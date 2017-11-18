package com.sportspartner.dao;

import com.sportspartner.model.ActivityComment;

import java.util.List;

public interface ActivityCommentDao {
    public List<ActivityComment> getAllActivityComments(String activityId) throws Exception;
//    public boolean hasActivityComment(ActivityComment activityMember);
//    public boolean newActivityComment(ActivityComment activityMember);
//    public boolean updateActivityComment(ActivityComment activityMember);
//    public boolean deleteActivityComment(ActivityComment activityMember);
}
