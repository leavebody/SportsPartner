package com.sportspartner.request;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sportspartner.models.Profile;
import com.sportspartner.util.LoginDBHelper;
import com.sportspartner.util.NetworkResponseRequest;
import com.sportspartner.util.VolleyCallback;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Xiaochen Li
 */

public class ProfileRequest extends Request {
    /**
     * Constructor.
     * @param c Caller context
     */
    public ProfileRequest(Context c){
        super(c);
    }

    /**
     * Send a request to get the profile of a user.
     * @param callback
     * @param email The email of the user
     */
    public void profileInfoVolleyRequest(final VolleyCallback callback, String email) {
        LoginDBHelper dbHelper = LoginDBHelper.getInstance(contextf);
        String userId = dbHelper.getEmail();
        String key = dbHelper.getKey();

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(contextf);
        String url = URL_CONTEXT+"v1/profile/"+email+"?"+"requestorId="+userId+"&requestorKey="+key;
        Log.d("ProfileRequest",url);
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

    /**
     * Send a request to get the outline of a user.
     * @param callback
     * @param email The email of the user.
     */
    public void userOutlineVolleyRequest(final VolleyCallback callback, String email) {


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(contextf);
        String url = URL_CONTEXT+"v1/profile/outline/"+email;

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
     * Send a request to get the profile comments of a user.
     * @param callback
     * @param email The email of the user.
     */
    public void profileCommentVolleyRequest(final VolleyCallback callback, String email) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(contextf);
        String url = URL_CONTEXT+"v1/profile_comments/"+email;

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

    /**
     * Send a request to update the profile of a user.
     * @param callback
     * @param profile The updated profile.
     * @param email The email of the user.
     */
    public void updateProfileVolleyRequest(final VolleyCallback callback, Profile profile, String email) {

        LoginDBHelper db = LoginDBHelper.getInstance(contextf);
        String userEmail = db.getEmail();
        String key = db.getKey();

        Gson gson = new Gson();
        String profileString = gson.toJson(profile);
        JsonObject profileJsObj = gson.fromJson(profileString, JsonObject.class);

        JsonObject jsonRequestObject = new JsonObject();

        jsonRequestObject.addProperty("userId", userEmail.trim().toLowerCase());
        jsonRequestObject.addProperty("key", key);
        jsonRequestObject.add("profile", profileJsObj);


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(contextf);
        String url = URL_CONTEXT+"v1/profile/"+email;
        NetworkResponseRequest nrRequest = new NetworkResponseRequest(com.android.volley.Request.Method.PUT, url, jsonRequestObject.toString(),
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

    /**
     * Send a request to get the interests of a user.
     * @param callback
     * @param email The email of the user.
     */
    public void interestsVolleyRequest(final VolleyCallback callback, String email) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(contextf);
        String url = URL_CONTEXT+"v1/interests/"+email;
        Log.d("ProfileRequest","GET "+url);
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

    /**
     * Send a request to update the interests of a user.
     * @param callback
     * @param interests a string for all sportId separated by ','
     * @param email the email of the person whose interests are being updated
     */
    public void updateInterestsVolleyRequest(final VolleyCallback callback, String interests, String email) {

        LoginDBHelper db = LoginDBHelper.getInstance(contextf);
        String userEmail = db.getEmail();
        String key = db.getKey();

        JsonObject jsonRequestObject = new JsonObject();

        jsonRequestObject.addProperty("email", userEmail.trim().toLowerCase());
        jsonRequestObject.addProperty("key", key);
        jsonRequestObject.addProperty("interests", interests);


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(contextf);
        String url = URL_CONTEXT+"v1/interests/"+email;

        NetworkResponseRequest nrRequest = new NetworkResponseRequest(com.android.volley.Request.Method.PUT, url, jsonRequestObject.toString(),
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
