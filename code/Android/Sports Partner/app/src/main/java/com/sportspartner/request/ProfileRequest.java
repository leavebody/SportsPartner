package com.sportspartner.request;

import android.content.Context;
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
 * Created by xc on 10/24/17.
 */

public class ProfileRequest extends Request {
    public ProfileRequest(Context c){
        super(c);
    }

    public void profileInfoVolleyRequest(final VolleyCallback callback, String email) {


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(contextf);
        String url = URL_CONTEXT+"v1/profile/"+email;

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
                Context context = contextf.getApplicationContext();
                Toast toast = Toast.makeText(context, "volley error: "+error.getMessage(), Toast.LENGTH_LONG);
                toast.show();
            }
        }
        );
        queue.add(nrRequest);
    }

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


    public void interestsVolleyRequest(final VolleyCallback callback, String email) {


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(contextf);
        String url = URL_CONTEXT+"v1/interests/"+email;

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
    /*
    * @param interests a string for all sportId separated by ','
    *       email the email of the person whose interests are being updated
    * */
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
