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
import com.sportspartner.service.serviceresult.ModelResult;
import com.sportspartner.util.ActivityCallBack;
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
        //TODO cancel comment
        /*ActivityRequest request = new ActivityRequest(c);
        request.activitiesOutlineRequest(new VolleyCallback() {
            @Override
            public void getBooleanOnSuccess(NetworkResponse response) {
                callback.getModelOnSuccess(ActivityService.getActivitiesOutlineRespProcess(response));
            }
        }, "recommend", email, limit, offset);*/
        //TODO delete test data
        ModelResult<ArrayList<SActivityOutline>> result = new ModelResult<>();
        result.setStatus(true);
        ArrayList<SActivityOutline> activityList = new ArrayList<SActivityOutline>();
        Date date1 = new Date(117, 10,23,10,30);
        Date date2 = new Date(117,10,23,12,00);
        activityList.add(new SActivityOutline("01","Open","statuts", "None","Basketball",date1, date2,"location", 10, 3));
        activityList.add(new SActivityOutline("02","Open","statuts", "None","Football",date1, date2,"location", 12, 10));
        activityList.add(new SActivityOutline("03","Open","statuts", "None","Lacrosse",date1, date2,"location", 10, 3));
        //activityList.add(new SActivityOutline("04","Open","statuts", "None","Badminton",date, date,"location", 4, 4));
        result.setModel(activityList);
        callback.getModelOnSuccess(result);
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
                    result.setModel(jsResp.get("id").getAsString());
                } else {
                    result.setMessage("create activity request failed: "+jsResp.get("message").getAsString());
                }

                break;

            default:
                result.setMessage("bad response:"+response.statusCode);
        }
        return result;
    }
}
