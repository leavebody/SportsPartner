package com.sportspartner.service;

import android.content.Context;

import com.android.volley.NetworkResponse;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.sportspartner.models.Profile;
import com.sportspartner.models.ProfileComment;
import com.sportspartner.models.UserOutline;
import com.sportspartner.request.ProfileRequest;
import com.sportspartner.service.serviceresult.ModelResult;
import com.sportspartner.util.ActivityCallBack;
import com.sportspartner.util.NetworkResponseRequest;
import com.sportspartner.util.VolleyCallback;

import java.util.ArrayList;

/**
 * Created by yujiaxiao on 10/24/17.
 */

public class ProfileService extends Service {
    /**
     * Get the profile of an user.
     * @param c Caller context.
     * @param email The email of the user.
     * @param callback
     */
    public static void getProfileInfo(Context c, String email, final ActivityCallBack callback) {
        ProfileRequest request = new ProfileRequest(c);
        request.profileInfoVolleyRequest(new VolleyCallback() {
            @Override
            public void onSuccess(NetworkResponse response) {
                callback.getModelOnSuccess(ProfileService.profileInfoRespProcess(response));
            }
        }, email);

    }

    /**
     * The helper method to process the result of profile request.
     * @param response The network response to process
     * @return A ModelResult with model type Profile, which is the requested profile
     */
    private static ModelResult<Profile> profileInfoRespProcess(NetworkResponse response){
        ModelResult<Profile> result = new ModelResult();
        switch (response.statusCode){
            case 200:
                boolean status = false;

                JsonObject jsResp = NetworkResponseRequest.parseToJsonObject(response);
                status = (jsResp.get("response").getAsString().equals("true"));
                result.setStatus(status);
                if(status) {
                    Gson gson = new Gson();
                    result.setModel(gson.fromJson(jsResp.get("profile"), Profile.class));

                } else {
                    result.setMessage("get profile failed: "+jsResp.get("message").getAsString());
                }

                break;

            default:
                result.setMessage("bad response:"+response.statusCode);
        }
        return result;
    }

    /**
     * Get the outline of an user.
     * @param c Caller context.
     * @param email The email of the user.
     * @param callback
     */
    public static void getProfileOutline(Context c, String email, final ActivityCallBack callback) {
        ProfileRequest request = new ProfileRequest(c);
        request.userOutlineVolleyRequest(new VolleyCallback() {
            @Override
            public void onSuccess(NetworkResponse response) {
                callback.getModelOnSuccess(ProfileService.profileOutlineRespProcess(response));
            }
        }, email);

    }

    /**
     * The helper method to process the result of profile outline request.
     * @param response The network response to process
     * @return A ModelResult with model type UserOutline, which is the requested outline
     */
    private static ModelResult<UserOutline> profileOutlineRespProcess(NetworkResponse response){
        ModelResult<UserOutline> result = new ModelResult();
        switch (response.statusCode){
            case 200:
                boolean status;
                JsonObject jsResp = NetworkResponseRequest.parseToJsonObject(response);
                status = (jsResp.get("response").getAsString().equals("true"));
                result.setStatus(status);
                if(status) {
                    Gson gson = new Gson();
                    result.setModel(gson.fromJson(jsResp.get("userOutline"), UserOutline.class));

                } else {
                    result.setMessage("get profile outline failed: "+jsResp.get("message").getAsString());
                }

                break;

            default:
                result.setMessage("bad response:"+response.statusCode);
        }
        return result;
    }

    /**
     * Get the profile comments to an user.
     * @param c Caller context.
     * @param email The email of the user.
     * @param callback
     */
    public static void getProfileComments(Context c, String email, final ActivityCallBack callback) {
        ProfileRequest request = new ProfileRequest(c);
        request.profileCommentVolleyRequest(new VolleyCallback() {
            @Override
            public void onSuccess(NetworkResponse response) {
                callback.getModelOnSuccess(ProfileService.profileCommentsRespProcess(response));
            }
        }, email);

    }

    /**
     * The helper method to process the result of profile outline request.
     * @param response The network response to process
     * @return A ModelResult with model type ArrayList<ProfileComment>,
     *          which is the requested comments
     */
    private static ModelResult<ArrayList<ProfileComment>> profileCommentsRespProcess(NetworkResponse response){
        ModelResult<ArrayList<ProfileComment>> result = new ModelResult<>();
        switch (response.statusCode){
            case 200:
                boolean status;
                JsonObject jsResp = NetworkResponseRequest.parseToJsonObject(response);
                status = (jsResp.get("response").getAsString().equals("true"));
                result.setStatus(status);
                if(status) {
                    JsonArray comment = jsResp.getAsJsonArray("profileComments");
                    Gson gson = new Gson();

                    ArrayList<ProfileComment> al = gson.fromJson(comment, new TypeToken<ArrayList<ProfileComment>>(){}.getType());
                    result.setModel(al);
                } else {
                    result.setMessage("get comment failed: "+jsResp.get("message").getAsString());
                }

                break;

            default:
                result.setMessage("bad response:"+response.statusCode);
        }
        return result;
    }

    /**
     * Update the profile of an user.
     * @param c Caller context.
     * @param email The email of the user.
     * @param profile The updated profile.
     * @param callback
     */
    public static void updateProfile(Context c, String email, Profile profile, final ActivityCallBack callback) {
        ProfileRequest request = new ProfileRequest(c);
        request.updateProfileVolleyRequest(new VolleyCallback() {
            @Override
            public void onSuccess(NetworkResponse response) {
                callback.getBooleanOnSuccess(ProfileService.booleanRespProcess(response, "update profile"));
            }
        }, profile, email);
    }

    /**
     * Get all interests of an user.
     * @param c Caller context.
     * @param email The email of the user.
     * @param callback
     */
    public static void getInterests(Context c, String email, final ActivityCallBack callback) {
        ProfileRequest request = new ProfileRequest(c);
        request.interestsVolleyRequest(new VolleyCallback() {
            @Override
            public void onSuccess(NetworkResponse response) {
                callback.getModelOnSuccess(ProfileService.getInterestsRespProcess(response));
            }
        }, email);
    }

    /**
     * The helper method to process the result of get interests request.
     * @param response The network response to process
     * @return A ModelResult with model type String,
     *          which is the interests of the user, separated by ","
     */
    private static ModelResult<String> getInterestsRespProcess(NetworkResponse response){
        ModelResult<String> result = new ModelResult<>();
        switch (response.statusCode){
            case 200:
                boolean status = false;

                JsonObject jsResp = NetworkResponseRequest.parseToJsonObject(response);
                status = (jsResp.get("response").getAsString().equals("true"));
                result.setStatus(status);
                if(status) {
                    result.setModel(jsResp.get("interests").getAsString());
                } else {
                    result.setMessage("get profile failed: "+jsResp.get("message").getAsString());
                }

                break;

            default:
                result.setMessage("bad response:"+response.statusCode);
        }
        return result;
    }

    /**
     * Update the interests of an user.
     * @param interests A string for all sportId separated by ','
     * @param c Caller context.
     * @param email The email of the user.
     * @param callback
     */
    public static void updateInterests(Context c, String email, String interests, final ActivityCallBack callback) {
        ProfileRequest request = new ProfileRequest(c);
        request.updateInterestsVolleyRequest(new VolleyCallback() {
            @Override
            public void onSuccess(NetworkResponse response) {
                callback.getBooleanOnSuccess(ProfileService.booleanRespProcess(response, "update profile"));
            }
        }, interests, email);
    }
}
