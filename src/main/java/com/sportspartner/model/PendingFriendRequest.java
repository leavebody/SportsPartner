package com.sportspartner.model;

public class PendingFriendRequest {
    private String receiverId;
    private String senderId;
    public PendingFriendRequest(String receiverId,String senderId){

        this.receiverId = receiverId;
        this.senderId = senderId;
    }
    public String getSenderId() {
        return senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }
}
