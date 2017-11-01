package com.sportspartner.util;

import com.android.volley.NetworkResponse;

/**
 * Use this interface to handle callback function in the caller class.
 * @author Xiaochen Li
 */

public interface VolleyCallback{
    public void onSuccess(NetworkResponse response);

}