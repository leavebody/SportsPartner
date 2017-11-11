package com.sportspartner.service.serviceresult;

/**
 * The result of a network request. BooleanResult only contains "status" to
 * indicate whether the request is processed successfully and a "message"
 * to tell why it's not processed successfully.
 *
 * @author Xiaochen Li
 */

public class BooleanResult {

    private boolean status;
    private String message;
    private String userType;

    public final boolean isStatus() {
        return status;
    }

    public final void setStatus(boolean status) {
        this.status = status;
    }

    public final String getMessage() {
        return message;
    }

    public final void setMessage(String message) {
        this.message = message;
    }

    public final String getUserType() {
        return userType;
    }

    public final void setUserType(String userType) {
        this.userType = userType;
    }
}
