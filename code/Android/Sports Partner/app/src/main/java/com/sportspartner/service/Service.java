package com.sportspartner.service;

import com.android.volley.NetworkResponse;
import com.google.gson.JsonObject;
import com.sportspartner.service.serviceresult.BooleanResult;
import com.sportspartner.util.NetworkResponseRequest;


/**
 * Created by xc on 10/25/17.
 */

public class Service {

    protected static BooleanResult booleanRespProcess(NetworkResponse response, String operation){
        BooleanResult result = new BooleanResult();
        boolean signupStatus = false;

        switch (response.statusCode){
            case 200:
                JsonObject jsResp = NetworkResponseRequest.parseToJsonObject(response);
                signupStatus = (jsResp.get("response").getAsString().equals("true"));
                result.setStatus(signupStatus);
                if(!signupStatus) {
                    result.setMessage(operation+" failed: "+jsResp.get("message").getAsString());
                }

                break;

            default:
                result.setMessage("bad response:"+response.statusCode);
        }
        return result;
    }
}
