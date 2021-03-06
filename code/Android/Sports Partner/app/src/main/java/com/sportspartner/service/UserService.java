package com.sportspartner.service;

import android.content.Context;

import com.android.volley.NetworkResponse;
import com.google.gson.JsonObject;
import com.sportspartner.request.UserRequest;
import com.sportspartner.util.chat.PushUtils;
import com.sportspartner.util.dbHelper.LoginDBHelper;
import com.sportspartner.util.NetworkResponseRequest;
import com.sportspartner.util.VolleyCallback;
import com.sportspartner.util.gcmNotification.RegistrationIntentService;

/**
 * @author Xiaochen Li
 */

public class UserService extends Service {

    /**
     * Login by email and password
     * @param c Caller context
     * @param email The email of the user
     * @param password The password of the user.
     * @param callback
     */
    public static void login(final Context c, final String email, String password, String registrationId, final ActivityCallBack callback){

        UserRequest ur = new UserRequest(c);
        ur.loginVolleyRequest(new VolleyCallback(){
            @Override
            public void onSuccess(NetworkResponse response){
                callback.getModelOnSuccess(UserService.loginRespProcess(response, c, email));
            }
        }, email, password, registrationId);
    }

    /**
     * The helper method to process the result of login request.
     * @param response The network response to process
     * @return A ModelResult.
     * @see ModelResult
     */
    public static ModelResult loginRespProcess(NetworkResponse response, Context c, String email){
        ModelResult result = new ModelResult();
        switch (response.statusCode){
            case 200:
                boolean loginStatus = false;
                JsonObject jsResp = NetworkResponseRequest.parseToJsonObject(response);
                loginStatus = (jsResp.get("response").getAsString().equals("true"));
                result.setStatus(loginStatus);
                if(loginStatus) {
                    String key = jsResp.get("key").getAsString();
                    LoginDBHelper dbHelper = LoginDBHelper.getInstance(c);
                    dbHelper.insert(email, key, RegistrationIntentService.getToken());
                } else {
                    result.setMessage("login failed: "+jsResp.get("message").getAsString());
                }
                break;

            default:
                result.setMessage("bad response:"+response.statusCode);
        }
        return result;
    }

    /**
     * Sign up.
     * @param c Caller context
     * @param email The sign up email.
     * @param password The password.
     * @param confirmPassword The confirmed password.
     * @param type The type of the request.
     *             person represents the personal user and
     *             facilityprovider represents the facility provider.
     * @param callback
     */
    public static void signup(Context c, String email, String password, String confirmPassword, String type, final ActivityCallBack callback){

        UserRequest ur = new UserRequest(c);
        ur.signUpVolleyRequest(new VolleyCallback(){
            @Override
            public void onSuccess(NetworkResponse response){
                callback.getModelOnSuccess(UserService.booleanRespProcess(response, "sign up"));
            }
        }, email, password, confirmPassword, type);
    }

    /**
     * Log out. End current login session.
     * @param c
     * @param callback
     */
    public static void logOut(final Context c, final ActivityCallBack callback){
        UserRequest ur = new UserRequest(c);
        ur.logOutVolleyRequest(new VolleyCallback(){
            @Override
            public void onSuccess(NetworkResponse response){
                callback.getModelOnSuccess(UserService.logOutRespProcess(response, c));
            }
        });
        LoginDBHelper.getInstance(c).delete();
        PushUtils.unregisterPushTokenForCurrentUser(c, null);
    }

    /**
     * The helper method to process the result of logout request.
     * @param response The network response to process
     * @return A BooleanResult.
     * @see BooleanResult
     */
    public static ModelResult logOutRespProcess(NetworkResponse response, Context c){
        ModelResult result = new ModelResult();
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
