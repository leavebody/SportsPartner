package com.sportspartner.util;

import com.sportspartner.models.ProfileComment;
import com.sportspartner.service.serviceresult.*;

import java.util.ArrayList;

/**
 * Use this class to handle callback function in the caller class.
 * @author Xiaochen Li
 */

public class ActivityCallBack <M> {
    public void onSuccess(BooleanResult booleanResult){}
    public void getModelOnSuccess(ModelResult<M> modelResult){}
}

