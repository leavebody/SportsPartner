package com.sportspartner.service.serviceresult;

/**
 * The result of a network request.
 * Other than the fields in its parent class,
 * a model is added to help the request caller
 * to get an arbitary class on demand.
 * @author Xiaochen Li
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
