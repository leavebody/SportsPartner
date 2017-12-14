package com.sportspartner.request;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.sportspartner.util.DBHelper.LoginDBHelper;
import com.sportspartner.util.NetworkResponseRequest;
import com.sportspartner.util.VolleyCallback;

/**
 * Created by yujiaxiao on 11/10/17.
 */

public class FriendRequest extends Request{
    /**
     * Constructor.
     * @param c Caller context
     */
    public FriendRequest(Context c){
        super(c);
    }

    /**
     * Send a request to get the friendList of a user.
     * @param callback
     */
    public void friendListVolleyRequest(final VolleyCallback callback){
        LoginDBHelper dbHelper = LoginDBHelper.getInstance(contextf);
        String userId = dbHelper.getEmail();

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(contextf);
        String url = URL_CONTEXT+"v1/"+userId+"/friends";
        Log.d("friendListVolleyRequest", url);
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
     * Send a request to send the friend request
     * @param callback
     * @param receiverId The email of the reciver.
     * @param senderId The email of the sender.
     */
    public void sendFriendRequestVolleyRequest(final VolleyCallback callback, String receiverId, String senderId ){
        LoginDBHelper dbHelper = LoginDBHelper.getInstance(contextf);
        String userId = dbHelper.getEmail();

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(contextf);
        String url = URL_CONTEXT+"v1/friendrequest/"+receiverId+"/"+senderId;
        NetworkResponseRequest nrRequest = new NetworkResponseRequest(com.android.volley.Request.Method.POST, url, null,
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
     * accept a request to send the friend request
     * @param callback
     * @param receiverId The email of the receiver.
     * @param senderId The email of the sender.
     */
    public void acceptFriendRequestVolleyRequest(final VolleyCallback callback, String receiverId, String senderId ){
        LoginDBHelper dbHelper = LoginDBHelper.getInstance(contextf);
        String userId = dbHelper.getEmail();

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(contextf);
        String url = URL_CONTEXT+"v1/acceptfriendrequest/"+receiverId+"/"+senderId;
        NetworkResponseRequest nrRequest = new NetworkResponseRequest(com.android.volley.Request.Method.POST, url, null,
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
     * decline a request to send the friend request
     * @param callback
     * @param receiverId The email of the receiver.
     * @param senderId The email of the sender.
     */
    public void declineFriendRequestVolleyRequest(final VolleyCallback callback, String receiverId, String senderId ){
        LoginDBHelper dbHelper = LoginDBHelper.getInstance(contextf);
        String userId = dbHelper.getEmail();

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(contextf);
        String url = URL_CONTEXT+"v1/declinefriendrequest/"+receiverId+"/"+senderId;
        NetworkResponseRequest nrRequest = new NetworkResponseRequest(com.android.volley.Request.Method.POST, url, null,
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

    //DELETE https://api.sportspartner.com/v1/deletefriend/:userId1/:userId2
    /**
     * Send a delete the friend request
     * @param callback
     * @param receiverId The email of the deleted friend.
     * @param senderId The email of the sender.
     */
    public void deteleFriendVolleyRequest(final VolleyCallback callback, String receiverId, String senderId ){
        LoginDBHelper dbHelper = LoginDBHelper.getInstance(contextf);
        String userId = dbHelper.getEmail();

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(contextf);
        String url = URL_CONTEXT+"v1/deletefriend/"+senderId+"/"+receiverId;
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
                Toast toast = Toast.makeText(context, "volley error: "+error.getMessage(), Toast.LENGTH_LONG);
                toast.show();
            }
        }
        );
        queue.add(nrRequest);
    }

    public void deteleFriendChannelVolleyRequest(String url ){

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(contextf);
        NetworkResponseRequest nrRequest = new NetworkResponseRequest(com.android.volley.Request.Method.DELETE, url, null,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {

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
