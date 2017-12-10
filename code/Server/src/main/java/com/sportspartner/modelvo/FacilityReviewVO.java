package com.sportspartner.modelvo;

public class FacilityReviewVO {
    private String activityid	;
    private String reviewer	;
    private String reviewee	;
    private double score	;
    private String comments;

    public String getActivityid() {
        return activityid;
    }

    public void setActivityid(String activityid) {
        this.activityid = activityid;
    }

    @Override
    public String toString() {
        return "FacilityReviewVO{" +
                "activityid='" + activityid + '\'' +
                ", reviewer='" + reviewer + '\'' +
                ", reviewee='" + reviewee + '\'' +
                ", score=" + score +
                ", comments='" + comments + '\'' +
                '}';
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

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
