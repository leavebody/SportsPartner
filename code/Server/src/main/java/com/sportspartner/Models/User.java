package com.sportspartner.Models;

public class User {
    private String userId;
    private String userName;
    private String password;
    private String email;
    private String address;

    public User(){}

    public User(String userId, String userName, String password, String email, String address){
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.address = address;
    }

    public String getUserId() { return userId; }
    public String getUserName() { return userName; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public String getAddress() { return address; }

    public void setUserId(String userId) { this.userId = userId; }
    public void setUserName(String userName) { this.userName = userName; }
    public void setPassword(String password) { this.password = password; }
    public void setEmail(String email) { this.email = email; }
    public void setAddress(String address) { this.address = address; }

}
