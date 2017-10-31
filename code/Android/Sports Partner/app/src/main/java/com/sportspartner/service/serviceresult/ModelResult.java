package com.sportspartner.service.serviceresult;

/**
 * Created by xc on 10/26/17.
 */

public class ModelResult <T> extends BooleanResult {
    private T model;

    public T getModel() {
        return model;
    }

    public void setModel(T model) {
        this.model = model;
    }
}
