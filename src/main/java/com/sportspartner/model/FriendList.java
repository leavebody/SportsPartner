package com.sportspartner.model;

import java.util.List;

public class FriendList {
    private String userId;
    private List<String> friendList; //list of userId

    public FriendList(){}

    public FriendList(String userId, List<String> friendList) {
        this.userId = userId;
        this.friendList = friendList;
    }

    public String getUserId() { return userId; }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getFriendList() {
        return friendList;
    }

    public void setFriendList(List<String> friendList) {
        this.friendList = friendList;
    }
}
