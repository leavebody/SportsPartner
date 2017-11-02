package com.sportspartner.model;

import java.util.Date;

public class ActivityComment {
    private String activityId;
    private  String commentId;
    private  String authorId;
    private Date time;
    private String content;

    public ActivityComment(String activityId, String commentId, String authorId, Date time, String content){
        this.activityId = activityId;
        this.commentId = commentId;
        this.authorId = authorId;
        this.time = time;
        this.content = content;
    }

    public String getActivityId() {
        return activityId;
    }

    public String getCommentId() {
        return commentId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public Date getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }
}
