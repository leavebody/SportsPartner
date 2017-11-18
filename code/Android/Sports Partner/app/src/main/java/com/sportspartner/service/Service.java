package com.sportspartner.service;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.google.gson.JsonObject;
import com.sportspartner.util.NetworkResponseRequest;


/**
 * The parent class of all services.
 * @author Xiaochen Li
 */

public class Service {

    /**
     * The helper method to process the result of a network request
     * @param operation The name of the network request.
     *                  This is used when the request returns false status and a toast
     *                  is shown on screen to tell the user which operation is failed.
     * @param response The network response to process
     * @return A BooleanResult with status and message.
     */
    protected static ModelResult booleanRespProcess(NetworkResponse response, String operation){
        ModelResult result = new ModelResult();
        boolean status;

        switch (response.statusCode){
            case 200:
                JsonObject jsResp = NetworkResponseRequest.parseToJsonObject(response);
                try {
                    status = (jsResp.get("response").getAsString().equals("true"));
                    result.setStatus(status);
                    if(!status) {
                        result.setMessage(operation+" failed: "+jsResp.get("message").getAsString());
                    }
                }
                catch (Exception e){
                    Log.d("Json Response", jsResp.toString());
                }
                break;

            default:
                result.setMessage("bad response:"+response.statusCode);
        }
        return result;
    }
}
