package com.sportspartner.models;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by yujiaxiao on 10/23/17.
 */

public class ProfileComment {
    private String userId;
    private String authorId;
    private Date time;
    private String content;

    public ProfileComment(){}

    public ProfileComment(String userId, String authorId, Date time, String content) {
        this.userId = userId;
        this.authorId = authorId;
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

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
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
