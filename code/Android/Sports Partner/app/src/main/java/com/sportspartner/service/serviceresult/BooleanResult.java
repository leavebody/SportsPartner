package com.sportspartner.service.serviceresult;

/**
 * Created by xc on 10/21/17.
 */

public class BooleanResult {
    private boolean status;
    private String message;

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
}
