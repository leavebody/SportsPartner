package com.sportspartner.models;

import android.content.Context;

import org.json.JSONException;

import java.util.Date;

/**
 * Created by yujiaxiao on 11/11/17.
 */

public class Notification {
    private String uuid;
    private String title;
    private String detail;
    private String sender;
    private String type;
    private Date date;
    private int priority;
    private Boolean isRead;

    public Notification() {
    }

    public Notification(String uuid, String title, String detail, String sender, String type, Date date, int priority, Boolean isread) {
        this.uuid = uuid;
        this.title = title;
        this.detail = detail;
        this.sender = sender;
        this.type = type;
        this.date = date;
        this.priority = priority;
        this.isRead = isread;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    public void showDialog(Context context, String myEmail) throws JSONException{};
}
