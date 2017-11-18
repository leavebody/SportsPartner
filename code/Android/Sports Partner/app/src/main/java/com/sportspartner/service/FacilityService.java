package com.sportspartner.service;

import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.sportspartner.models.FacilityMarker;
import com.sportspartner.request.FacilityRequest;
import com.sportspartner.util.NetworkResponseRequest;
import com.sportspartner.util.VolleyCallback;

import java.util.ArrayList;

/**
 * @author Xiaochen Li
 */

public class FacilityService extends Service {

    /**
     * Get an ArrayList of the marker info in the requested range.
     *
     * @param c        Caller context.
     * @param callback
     */
    public static void getFacilityMarkers(final Context c, LatLngBounds bounds, final ActivityCallBack callback) {

        FacilityRequest request = new FacilityRequest(c);
        request.nearbyFacilitiesRequest(bounds, new VolleyCallback() {
            @Override
            public void onSuccess(NetworkResponse response) {
                callback.getModelOnSuccess(getFacilityMarkersRespProcess(response));
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
    private static ModelResult<ArrayList<FacilityMarker>> getFacilityMarkersRespProcess(NetworkResponse response) {
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
                    result.setMessage("get facility markers request failed: " + jsResp.get("message").getAsString());
                }
                break;
            default:
                result.setMessage("bad response:" + response.statusCode);
        }
        return result;
    }

}
