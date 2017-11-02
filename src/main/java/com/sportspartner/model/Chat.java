package com.sportspartner.model;

import java.util.List;

public class Chat{
    private String chatId;
    private List<String> chatMember;

    public Chat(){}

    public Chat(String chatId, List<String> chatMember) {
        this.chatId = chatId;
        this.chatMember = chatMember;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public List<String> getChatMember() {
        return chatMember;
    }

    public void setChatMember(List<String> chatMember) {
        this.chatMember = chatMember;
    }
}
