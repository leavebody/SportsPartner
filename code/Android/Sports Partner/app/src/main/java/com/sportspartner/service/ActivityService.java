package com.sportspartner.service;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.sportspartner.models.ActivitySearch;
import com.sportspartner.models.FacilityReview;
import com.sportspartner.models.SActivity;
import com.sportspartner.models.SActivityOutline;
import com.sportspartner.models.UserReview;
import com.sportspartner.request.ActivityRequest;
import com.sportspartner.util.NetworkResponseRequest;
import com.sportspartner.util.VolleyCallback;


import java.util.ArrayList;

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
    public static final double DEFAULT_LATITUDE = 38.567;
    public static final double DEFAULT_LONGITUDE = 76.312;

    public static void getUpcomingActivities(Context c, String email, int limit, int offset, final ActivityCallBack<ArrayList<SActivityOutline>> callback) {

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
    public static void getHistoryActivities(Context c, String email, int limit, int offset, final ActivityCallBack<ArrayList<SActivityOutline>> callback) {

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
    public static void getRecommendActivities(final Activity c, final String email, final int limit, final int offset, final ActivityCallBack<ArrayList<SActivityOutline>> callback) {
        final ActivityRequest request = new ActivityRequest(c);
        ResourceService.getDeviceLocation(c, new ActivityCallBack<Location>() {
            @Override
            public void getModelOnSuccess(ModelResult<Location> modelResult) {
                double latitude;
                double longitude;
                if(modelResult.isStatus()){
                    latitude = modelResult.getModel().getLatitude();
                    longitude = modelResult.getModel().getLongitude();
                } else {
                    Toast.makeText(c.getApplicationContext(), "get device location failed, using default", Toast.LENGTH_LONG).show();
                    latitude = ActivityService.DEFAULT_LATITUDE;
                    longitude = ActivityService.DEFAULT_LONGITUDE;
                }
                request.recommendActivitiesOutlineRequest(new VolleyCallback() {
                    @Override
                    public void onSuccess(NetworkResponse response) {
                        callback.getModelOnSuccess(ActivityService.getActivitiesOutlineRespProcess(response));
                    }
                }, email, limit, offset, latitude, longitude);
            }
        });

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
                callback.getModelOnSuccess(ActivityService.booleanRespProcess(response, "Delete activity "));
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
                callback.getModelOnSuccess(ActivityService.booleanRespProcess(response, "Join activity "));
            }
        }, activityId, creatorId);
    }

    /**
     * leave an activity.
     * @param c Caller context
     * @param activityId The UUID of the activity.
     * @param callBack
     */
    public static void leaveActivity(Context c, String activityId, final ActivityCallBack callBack){

        ActivityRequest request = new ActivityRequest(c);
        request.leaveActivityRequest(new VolleyCallback() {
            @Override
            public void onSuccess(NetworkResponse response){
                callBack.getModelOnSuccess(ActivityService.booleanRespProcess(response, "Leave activity"));
            }
        }, activityId);
    }

    /**
     * review an activity.
     * @param c Caller context
     * @param callback
     */
    public static void reviewActivity(Context c, String activityId, ArrayList<UserReview> userReviews, FacilityReview facilityReview, final ActivityCallBack callback) {

        ActivityRequest request = new ActivityRequest(c);
        request.reviewRequest(new VolleyCallback() {
            @Override
            public void onSuccess(NetworkResponse response) {
                callback.getModelOnSuccess(ActivityService.booleanRespProcess(response, "Review activity "));
            }
        }, activityId, userReviews, facilityReview);
    }

    /**
     * Search activities
     * @param c
     * @param activitySearch the object of ActivitySearch
     * @param limit the max number of the result of activities
     * @param offset return activities whose index of the list of the search result is larger than the offset
     * @param callback
     */
    public static void searchActivity(Context c, ActivitySearch activitySearch, int limit, int offset, final ActivityCallBack callback) {
        ActivityRequest request = new ActivityRequest(c);
        request.searchRequest(new VolleyCallback() {
            @Override
            public void onSuccess(NetworkResponse response) {
                callback.getModelOnSuccess(ActivityService.searchActivityRespProcess(response));
            }
        }, activitySearch, limit, offset);
    }

    /**
     * The helper method to process the result of search activity outline request.
     * @param response The network response to process
     * @return A ModelResult with model type StArrayList<ActivitySearch>, which is the list of the activities
     */
    public static ModelResult<ArrayList<SActivityOutline>> searchActivityRespProcess(NetworkResponse response){
        ModelResult<ArrayList<SActivityOutline>> result = new ModelResult<>();
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
                    result.setMessage("search activity request failed: "+jsResp.get("message").getAsString());
                }
                break;

            default:
                result.setMessage("bad response:"+response.statusCode);
        }
        return result;
    }

}
