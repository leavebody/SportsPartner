package com.sportspartner.request;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.sportspartner.util.NetworkResponseRequest;
import com.sportspartner.util.VolleyCallback;

/**
 * @author Xiaochen Li
 */

public class FacilityRequest extends Request {

    /**
     * Constructor
     * @param c Caller context
     */
    public FacilityRequest(Context c){
        super(c);
    }

    /**
     * Send a request for a list of nearby facilities.
     * @param callback Caller context.
     */
    public void nearbyFacilitiesRequest(LatLngBounds bounds, final VolleyCallback callback) {
        nearbyFacilitiesRequest(bounds.northeast, bounds.southwest, callback);
    }

    public void nearbyFacilitiesRequest(LatLng northeast, LatLng southwest, final VolleyCallback callback) {
        nearbyFacilitiesRequest(southwest.latitude, northeast.latitude, southwest.longitude, northeast.longitude, callback);
    }

    /**
     *
     * @param las The down most latitude of the region.
     * @param lal The up most latitude of the region.
     * @param los The left most longitude of the region.
     * @param lol The right most longitude of the region.
     * @param callback
     */
    public void nearbyFacilitiesRequest(double las, double lal, double los, double lol, final VolleyCallback callback){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(contextf);
        String url = URL_CONTEXT+String.format("v1/facility_markers?las=%s&lal=%s&los=%s&lol=%s", las, lal, los, lol);
        Log.d("FacilityRequest", url);
        NetworkResponseRequest nrRequest = new NetworkResponseRequest(com.android.volley.Request.Method.GET, url, null,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Context context = contextf.getApplicationContext();
                Toast toast = Toast.makeText(context, "volley error: "+error.getMessage(), Toast.LENGTH_LONG);
                toast.show();
            }
        }
        );
        queue.add(nrRequest);
    }
}
