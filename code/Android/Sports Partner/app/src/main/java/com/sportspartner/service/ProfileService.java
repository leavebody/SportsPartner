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
    public static void getProfileInfo(Context c, String email, final ActivityCallBack callback) {
        ProfileRequest request = new ProfileRequest(c);
        request.profileInfoVolleyRequest(new VolleyCallback() {
            @Override
            public void onSuccess(NetworkResponse response) {
                callback.getModelOnSuccess(ProfileService.profileInfoRespProcess(response));
            }
        }, email);

    }

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

    public static void getProfileOutline(Context c, String email, final ActivityCallBack callback) {
        ProfileRequest request = new ProfileRequest(c);
        request.userOutlineVolleyRequest(new VolleyCallback() {
            @Override
            public void onSuccess(NetworkResponse response) {
                callback.getModelOnSuccess(ProfileService.profileOutlineRespProcess(response));
            }
        }, email);

    }

    private static ModelResult<UserOutline> profileOutlineRespProcess(NetworkResponse response){
        ModelResult<UserOutline> result = new ModelResult();
        switch (response.statusCode){
            case 200:
                boolean status = false;

                JsonObject jsResp = NetworkResponseRequest.parseToJsonObject(response);
                status = (jsResp.get("response").getAsString().equals("true"));
                result.setStatus(status);
                if(status) {
                    Gson gson = new Gson();
                    result.setModel(gson.fromJson(jsResp.get("userOutline"), UserOutline.class));

                } else {
                    result.setMessage("get profile failed: "+jsResp.get("message").getAsString());
                }

                break;

            default:
                result.setMessage("bad response:"+response.statusCode);
        }
        return result;
    }

    public static void getProfileComments(Context c, String email, final ActivityCallBack callback) {
        ProfileRequest request = new ProfileRequest(c);
        request.profileCommentVolleyRequest(new VolleyCallback() {
            @Override
            public void onSuccess(NetworkResponse response) {
                callback.getModelOnSuccess(ProfileService.profileCommentsRespProcess(response));
            }
        }, email);

    }

    private static ModelResult<ArrayList<ProfileComment>> profileCommentsRespProcess(NetworkResponse response){
        ModelResult<ArrayList<ProfileComment>> result = new ModelResult<>();
        switch (response.statusCode){
            case 200:
                boolean status = false;

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

    public static void updateProfile(Context c, String email, Profile profile, final ActivityCallBack callback) {
        ProfileRequest request = new ProfileRequest(c);
        request.updateProfileVolleyRequest(new VolleyCallback() {
            @Override
            public void onSuccess(NetworkResponse response) {
                callback.onSuccess(ProfileService.booleanRespProcess(response, "update profile"));
            }
        }, profile, email);
    }

    public static void getInterests(Context c, String email, final ActivityCallBack callback) {
        ProfileRequest request = new ProfileRequest(c);
        request.interestsVolleyRequest(new VolleyCallback() {
            @Override
            public void onSuccess(NetworkResponse response) {
                callback.getModelOnSuccess(ProfileService.getInterestsRespProcess(response));
            }
        }, email);
    }

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

    /*
    * @param interests a string for all sportId separated by ','
    *       email the email of the person whose interests are being updated
    * */
    public static void updateInterests(Context c, String email, String interests, final ActivityCallBack callback) {
        ProfileRequest request = new ProfileRequest(c);
        request.updateInterestsVolleyRequest(new VolleyCallback() {
            @Override
            public void onSuccess(NetworkResponse response) {
                callback.onSuccess(ProfileService.booleanRespProcess(response, "update profile"));
            }
        }, interests, email);
    }
}
