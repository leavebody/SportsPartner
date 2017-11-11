package com.sportspartner.service;

import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.sportspartner.models.FacilityMarker;
import com.sportspartner.request.FacilityRequest;
import com.sportspartner.request.ResourceRequest;
import com.sportspartner.service.serviceresult.ModelResult;
import com.sportspartner.util.ActivityCallBack;
import com.sportspartner.util.NetworkResponseRequest;
import com.sportspartner.util.VolleyCallback;

import java.util.ArrayList;

/**
 * @author Xiaochen Li
 */

public class FacilityService extends Service {

    /**
     * Get an ArrayList of the marker info of all facilities in the APP.
     *
     * @param c        Caller context.
     * @param callback
     */
    public static void getAllFacilityMarkers(final Context c, final ActivityCallBack callback) {

        FacilityRequest request = new FacilityRequest(c);
        request.allFacilitiesRequest(new VolleyCallback() {
            @Override
            public void onSuccess(NetworkResponse response) {
                callback.getModelOnSuccess(getAllFacilityMarkersRespProcess(response));
            }
        });

    }

    /**
     * The helper method to process the result of get all facility markers request.
     *
     * @param response The network response to process
     * @return A ModelResult with model type ArrayList<FacilityMarker>,
     * which is the requested sports data.
     */
    private static ModelResult<ArrayList<FacilityMarker>> getAllFacilityMarkersRespProcess(NetworkResponse response) {
        ModelResult<ArrayList<FacilityMarker>> result = new ModelResult();
        switch (response.statusCode) {
            case 200:
                boolean status;
                JsonObject jsResp = NetworkResponseRequest.parseToJsonObject(response);
                status = (jsResp.get("response").getAsString().equals("true"));
                result.setStatus(status);
                if (status) {
                    Gson gson = new Gson();
                    JsonArray jsArrayMarker = jsResp.getAsJsonArray("facilities");
                    ArrayList<FacilityMarker> arrayMarker = gson.fromJson(jsArrayMarker,
                            new TypeToken<ArrayList<FacilityMarker>>() {
                            }.getType());
                    result.setModel(arrayMarker);
                } else {
                    result.setMessage("get all facility markers request failed: " + jsResp.get("message").getAsString());
                }
                break;
            default:
                result.setMessage("bad response:" + response.statusCode);
        }
        return result;
    }

}
