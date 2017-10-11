package com.sportspartner.Models;

import java.util.List;

public class Moments {
    private String userId;
    private String momentId;
    private String content;
    private String time;
    private List<String> thumbsUp; //list of userId
    private List<String> comment;

    public Moments(){}

    public Moments(String userId, String momentId, String content, String time, List<String> thumbsUp, List<String> comment) {
        this.userId = userId;
        this.momentId = momentId;
        this.content = content;
        this.time = time;
        this.thumbsUp = thumbsUp;
        this.comment = comment;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMomentId() {
        return momentId;
    }

    public void setMomentId(String momentId) {
        this.momentId = momentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<String> getThumbsUp() {
        return thumbsUp;
    }

    public void setThumbsUp(List<String> thumbsUp) {
        this.thumbsUp = thumbsUp;
    }

    public List<String> getComment() {
        return comment;
    }

    public void setComment(List<String> comment) {
        this.comment = comment;
    }
}
