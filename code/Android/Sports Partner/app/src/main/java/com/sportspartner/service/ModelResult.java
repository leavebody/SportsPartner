package com.sportspartner.service;

/**
 * The result of a network request.
 * Other than the fields in its parent class,
 * a model is added to help the request caller
 * to get an arbitary class on demand.
 * @author Xiaochen Li
 */

public class ModelResult <T> {
    private T model;
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

    public T getModel() {
        return model;
    }

    public void setModel(T model) {
        this.model = model;
    }
}
