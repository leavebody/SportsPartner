package com.sportspartner.util;

import com.sportspartner.service.serviceresult.*;

/**
 * Use this class to handle callback function in the caller class.
 * @author Xiaochen Li
 */

public class ActivityCallBack <M> {
    public void getBooleanOnSuccess(BooleanResult booleanResult){}
    public void getModelOnSuccess(ModelResult<M> modelResult){}
}

