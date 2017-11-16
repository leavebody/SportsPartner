package com.sportspartner.models;

import java.util.Date;

/**
 * Created by yujiaxiao on 11/15/17.
 */

public class NightMode {
    private Date startTime;
    private Date endTime;

    public NightMode() {
    }

    public NightMode(Date startTime, Date endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
