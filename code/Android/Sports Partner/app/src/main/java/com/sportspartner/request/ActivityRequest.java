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
import com.google.gson.JsonObject;
import com.sportspartner.models.ActivityReview;
import com.sportspartner.models.ActivitySearch;
import com.sportspartner.models.FacilityReview;
import com.sportspartner.models.SActivity;
import com.sportspartner.models.UserReview;
import com.sportspartner.util.dbHelper.LoginDBHelper;
import com.sportspartner.util.NetworkResponseRequest;
import com.sportspartner.util.VolleyCallback;

import java.util.ArrayList;

/**
 * @author Xiaochen Li, Xuan Zhang
 */

public class ActivityRequest extends com.sportspartner.request.Request{
    /**
     * Constructor
     * @param c Caller context
     */
    public ActivityRequest(Context c){
        super(c);
    }

    /**
     * Send request for a group of activity outlines
     * @param callback
     * @param type either "past" or "upcoming" or "recommend" for different types of search
     * @param id the activity for whom
     * @param limit The maximum number of results to return.
     * @param offset The index of the first result to return.
     */
    public void activitiesOutlineRequest(final VolleyCallback callback, String type, String id, int limit, int offset) {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(contextf);
        String url = URL_CONTEXT+"v1/activity_"+type+"?id="+id+"&offset="+offset+"&limit="+limit;
        Log.d("activityOtlRequest", url);

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
                        Toast toast = Toast.makeText(context, "volley error: "+error.getMessage(), Toast.LENGTH_SHORT);
                        toast.show();
                    }
        }
        );
        queue.add(nrRequest);
    }

    /**
     * Send request for a group of activity outlines
     * @param callback
     * @param id the activity for whom
     * @param limit The maximum number of results to return.
     * @param offset The index of the first result to return.
     */
    public void recommendActivitiesOutlineRequest(final VolleyCallback callback,
                                                  String id, int limit, int offset,
                                                  double latitude, double longitude) {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(contextf);
        String url = URL_CONTEXT+"v1/activity_recommend"+"?id="+id+"&offset="+offset+"&limit="+limit+
                "&latitude="+latitude+"&longitude="+longitude;
        Log.d("activityOtlRequest", url);

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
                Toast toast = Toast.makeText(context, "volley error: "+error.getMessage(), Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        );
        queue.add(nrRequest);
    }


    /**
     * Send request to get the detail of an activity
     * @param callback
     * @param type either "full" for full activity or "outline" for activity outline
     * @param id the id for this activity
     */
    public void activityInfoRequest(final VolleyCallback callback, String type, String id) {
        //get key and email
        LoginDBHelper db = LoginDBHelper.getInstance(contextf);
        String userEmail = db.getEmail();
        String key = db.getKey();

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(contextf);
        String url = URL_CONTEXT+"v1/activity/"+id+"?content="+type+"&requestorId="+userEmail+"&requestorKey="+key;

        Log.d("activityInfoRequest", url);

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
                Toast toast = Toast.makeText(context, "volley error: "+error.getMessage(), Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        );
        queue.add(nrRequest);
    }

    /**
     * Send a request to create an activity
     * @param callback
     * @param activity the activity to create
     */
    public void createActivityRequest(final VolleyCallback callback, SActivity activity) {
        LoginDBHelper db = LoginDBHelper.getInstance(contextf);
        String userEmail = db.getEmail();
        String key = db.getKey();

        Gson gson = new Gson();
        String activityString = gson.toJson(activity);
        JsonObject activityJsObj = gson.fromJson(activityString, JsonObject.class);

        JsonObject jsonRequestObject = new JsonObject();

        jsonRequestObject.addProperty("userId", userEmail.trim().toLowerCase());
        jsonRequestObject.addProperty("key", key);
        jsonRequestObject.add("activity", activityJsObj);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(contextf);
        String url = URL_CONTEXT+"v1/activity";

        NetworkResponseRequest nrRequest = new NetworkResponseRequest(com.android.volley.Request.Method.POST, url,
                jsonRequestObject.toString(),
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Context context = contextf.getApplicationContext();
                Toast toast = Toast.makeText(context, "volley error: "+error.getMessage(), Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        );
        queue.add(nrRequest);
    }

    /**
     * send a request to update an activity
     * @param callback
     * @param activity the activity object
     */
    public void updateActivityRequest(final VolleyCallback callback, SActivity activity) {
        LoginDBHelper db = LoginDBHelper.getInstance(contextf);
        String userEmail = db.getEmail();
        String key = db.getKey();

        Gson gson = new Gson();
        String activityString = gson.toJson(activity);
        JsonObject activityJsObj = gson.fromJson(activityString, JsonObject.class);

        JsonObject jsonRequestObject = new JsonObject();
        jsonRequestObject.addProperty("requestorId", userEmail.trim().toLowerCase());
        jsonRequestObject.addProperty("requestorKey", key);
        jsonRequestObject.add("activity", activityJsObj);

        Log.d("json", jsonRequestObject.toString());

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(contextf);
        String url = URL_CONTEXT+"v1/activity/" + activity.getActivityId();
        Log.d("url update", url);

        NetworkResponseRequest nrRequest = new NetworkResponseRequest(com.android.volley.Request.Method.PUT, url,
                jsonRequestObject.toString(),
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Context context = contextf.getApplicationContext();
                Toast toast = Toast.makeText(context, "volley error: "+error.getMessage(), Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        );
        queue.add(nrRequest);
    }

    /**
     * send a request to delete the activity
     * @param callback
     * @param activityId the id of the activity needed to be deleted
     */
    //userId=userId&key=key&
    public void deleteActivityRequest(final VolleyCallback callback, String activityId) {
        LoginDBHelper db = LoginDBHelper.getInstance(contextf);
        String userEmail = db.getEmail();
        String key = db.getKey();

        Log.d("key",String.valueOf(key));

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(contextf);
        String url = URL_CONTEXT+"v1/activity/"+activityId+"/"+userEmail+"/"+key;
        Log.d("deleteActivityRequest",url);

        NetworkResponseRequest nrRequest = new NetworkResponseRequest(com.android.volley.Request.Method.DELETE, url, null,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Context context = contextf.getApplicationContext();
                Toast toast = Toast.makeText(context, "volley error: "+error.getMessage(), Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        );
        queue.add(nrRequest);
    }

    /**
     * send a request to join the activity
     * @param callback
     * @param activityId the id of the activity needed to be deleted
     * @param creatorId the id of the creator of this activity
     */
    public void joinActivityRequest(final VolleyCallback callback, String activityId, String creatorId) {
        LoginDBHelper db = LoginDBHelper.getInstance(contextf);
        String userEmail = db.getEmail();

        JsonObject jsonRequestObject = new JsonObject();

        jsonRequestObject.addProperty("creatorId", creatorId);
        jsonRequestObject.addProperty("senderId", userEmail.trim().toLowerCase());

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(contextf);
        String url = URL_CONTEXT+"v1/joinactivityapplication/" + activityId;

        NetworkResponseRequest nrRequest = new NetworkResponseRequest(com.android.volley.Request.Method.POST, url,
                jsonRequestObject.toString(),
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Context context = contextf.getApplicationContext();
                Toast toast = Toast.makeText(context, "volley error: "+error.getMessage(), Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        );
        queue.add(nrRequest);
    }

    /**
     * accept an application to join an activity
     * @param callback
     * @param activityId The UUID of the activity.
     * @param userId The userId of the user who sent the application to join the activity.
     */
    public void acceptJoinActivityVolleyRequest(final VolleyCallback callback, String activityId, String userId ){
        LoginDBHelper dbHelper = LoginDBHelper.getInstance(contextf);
        String creatorId = dbHelper.getEmail();
        String creatorKey = dbHelper.getKey();

        JsonObject jsonRequestObject = new JsonObject();

        jsonRequestObject.addProperty("creatorId", creatorId.trim().toLowerCase());
        jsonRequestObject.addProperty("creatorKey", creatorKey);
        jsonRequestObject.addProperty("userId", userId);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(contextf);
        String url = URL_CONTEXT+"v1/acceptjoinactivityapplication/"+activityId;
        NetworkResponseRequest nrRequest = new NetworkResponseRequest(com.android.volley.Request.Method.POST, url,
                jsonRequestObject.toString(),
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
     * decline an application to join an activity
     * @param callback
     * @param activityId The UUID of the activity.
     * @param userId The userId of the user who sent the activity joining application.
     */
    public void declineJoinActivityVolleyRequest(final VolleyCallback callback, String activityId, String userId){
        LoginDBHelper dbHelper = LoginDBHelper.getInstance(contextf);
        String creatorId = dbHelper.getEmail();
        String creatorKey = dbHelper.getKey();

        JsonObject jsonRequestObject = new JsonObject();

        jsonRequestObject.addProperty("creatorId", creatorId.trim().toLowerCase());
        jsonRequestObject.addProperty("creatorKey", creatorKey);
        jsonRequestObject.addProperty("userId", userId);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(contextf);
        String url = URL_CONTEXT+"v1/declinejoinactivityapplication/"+activityId;
        NetworkResponseRequest nrRequest = new NetworkResponseRequest(com.android.volley.Request.Method.POST, url,
                jsonRequestObject.toString(),
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
     * Send a request to leave an activity.
     * @param callback
     * @param activityId The UUID of the activity.
     */
    public void leaveActivityRequest(final VolleyCallback callback, String activityId) {
        LoginDBHelper db = LoginDBHelper.getInstance(contextf);
        String userId = db.getEmail();
        String key = db.getKey();

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(contextf);
        String url = URL_CONTEXT+"v1/activity_leave/" + activityId + "?userId="+userId+"&key="+key;

        NetworkResponseRequest nrRequest = new NetworkResponseRequest(com.android.volley.Request.Method.DELETE, url,
                null,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Context context = contextf.getApplicationContext();
                Toast toast = Toast.makeText(context, "volley error: "+error.getMessage(), Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        );
        queue.add(nrRequest);
    }
    /**
     * Send a request to review members and facility of an activity
     * @param callback
     */
    public void reviewRequest(final VolleyCallback callback, String activityId, ArrayList<UserReview> userReviews, FacilityReview facilityReview) {
        LoginDBHelper db = LoginDBHelper.getInstance(contextf);
        String userEmail = db.getEmail();
        String key = db.getKey();

        ActivityReview review = new ActivityReview();
        review.setKey(key);
        review.setUserId(userEmail);
        review.setFacilityreview(facilityReview);
        review.setUserreviews(userReviews);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(contextf);
        String url = URL_CONTEXT+"v1/activityreview/"+activityId;
        Log.d("ActivityRequestURL", url);
        Log.d("ActivityRequest", "POST "+new Gson().toJson(review));
        NetworkResponseRequest nrRequest = new NetworkResponseRequest(com.android.volley.Request.Method.POST, url,
                new Gson().toJson(review),
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Context context = contextf.getApplicationContext();
                Toast toast = Toast.makeText(context, "volley error: "+error.getMessage(), Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        );
        queue.add(nrRequest);
    }

    //POST https://api.sportspartner.com/v1/search?type=activity&limit=number&offset=number
    /**
     * Send a request to review members and facility of an activity
     * @param callback
     */
    public void searchRequest(final VolleyCallback callback, ActivitySearch activitySearch, int limit, int offset) {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(contextf);
        String url = URL_CONTEXT+"v1/search?type=activity"+"&limit="+limit+"&offset="+offset;
        NetworkResponseRequest nrRequest = new NetworkResponseRequest(com.android.volley.Request.Method.POST, url,
                new Gson().toJson(activitySearch),
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Context context = contextf.getApplicationContext();
                Toast toast = Toast.makeText(context, "volley error: "+error.getMessage(), Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        );
        queue.add(nrRequest);
    }
}
