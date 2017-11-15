package com.sportspartner.model;


import java.util.Date;

public class ProfileComment {
    private String userId;
    private String commentId;
    private String authorId;
    private String authorName;
    private Date time;
    private String content;

    public ProfileComment(){}

    public ProfileComment(String userId, String commentId, String authorName, String authorId, Date time, String content) {
        this.userId = userId;
        this.commentId = commentId;
        this.authorId = authorId;
        this.authorName = authorName;
        this.time = time;
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCommentId() { return commentId; }

    public void setCommentId(String commentId) { this.commentId = commentId; }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
