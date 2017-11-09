package com.sportspartner.request;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.sportspartner.util.BitmapHelper;
import com.sportspartner.util.LoginDBHelper;
import com.sportspartner.util.NetworkResponseRequest;
import com.sportspartner.util.VolleyCallback;

/**
 * @author Xiaochen Li
 */

public class ResourceRequest extends Request {
    /**
     * Constructor
     * @param c
     */
    public ResourceRequest(Context c) {super(c);}

    /**
     * Send a request for an image.
     * @param callback
     * @param iconUUID The UUID of the icon.
     * @param type The type of the requested image. 'small' for the small version of the icon, 'origin' for the original icon image.
     */
    public void imageRequest(final VolleyCallback callback, String iconUUID, String type) {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(contextf);
        String url = URL_CONTEXT+"v1/resource/"+iconUUID+"?type="+type;
        Log.d("imageRequest",url);
        NetworkResponseRequest nrRequest = new NetworkResponseRequest(com.android.volley.Request.Method.GET, url, null,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Context context = contextf.getApplicationContext();
//                Toast toast = Toast.makeText(context, "volley error: "+error.getMessage(), Toast.LENGTH_LONG);
//                toast.show();
            }
        }
        );
        queue.add(nrRequest);
    }

    /**
     * Upload the user icon.
     * @param callback
     * @param bitmapString The bitmap string of the icon.
     */
    public void imageUploadRequest(final VolleyCallback callback, String bitmapString) {

        LoginDBHelper dbHelper = LoginDBHelper.getInstance(contextf);
        String email = dbHelper.getEmail();
        String key = dbHelper.getKey();

        JsonObject jsonRequestObject = new JsonObject();

        jsonRequestObject.addProperty("userId", email);
        jsonRequestObject.addProperty("key", key);
        jsonRequestObject.addProperty("object", "USER");
        jsonRequestObject.addProperty("image", bitmapString);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(contextf);
        String url = URL_CONTEXT+"v1/resource/icon/"+email;
        NetworkResponseRequest nrRequest = new NetworkResponseRequest(com.android.volley.Request.Method.POST, url, jsonRequestObject.toString(),
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Context context = contextf.getApplicationContext();
//                Toast toast = Toast.makeText(context, "volley error: "+error.getMessage(), Toast.LENGTH_LONG);
//                toast.show();
            }
        }
        );
        queue.add(nrRequest);
    }

    /**
     * Send a request for a list of all sports.
     * @param callback Caller context.
     */
    public void allSportsRequest(final VolleyCallback callback) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(contextf);
        String url = URL_CONTEXT+"v1/sports";

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
