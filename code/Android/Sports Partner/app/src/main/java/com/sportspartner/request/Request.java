package com.sportspartner.request;

import android.content.Context;

/**
 * Created by xc on 10/24/17.
 */

public class Request {
    //for tablet
    //protected static final String URL_CONTEXT = "http://10.194.75.125:8080/api.sportspartner.com/";
    //for emulator
    protected static final String URL_CONTEXT = "http://10.0.2.2:8080/api.sportspartner.com/";
    protected Context contextf;

    public Request(Context c){
        contextf = c;
    }
}
