package com.sportspartner.modelvo;

import java.util.Date;
import com.sportspartner.model.*;

public class ProfileCommentVO {
    private String userId;
    private String authorId;
    private String authorName;
    private String commentId;
    private Date time;
    private String content;

    public ProfileCommentVO(){}

    public ProfileCommentVO(String userId, String authorId, String authorName, String commentId, Date time, String content) {
        this.userId = userId;
        this.authorId = authorId;
        this.authorName = authorName;
        this.commentId = commentId;
        this.time = time;
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorid(String authorid) {
        this.authorId = authorid;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
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

    public void setFromProfileComment(ProfileComment profileComment){
        this.userId = profileComment.getUserId();
        this.authorId = profileComment.getAuthorId();
        this.authorName = profileComment.getAuthorName();
        this.commentId = profileComment.getCommentId();
        this.time = profileComment.getTime();
        this.content = profileComment.getContent();
    }
}
