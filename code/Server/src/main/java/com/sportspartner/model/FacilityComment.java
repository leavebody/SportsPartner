package com.sportspartner.model;

import java.util.Date;

public class FacilityComment {
    private String facilityId;
    private String commentId;
    private String authorId;
    private String providerId;
    private String content;
    private Date time;

    public FacilityComment() {
    }

    public FacilityComment(String facilityId, String commentId, String authorId, String providerId, String content, Date time) {
        this.facilityId = facilityId;
        this.commentId = commentId;
        this.authorId = authorId;
        this.providerId = providerId;
        this.content = content;
        this.time = time;
    }

    public String getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
