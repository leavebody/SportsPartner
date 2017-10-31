package com.sportspartner.service;

import android.content.Context;

import com.android.volley.NetworkResponse;
import com.google.gson.JsonObject;
import com.sportspartner.request.UserRequest;
import com.sportspartner.util.ActivityCallBack;
import com.sportspartner.service.serviceresult.BooleanResult;
import com.sportspartner.util.LoginDBHelper;
import com.sportspartner.util.NetworkResponseRequest;
import com.sportspartner.util.VolleyCallback;

/**
 * Created by xc on 10/21/17.
 */

public class UserService extends Service {

    public static void login(final Context c, final String email, String password, final ActivityCallBack callback){

        UserRequest ur = new UserRequest(c);
        ur.loginVolleyRequest(new VolleyCallback(){
            @Override
            public void onSuccess(NetworkResponse response){
                callback.onSuccess(UserService.loginRespProcess(response, c, email));
            }
        }, email, password);
    }
    public static BooleanResult loginRespProcess(NetworkResponse response, Context c, String email){
        BooleanResult result = new BooleanResult();
        switch (response.statusCode){
            case 200:
                boolean loginStatus = false;
                JsonObject jsResp = NetworkResponseRequest.parseToJsonObject(response);
                loginStatus = (jsResp.get("response").getAsString().equals("true"));
                result.setStatus(loginStatus);
                if(loginStatus) {
                    String key = jsResp.get("key").getAsString();
                    LoginDBHelper dbHelper = LoginDBHelper.getInstance(c);
                    dbHelper.insert(email, key);
                } else {
                    result.setMessage("login failed: "+jsResp.get("message").getAsString());
                }
                break;

            default:
                result.setMessage("bad response:"+response.statusCode);
        }
        return result;
    }

    public static void signup(Context c, String email, String password, String confirmPassword, String type, final ActivityCallBack callback){

        UserRequest ur = new UserRequest(c);
        ur.signUpVolleyRequest(new VolleyCallback(){
            @Override
            public void onSuccess(NetworkResponse response){
                callback.onSuccess(UserService.signupRespProcess(response));
            }
        }, email, password, confirmPassword, type);
    }

    public static BooleanResult signupRespProcess(NetworkResponse response){
        BooleanResult result = new BooleanResult();
        boolean signupStatus = false;

        switch (response.statusCode){
            case 200:

                JsonObject jsResp = NetworkResponseRequest.parseToJsonObject(response);
                signupStatus = (jsResp.get("response").getAsString().equals("true"));
                result.setStatus(signupStatus);
                if(!signupStatus) {
                    result.setMessage("signup failed: "+jsResp.get("message").getAsString());
                }

                break;

            default:
                result.setMessage("bad response:"+response.statusCode);
        }
        return result;
    }

    public static void logOut(final Context c, final ActivityCallBack callback){

        UserRequest ur = new UserRequest(c);
        ur.logOutVolleyRequest(new VolleyCallback(){
            @Override
            public void onSuccess(NetworkResponse response){
                callback.onSuccess(UserService.logOutRespProcess(response, c));
            }
        });
    }
    public static BooleanResult logOutRespProcess(NetworkResponse response, Context c){
        BooleanResult result = new BooleanResult();
        switch (response.statusCode){
            case 200:
                boolean status;
                JsonObject jsResp = NetworkResponseRequest.parseToJsonObject(response);
                status = (jsResp.get("response").getAsString().equals("true"));
                result.setStatus(status);
                if(status) {
                    LoginDBHelper dbHelper = LoginDBHelper.getInstance(c);
                    dbHelper.delete();
                } else {
                    result.setMessage("logOut failed: "+jsResp.get("message").getAsString());
                }
                break;

            default:
                result.setMessage("bad response:"+response.statusCode);
        }
        return result;
    }
}
