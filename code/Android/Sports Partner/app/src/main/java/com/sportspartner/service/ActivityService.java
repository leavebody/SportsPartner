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
 * Created by xc on 10/23/17.
 */

public class ActivityService extends Service {
    public static void getUpcomingActivities(Context c, String email, int limit, int offset, final ActivityCallBack callback) {

        ActivityRequest request = new ActivityRequest(c);
        request.activitiesOutlineRequest(new VolleyCallback() {
            @Override
            public void onSuccess(NetworkResponse response) {
                callback.getModelOnSuccess(ActivityService.getActivitiesOutlineRespProcess(response));
            }
        }, "upcoming", email, limit, offset);
    }

    public static void getHistoryActivities(Context c, String email, int limit, int offset, final ActivityCallBack callback) {

        ActivityRequest request = new ActivityRequest(c);
        request.activitiesOutlineRequest(new VolleyCallback() {
            @Override
            public void onSuccess(NetworkResponse response) {
                callback.getModelOnSuccess(ActivityService.getActivitiesOutlineRespProcess(response));
            }
        }, "past", email, limit, offset);
    }

    public static void getRecommendActivities(Context c, String email, int limit, int offset, final ActivityCallBack callback) {
        //TODO cancel comment
        /*ActivityRequest request = new ActivityRequest(c);
        request.activitiesOutlineRequest(new VolleyCallback() {
            @Override
            public void onSuccess(NetworkResponse response) {
                callback.getModelOnSuccess(ActivityService.getActivitiesOutlineRespProcess(response));
            }
        }, "recommend", email, limit, offset);*/
        //TODO delete test data
        ModelResult<ArrayList<SActivityOutline>> result = new ModelResult<>();
        result.setStatus(true);
        ArrayList<SActivityOutline> activityList = new ArrayList<SActivityOutline>();
        Date date = new Date(20, 10,23);
        activityList.add(new SActivityOutline("01","Open","statuts", "None","Basketball",date, date,"location", 3, 10));
        activityList.add(new SActivityOutline("02","Open","statuts", "None","Football",date, date,"location", 10, 12));
        activityList.add(new SActivityOutline("03","Open","statuts", "None","Lacrosse",date, date,"location", 3, 10));
        //activityList.add(new SActivityOutline("04","Open","statuts", "None","Badminton",date, date,"location", 4, 4));
        result.setModel(activityList);
        callback.getModelOnSuccess(result);
    }

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

    public static void getSActivity(Context c, String id, final ActivityCallBack callback) {

        ActivityRequest request = new ActivityRequest(c);
        request.activityInfoRequest(new VolleyCallback() {
            @Override
            public void onSuccess(NetworkResponse response) {
                callback.getModelOnSuccess(ActivityService.getActivityRespProcess(response));
            }
        }, "full", id);
    }

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

    public static void getSActivityOutline(Context c, String id, final ActivityCallBack callback) {

        ActivityRequest request = new ActivityRequest(c);
        request.activityInfoRequest(new VolleyCallback() {
            @Override
            public void onSuccess(NetworkResponse response) {
                callback.getModelOnSuccess(ActivityService.getActivityOutlineRespProcess(response));
            }
        }, "outline", id);
    }

    public static ModelResult<SActivityOutline> getActivityOutlineRespProcess(NetworkResponse response){
        ModelResult<SActivityOutline> result = new ModelResult<>();
        switch (response.statusCode){
            case 200:
                boolean status = false;

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

    public static void createActivity(Context c, SActivity activity, final ActivityCallBack callback) {

        ActivityRequest request = new ActivityRequest(c);
        request.createActivityRequest(new VolleyCallback() {
            @Override
            public void onSuccess(NetworkResponse response) {
                callback.getModelOnSuccess(ActivityService.createActivityRespProcess(response));
            }
        }, activity);
    }

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
