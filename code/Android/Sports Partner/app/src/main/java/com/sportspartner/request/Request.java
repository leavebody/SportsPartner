package com.sportspartner.request;

import android.content.Context;

/**
 * @author Xiaochen Li
 */

/**
 * The base class of all requests
 */
public class Request {
    //for heroku
    protected static final String URL_CONTEXT = "http://oosesportspartner.herokuapp.com/api.sportspartner.com/";
    //for tablet
    //protected static final String URL_CONTEXT = "http://10.194.79.55:4567/api.sportspartner.com/";
    //for emulator
    //protected static final String URL_CONTEXT = "http://10.0.2.2:4567/api.sportspartner.com/";

    //server: heroku
    protected static final String URL_CONTEXT = "http://oosesportspartner.herokuapp.com/api.sportspartner.com/";
    //server: localhost, device: tablet
    //protected static final String URL_CONTEXT = "http://10.194.79.55:4567/api.sportspartner.com/";
    //server: localhost, device: emulator
    //protected static final String URL_CONTEXT = "http://10.0.2.2:4567/api.sportspartner.com/";

    protected Context contextf;

    public Request(Context c){
        contextf = c;
    }
}
