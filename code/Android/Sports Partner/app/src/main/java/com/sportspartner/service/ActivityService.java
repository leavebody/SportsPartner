package com.sportspartner.service;

import android.content.Context;

import com.android.volley.NetworkResponse;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.sportspartner.models.SActivity;
import com.sportspartner.models.SActivityOutline;
import com.sportspartner.request.ActivityRequest;
import com.sportspartner.util.NetworkResponseRequest;
import com.sportspartner.util.VolleyCallback;


import java.util.ArrayList;
import java.util.Date;

/**
 * @author Xiaochen Li
 */

public class ActivityService extends Service {
    /**
     * Get a list of upcoming activity outlines of a user.
     * @param c Caller context.
     * @param email The email of the user.
     * @param limit The number of activities that will return.
     * @param offset The index of the first activity that will return.
     * @param callback
     */
    public static void getUpcomingActivities(Context c, String email, int limit, int offset, final ActivityCallBack callback) {

        ActivityRequest request = new ActivityRequest(c);
        request.activitiesOutlineRequest(new VolleyCallback() {
            @Override
            public void onSuccess(NetworkResponse response) {
                callback.getModelOnSuccess(ActivityService.getActivitiesOutlineRespProcess(response));
            }
        }, "upcoming", email, limit, offset);
    }

    /**
     * Get a list of history activity outlines of a user.
     * @param c Caller context.
     * @param email The email of the user.
     * @param limit The number of activities that will return.
     * @param offset The index of the first activity that will return.
     * @param callback
     */
    public static void getHistoryActivities(Context c, String email, int limit, int offset, final ActivityCallBack callback) {

        ActivityRequest request = new ActivityRequest(c);
        request.activitiesOutlineRequest(new VolleyCallback() {
            @Override
            public void onSuccess(NetworkResponse response) {
                callback.getModelOnSuccess(ActivityService.getActivitiesOutlineRespProcess(response));
            }
        }, "past", email, limit, offset);
    }

    /**
     * Get a list of recommended activity outlines of a user.
     * @param c Caller context.
     * @param email The email of the user.
     * @param limit The number of activities that will return.
     * @param offset The index of the first activity that will return.
     * @param callback
     */
    public static void getRecommendActivities(Context c, String email, int limit, int offset, final ActivityCallBack callback) {

        ActivityRequest request = new ActivityRequest(c);
        request.activitiesOutlineRequest(new VolleyCallback() {
            @Override
            public void onSuccess(NetworkResponse response) {
                callback.getModelOnSuccess(ActivityService.getActivitiesOutlineRespProcess(response));
            }
        }, "recommend", email, limit, offset);

    }

    /**
     * The helper method to process the result of outlines request.
     * @param response The network response to process
     * @return A ModelResult with model type ArrayList<SActivityOutline>
     */
    public static ModelResult<ArrayList<SActivityOutline>> getActivitiesOutlineRespProcess(NetworkResponse response){
        ModelResult<ArrayList<SActivityOutline>> result = new ModelResult();
        switch (response.statusCode){
            case 200:
                boolean status;
                JsonObject jsResp = NetworkResponseRequest.parseToJsonObject(response);
                status = (jsResp.get("response").getAsString().equals("true"));
                result.setStatus(status);
                if(status) {
                    Gson gson = new Gson();
                    JsonArray jsArrayAct = jsResp.getAsJsonArray("activityOutlines");
                    ArrayList<SActivityOutline> arrayAct = gson.fromJson(jsArrayAct,
                            new TypeToken<ArrayList<SActivityOutline>>(){}.getType());

                    result.setModel(arrayAct);

                } else {
                    result.setMessage("outline request failed: "+jsResp.get("message").getAsString());
                }

                break;

            default:
                result.setMessage("bad response:"+response.statusCode);
        }
        return result;
    }

    /**
     * Get an activity by its id.
     * @param c The caller context.
     * @param id The id of the activity.
     * @param callback
     */
    public static void getSActivity(Context c, String id, final ActivityCallBack callback) {

        ActivityRequest request = new ActivityRequest(c);
        request.activityInfoRequest(new VolleyCallback() {
            @Override
            public void onSuccess(NetworkResponse response) {
                callback.getModelOnSuccess(ActivityService.getActivityRespProcess(response));
            }
        }, "full", id);
    }

    /**
     * The helper method to process the result of get activity request.
     * @param response The network response to process
     * @return A ModelResult with model type SActivity
     */
    public static ModelResult<SActivity> getActivityRespProcess(NetworkResponse response){
        ModelResult<SActivity> result = new ModelResult<>();
        switch (response.statusCode){
            case 200:
                boolean status = false;

                JsonObject jsResp = NetworkResponseRequest.parseToJsonObject(response);
                status = (jsResp.get("response").getAsString().equals("true"));
                result.setStatus(status);
                if(status) {
                    Gson gson = new Gson();
                    JsonObject jsObjAct = jsResp.getAsJsonObject("activity");
                    SActivity activity = gson.fromJson(jsObjAct, SActivity.class);
                    result.setModel(activity);
                    String userType = jsResp.get("userType").getAsString();
                    result.setUserType(userType);
                } else {
                    result.setMessage("get activity request failed: "+jsResp.get("message").getAsString());
                }

                break;

            default:
                result.setMessage("bad response:"+response.statusCode);
        }
        return result;
    }

    /**
     * Get an activity by its id.
     * @param c The caller context.
     * @param id The id of the activity.
     * @param callback
     */
    public static void getSActivityOutline(Context c, String id, final ActivityCallBack callback) {

        ActivityRequest request = new ActivityRequest(c);
        request.activityInfoRequest(new VolleyCallback() {
            @Override
            public void onSuccess(NetworkResponse response) {
                callback.getModelOnSuccess(ActivityService.getActivityOutlineRespProcess(response));
            }
        }, "outline", id);
    }

    /**
     * The helper method to process the result of get activity outline request.
     * @param response The network response to process
     * @return A ModelResult with model type SActivityOutline
     */
    public static ModelResult<SActivityOutline> getActivityOutlineRespProcess(NetworkResponse response){
        ModelResult<SActivityOutline> result = new ModelResult<>();
        switch (response.statusCode){
            case 200:
                boolean status;

                JsonObject jsResp = NetworkResponseRequest.parseToJsonObject(response);
                status = (jsResp.get("response").getAsString().equals("true"));
                result.setStatus(status);
                if(status) {
                    Gson gson = new Gson();
                    JsonObject jsObjAct = jsResp.getAsJsonObject("activityOutline");
                    SActivityOutline activity = gson.fromJson(jsObjAct, SActivityOutline.class);
                    result.setModel(activity);
                } else {
                    result.setMessage("get activity outline request failed: "+jsResp.get("message").getAsString());
                }

                break;

            default:
                result.setMessage("bad response:"+response.statusCode);
        }
        return result;
    }

    /**
     * Create an new activity.
     * @param c Caller context
     * @param activity The activity to create.
     * @param callback
     */
    public static void createActivity(Context c, SActivity activity, final ActivityCallBack callback) {

        ActivityRequest request = new ActivityRequest(c);
        request.createActivityRequest(new VolleyCallback() {
            @Override
            public void onSuccess(NetworkResponse response) {
                callback.getModelOnSuccess(ActivityService.createActivityRespProcess(response));
            }
        }, activity);
    }

    /**
     * The helper method to process the result of get activity outline request.
     * @param response The network response to process
     * @return A ModelResult with model type String, which is the id of the created activity
     */
    public static ModelResult<String> createActivityRespProcess(NetworkResponse response){
        ModelResult<String> result = new ModelResult<>();
        switch (response.statusCode){
            case 200:
                boolean status;
                JsonObject jsResp = NetworkResponseRequest.parseToJsonObject(response);
                status = (jsResp.get("response").getAsString().equals("true"));
                result.setStatus(status);
                if(status) {
                    result.setModel(jsResp.get("activityId").getAsString());
                } else {
                    result.setMessage("create activity request failed: "+jsResp.get("message").getAsString());
                }

                break;

            default:
                result.setMessage("bad response:"+response.statusCode);
        }
        return result;
    }

    /**
     * uptate an new activity.
     * @param c Caller context
     * @param activity The activity to create.
     * @param activityId The Id of the activity
     * @param callback
     */
    public static void updateActivity(Context c, SActivity activity, final ActivityCallBack callback) {

        ActivityRequest request = new ActivityRequest(c);
        request.updateActivityRequest(new VolleyCallback() {
            @Override
            public void onSuccess(NetworkResponse response) {
                callback.getModelOnSuccess(ActivityService.booleanRespProcess(response, "Update activity "));
            }
        }, activity);
    }

    /**
     * uptate an new activity.
     * @param c Caller context
     * @param activityId The Id of the activity
     * @param callback
     */
    public static void deleteActivity(Context c, String activityId, final ActivityCallBack callback) {

        ActivityRequest request = new ActivityRequest(c);
        request.deleteActivityRequest(new VolleyCallback() {
            @Override
            public void onSuccess(NetworkResponse response) {
                callback.getModelOnSuccess(ActivityService.booleanRespProcess(response, "Update activity "));
            }
        }, activityId);
    }

    /**
     * uptate an new activity.
     * @param c Caller context
     * @param activityId The Id of the activity
     * @param callback
     */
    public static void joinActivity(Context c, String activityId, String creatorId, final ActivityCallBack callback) {

        ActivityRequest request = new ActivityRequest(c);
        request.joinActivityRequest(new VolleyCallback() {
            @Override
            public void onSuccess(NetworkResponse response) {
                callback.getModelOnSuccess(ActivityService.booleanRespProcess(response, "Update activity "));
            }
        }, activityId, creatorId);
    }
}
