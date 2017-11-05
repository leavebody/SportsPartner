package com.sportspartner.modelvo;
import com.sportspartner.model.Person;
public class UserOutlineVO {
    private String userId;
    private String userName;
    private String iconUUID;

    public UserOutlineVO(){}

    public UserOutlineVO(String userId, String userName, String iconUUID) {
        this.userId = userId;
        this.userName = userName;
        this.iconUUID = iconUUID;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIconUUID() {
        return iconUUID;
    }

    public void setIconUUID(String iconUUID) {
        this.iconUUID = iconUUID;
    }

    public void setFromPerson(Person person){
        this.userId = person.getUserId();
        this.userName = person.getUserName();
        this.iconUUID = person.getIconUUID();
    }
}
