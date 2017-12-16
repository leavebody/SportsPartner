package com.sportspartner.models;

import java.util.Date;

/**
 * Created by xuanzhang on 12/13/17.
 */

public class ActivityNoti {
    private String activityId;
    private Date startTime;

    public ActivityNoti(){}

    public ActivityNoti(String activityId, Date startTime) {
        this.activityId = activityId;
        this.startTime = startTime;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
}
