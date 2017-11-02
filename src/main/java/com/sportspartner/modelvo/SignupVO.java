package com.sportspartner.modelvo;

import com.sportspartner.model.Person;
import com.sportspartner.model.User;

public class SignupVO {
    private String userId;
    private String password;
    private String confirmPassword;
    private String type;

    public SignupVO(String userId, String password, String confirmPassword, String type) {
        this.userId = userId;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isSamePassword(){
        return this.password.equals(this.confirmPassword);
    }

    public boolean isValidType(){
        return this.type.equals("PERSON") || this.type.equals("PROVIDER");
    }

    public boolean isMissingField(){
        return (this.userId.equals(null) || this.password.equals(null) || this.confirmPassword.equals(null) || this.type.equals(null));
    }

    public User cast2User(){
        return new User(this.userId, this.password, this.type);
    }

    public Person cast2Person(){
        return new Person(this.userId);
    }

}
