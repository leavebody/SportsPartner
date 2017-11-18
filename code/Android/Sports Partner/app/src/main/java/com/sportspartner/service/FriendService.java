package com.sportspartner.service;

import android.content.Context;

import com.android.volley.NetworkResponse;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.sportspartner.models.UserOutline;
import com.sportspartner.request.FriendRequest;
import com.sportspartner.util.NetworkResponseRequest;
import com.sportspartner.util.VolleyCallback;

import java.util.ArrayList;

/**
 * @author Yujia Xiao
 * @author Xiaochen Li
 */

public class FriendService extends Service{
    /**
     * Get the friendList of an user.
     * @param c Caller context.
     * @param callback
     */
    public static void getFriendList(Context c, final ActivityCallBack callback) {
        FriendRequest request = new FriendRequest(c);
        request.friendListVolleyRequest(new VolleyCallback() {
            @Override
            public void onSuccess(NetworkResponse response) {
                callback.getModelOnSuccess(FriendService.friendListRespProcess(response));
            }
        });

    }
    /**
     * The helper method to process the result of get friendList request.
     * @param response The network response to process
     * @return A ModelResult with model type UserOutline, which is the requested profile
     */
    private static ModelResult<ArrayList<UserOutline>> friendListRespProcess(NetworkResponse response) {
        ModelResult<ArrayList<UserOutline>> result = new ModelResult();
        switch (response.statusCode){
            case 200:
                boolean status = false;

                JsonObject jsResp = NetworkResponseRequest.parseToJsonObject(response);
                status = (jsResp.get("response").getAsString().equals("true"));
                result.setStatus(status);
                if(status) {
                    JsonArray comment = jsResp.getAsJsonArray("friendlist");
                    Gson gson = new Gson();

                    ArrayList<UserOutline> al = gson.fromJson(comment, new TypeToken<ArrayList<UserOutline>>(){}.getType());
                    result.setModel(al);

                } else {
                    result.setMessage("get friend list failed: "+jsResp.get("message").getAsString());
                }

                break;

            default:
                result.setMessage("bad response:"+response.statusCode);
        }
        return result;
    }

    /**
     * send the friend request.
     * @param c Caller context.
     * @param receiverId The email of the receiver.
     * @param senderId The email of the sender
     * @param callback
     */
    public static void sendFriendRequest(Context c, String receiverId, String senderId, final ActivityCallBack callback) {
        FriendRequest request = new FriendRequest(c);
        request.sendFriendRequestVolleyRequest(new VolleyCallback() {
            @Override
            public void onSuccess(NetworkResponse response) {
                callback.getModelOnSuccess(FriendService.booleanRespProcess(response, "send friend request "));
            }
        },receiverId, senderId);

    }

    /**
     * accept the friend request.
     * @param c Caller context.
     * @param receiverId The email of the receiver.
     * @param senderId The email of the sender
     * @param callback
     */
    public static void acceptFriendRequest(Context c, String receiverId, String senderId, final ActivityCallBack callback) {
        FriendRequest request = new FriendRequest(c);
        request.acceptFriendRequestVolleyRequest(new VolleyCallback() {
            @Override
            public void onSuccess(NetworkResponse response) {
                callback.getModelOnSuccess(FriendService.booleanRespProcess(response, "accept friend request "));
            }
        },receiverId, senderId);

    }

    /**
     * decline the friend request.
     * @param c Caller context.
     * @param receiverId The email of the receiver.
     * @param senderId The email of the sender
     * @param callback
     */
    public static void declineFriendRequest(Context c, String receiverId, String senderId, final ActivityCallBack callback) {
        FriendRequest request = new FriendRequest(c);
        request.declineFriendRequestVolleyRequest(new VolleyCallback() {
            @Override
            public void onSuccess(NetworkResponse response) {
                callback.getModelOnSuccess(FriendService.booleanRespProcess(response, "Decline friend request "));
            }
        },receiverId, senderId);

    }

    /**
     * delete the friend.
     * @param c Caller context.
     * @param receiverId The email of the receiver.
     * @param senderId The email of the sender
     * @param callback
     */
    public static void deleteFriend(Context c, String receiverId, String senderId, final ActivityCallBack callback) {
        FriendRequest request = new FriendRequest(c);
        request.deteleFriendVolleyRequest(new VolleyCallback() {
            @Override
            public void onSuccess(NetworkResponse response) {
                callback.getModelOnSuccess(FriendService.booleanRespProcess(response, "Delete friend "));
            }
        },receiverId, senderId);

    }

}
