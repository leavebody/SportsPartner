package com.sportspartner.modelvo;

import com.sportspartner.model.User;

public class LoginVO{
    private String userId;
    private String password;
    private String registrationId;

    public LoginVO(){}
//    public LoginVO(String userId, String password, String registrationId) {
//        this.userId = userId;
//        this.password = password;
//        this.registrationId = registrationId;
//    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public boolean isMissingField(){
        return (this.getUserId().equals(null) || this.getPassword().equals(null) ||this.getRegistrationId().equals(null));
    }

    public boolean isCorrectPassword(User user){
        return this.password.equals(user.getPassword());
    }

}
