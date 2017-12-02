package com.sportspartner.models;

/**
 * @author Xiaochen Li
 */

public class UserReview {
    private String activityid	;
    private String reviewer	;
    private String reviewee	;
    private double punctuality	;
    private double participation;
    private String comments;

    public String getActivityid() {
        return activityid;
    }

    public void setActivityid(String activityid) {
        this.activityid = activityid;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public String getReviewee() {
        return reviewee;
    }

    public void setReviewee(String reviewee) {
        this.reviewee = reviewee;
    }

    public double getPunctuality() {
        return punctuality;
    }

    public void setPunctuality(double punctuality) {
        this.punctuality = punctuality;
    }

    public double getParticipation() {
        return participation;
    }

    @Override
    public String toString() {
        return "UserReview{" +
                "activityid='" + activityid + '\'' +
                ", reviewer='" + reviewer + '\'' +
                ", reviewee='" + reviewee + '\'' +
                ", punctuality=" + punctuality +
                ", participation=" + participation +
                ", comments='" + comments + '\'' +
                '}';
    }

    public void setParticipation(double participation) {
        this.participation = participation;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
