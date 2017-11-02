package com.sportspartner.modelvo;

import com.sportspartner.model.User;

public class LoginVO{
    private String userId;
    private String password;

    public LoginVO(){}
    public LoginVO(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isMissingField(){
        return (this.getUserId().equals(null) || this.getPassword().equals(null));
    }

    public boolean isCorrectPassword(User user){
        return this.password.equals(user.getPassword());
    }

}
