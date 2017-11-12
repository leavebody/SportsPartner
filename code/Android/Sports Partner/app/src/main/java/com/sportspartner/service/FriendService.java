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
                callback.getModelOnSuccess(FriendService.sendFriendRequestRespProcess(response));
            }
        },receiverId, senderId);

    }
    /**
     * The helper method to process the result of send friend request request.
     * @param response The network response to process
     * @return A BooleanResult
     * */
    private static ModelResult sendFriendRequestRespProcess(NetworkResponse response) {
        ModelResult result = new ModelResult();
        switch (response.statusCode){
            case 200:
                boolean status = false;
                JsonObject jsResp = NetworkResponseRequest.parseToJsonObject(response);
                status = (jsResp.get("response").getAsString().equals("true"));
                result.setStatus(status);
                if(!status) {
                    result.setMessage("send friend request failed: "+jsResp.get("message").getAsString());
                }

                break;

            default:
                result.setMessage("bad response:"+response.statusCode);
        }
        return result;
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
        request.sendFriendRequestVolleyRequest(new VolleyCallback() {
            @Override
            public void onSuccess(NetworkResponse response) {
                callback.getModelOnSuccess(FriendService.acceptFriendRequestRespProcess(response));
            }
        },receiverId, senderId);

    }
    /**
     * The helper method to process the result of accept friend request request.
     * @param response The network response to process
     * @return A BooleanResult
     * */
    private static ModelResult acceptFriendRequestRespProcess(NetworkResponse response) {
        ModelResult result = new ModelResult();
        switch (response.statusCode){
            case 200:
                boolean status = false;
                JsonObject jsResp = NetworkResponseRequest.parseToJsonObject(response);
                status = (jsResp.get("response").getAsString().equals("true"));
                result.setStatus(status);
                if(!status) {
                    result.setMessage("accept friend request failed: "+jsResp.get("message").getAsString());
                }

                break;

            default:
                result.setMessage("bad response:"+response.statusCode);
        }
        return result;
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
                callback.getModelOnSuccess(FriendService.declineFriendRequestRespProcess(response));
            }
        },receiverId, senderId);

    }
    /**
     * The helper method to process the result of decline friend request request.
     * @param response The network response to process
     * @return A BooleanResult
     * */
    private static ModelResult declineFriendRequestRespProcess(NetworkResponse response) {
        ModelResult result = new ModelResult();
        switch (response.statusCode){
            case 200:
                boolean status = false;
                JsonObject jsResp = NetworkResponseRequest.parseToJsonObject(response);
                status = (jsResp.get("response").getAsString().equals("true"));
                result.setStatus(status);
                if(!status) {
                    result.setMessage("Decline friend request failed: "+jsResp.get("message").getAsString());
                }

                break;

            default:
                result.setMessage("bad response:"+response.statusCode);
        }
        return result;
    }

}
