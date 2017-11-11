package com.sportspartner.service;

import com.sportspartner.service.ModelResult;

/**
 * Use this interface to handle callback function in the caller class.
 * @author Xiaochen Li
 */

public interface ActivityCallBack <M> {
    void getModelOnSuccess(ModelResult<M> modelResult);
}

