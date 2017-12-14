package com.sportspartner.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sportspartner.dao.impl.*;
import com.sportspartner.model.*;
import com.sportspartner.util.GCMHelper;
import com.sportspartner.util.JsonResponse;

import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;


public class NotificationService {

    private PersonDaoImpl personDaoImpl = new PersonDaoImpl();
    private ActivityMemberDaoImpl activityMemberDaoImpl = new ActivityMemberDaoImpl();
    private PendingJoinActivityRequestDaoImpl pendingJoinActivityRequestDaoImpl = new PendingJoinActivityRequestDaoImpl();
    private NotificationDaoImpl notificationDaoImpl = new NotificationDaoImpl();
    private FriendDaoImpl friendDaoImpl = new FriendDaoImpl();
    private PendingFriendRequestDaoImpl pendingFriendRequestDaoImpl = new PendingFriendRequestDaoImpl();


    /**
     * Sender send a friend request to receiver
     *
     * @param receiverId Id of receiver
     * @param senderId   Id of sender
     * @return JsonResponse to the front-end
     */
    public JsonResponse sendFriendRequest(String receiverId, String senderId) throws Exception {
        JsonResponse resp = new JsonResponse();
        GCMHelper gcmHelper = new GCMHelper();

        Person sender = personDaoImpl.getPerson(senderId);
        String senderName = sender.getUserName();
        PendingFriendRequest request = new PendingFriendRequest(receiverId, senderId);
        if (friendDaoImpl.getFriend(receiverId, senderId) || friendDaoImpl.getFriend(senderId, receiverId)) {
            resp.setResponse("false");
            resp.setMessage("They are already friends.");
        } else if (pendingFriendRequestDaoImpl.hasPendingRequest(request)) {
            resp.setResponse("false");
            resp.setMessage("Request has already been sent.");
        } else if (!pendingFriendRequestDaoImpl.newPendingRequest(request)) {
            resp.setResponse("false");
            resp.setMessage("Fail to add pending request.");
        }
                /*
                else if(!gcmHelper.SendGCMData(receiverId,"New Friend Request",senderName+" sends you a new friend request")){
                    resp.setResponse("false");
                    resp.setMessage("Fail to send GCM.");
                }
                */
        else {
            String notificationId = UUID.randomUUID().toString();
            String notificationTitle = " New Friend Request";
            String notificationDetail = senderName + " sends you a new friend request";
            String notificationType = "INTERACTION";
            Date time = new Date(System.currentTimeMillis());
            int notificationState = 1;
            int notificationPriority = 1;
            Notification notification = new Notification(receiverId, notificationId, notificationTitle, notificationDetail, notificationType,
                    senderId, time, notificationState, notificationPriority);
            if (!notificationDaoImpl.newNotification(notification)) {
                resp.setResponse("false");
                resp.setMessage("Fail to store notification.");
            } else if (!gcmHelper.SendGCMNotification(notification)) {
                resp.setResponse("false");
                resp.setMessage("Fail to send GCM.");
            } else {
                resp.setResponse("true");
            }
        }

        return resp;
    }

    /**
     * Receiver accept the friend request from sender. A GCM message will be sent to the sender.
     *
     * @param receiverId Id of receiver
     * @param senderId   Id of sender
     * @return JsonResponse to the front-end
     */
    public JsonResponse acceptFriendRequest(String receiverId, String senderId) throws Exception {
        JsonResponse resp = new JsonResponse();
        GCMHelper gcmHelper = new GCMHelper();

        Person receiver = personDaoImpl.getPerson(receiverId);
        String receiverName = receiver.getUserName();
        if (friendDaoImpl.getFriend(receiverId, senderId) || friendDaoImpl.getFriend(senderId, receiverId)) {
            resp.setResponse("false");
            resp.setMessage("They are already friends.");
        } else if (!pendingFriendRequestDaoImpl.hasPendingRequest(new PendingFriendRequest(receiverId, senderId))) {
            resp.setResponse("false");
            resp.setMessage("No such pending request.");
        } else if (!friendDaoImpl.newFriend(senderId, receiverId)) {
            resp.setResponse("false");
            resp.setMessage("Fail to accept friend request.");
        } else if (!pendingFriendRequestDaoImpl.deletePendingRequest(new PendingFriendRequest(receiverId, senderId))) {
            resp.setResponse("false");
            resp.setMessage("Fail to delete pending friend request.");
        } else {
            String notificationId = UUID.randomUUID().toString();
            String notificationTitle = "Friend Request Accepted";
            String notificationDetail = receiverName + " has accepted your friend request";
            String notificationType = "INTERACTION";
            Date time = new Date(System.currentTimeMillis());
            int notificationState = 1;
            int notificationPriority = 1;
            Notification notification = new Notification(senderId, notificationId, notificationTitle, notificationDetail, notificationType,
                    receiverId, time, notificationState, notificationPriority);
            if (!notificationDaoImpl.newNotification(notification)) {
                resp.setResponse("false");
                resp.setMessage("Fail to store notification.");
            } else if (!gcmHelper.SendGCMNotification(notification)) {
                resp.setResponse("false");
                resp.setMessage("Fail to send GCM.");
            } else {
                resp.setResponse("true");
            }
        }

        return resp;
    }

    /**
     * Receiver decline the friend request from sender. A GCM message will be sent to the sender.
     *
     * @param receiverId Id of receiver
     * @param senderId   Id of the sender
     * @return JsonResponse to the front-end
     */
    public JsonResponse declineFriendRequest(String receiverId, String senderId) throws Exception {
        JsonResponse resp = new JsonResponse();
        GCMHelper gcmHelper = new GCMHelper();

        Person receiver = personDaoImpl.getPerson(receiverId);
        String receiverName = receiver.getUserName();
        if (friendDaoImpl.getFriend(receiverId, senderId) || friendDaoImpl.getFriend(senderId, receiverId)) {
            resp.setResponse("false");
            resp.setMessage("They are already friends.");
        } else if (!pendingFriendRequestDaoImpl.hasPendingRequest(new PendingFriendRequest(receiverId, senderId))) {
            resp.setResponse("false");
            resp.setMessage("No such pending request.");
        } else if (!pendingFriendRequestDaoImpl.deletePendingRequest(new PendingFriendRequest(receiverId, senderId))) {
            resp.setResponse("false");
            resp.setMessage("Fail to delete pending friend request.");
        }
                /*
                if(!gcmHelper.SendGCMData(senderId,"Friend Request Declined :(",receiverName+" has declined your request :(")){
                resp.setResponse("false");
                resp.setMessage("Fail to send GCM.");
                */
        else {
            String notificationId = UUID.randomUUID().toString();
            String notificationTitle = "Friend Request Declined";
            String notificationDetail = receiverName + " has declined your friend request";
            String notificationType = "INTERACTION";
            Date time = new Date(System.currentTimeMillis());
            int notificationState = 1;
            int notificationPriority = 1;
            Notification notification = new Notification(senderId, notificationId, notificationTitle, notificationDetail, notificationType,
                    receiverId, time, notificationState, notificationPriority);
            if (!notificationDaoImpl.newNotification(notification)) {
                resp.setResponse("false");
                resp.setMessage("Fail to store notification.");
            } else if (!gcmHelper.SendGCMNotification(notification)) {
                resp.setResponse("false");
                resp.setMessage("Fail to send GCM.");
            } else {
                resp.setResponse("true");
            }
        }

        return resp;
    }


    public JsonResponse sendJoinActivityRequest(String activityId, String body) throws Exception {
        JsonResponse resp = new JsonResponse();
        GCMHelper gcmHelper = new GCMHelper();
        try {
            JsonObject json = new Gson().fromJson(body, JsonObject.class);
            String creatorId = json.get("creatorId").getAsString();
            String senderId = json.get("senderId").getAsString();
            Person sender = personDaoImpl.getPerson(senderId);
            String senderName  = sender.getUserName();
            PendingJoinActivityRequest request = new PendingJoinActivityRequest(activityId, senderId, creatorId);
            if(activityMemberDaoImpl.hasActivityMember(new ActivityMember(activityId, senderId))){
                resp.setResponse("false");
                resp.setMessage("The user has already been a member of the activity.");
            }else if (pendingJoinActivityRequestDaoImpl.hasPendingRequest(request))
            {
                resp.setResponse("false");
                resp.setMessage("Request has already been sent.");
            }else if(!pendingJoinActivityRequestDaoImpl.newPendingRequest(request)){
                resp.setResponse("false");
                resp.setMessage("Fail to add pending request.");
            }
            else{
                String notificationId  = UUID.randomUUID().toString();
                String notificationTitle = " New Activity Joining Application";
                String notificationDetail = senderName + " applies to join the following activity";
                String notificationType = "INTERACTION";
                Date time = new Date(System.currentTimeMillis());
                int notificationState = 1;
                int notificationPriority = 2;
                Notification notification = new Notification(creatorId,notificationId,notificationTitle,notificationDetail,notificationType,
                        senderId,time,notificationState,notificationPriority);
                if(!notificationDaoImpl.newNotification(notification)){
                    resp.setResponse("false");
                    resp.setMessage("Fail to store notification.");
                }
                else if(!gcmHelper.SendGCMNotification(notification)){
                    resp.setResponse("false");
                    resp.setMessage("Fail to send GCM.");
                }
                else {
                    resp.setResponse("true");
                }
            }
        } catch (Exception ex) {
            throw new Exception("Fail to send new notification for joining an acticity", ex);
        }
        return resp;
    }


    /**
     * Creator of an activity accept a new join application.
     * A join activity request notification will be removed from notification list.
     * And a new member will be added to the activity.
     * @param activityId The UUID of the acitivity.
     * @param body The Json string from controller, containing "creatorId", "creatorKey", "userId".
     * @return JsonResponse object
     * @throws Exception
     */

    public JsonResponse acceptJoinActivityRequest(String activityId, String body) throws Exception {
        JsonResponse resp = new JsonResponse();
        GCMHelper gcmHelper = new GCMHelper();
        try{
            JsonObject json = new Gson().fromJson(body, JsonObject.class);
            String creatorId = json.get("creatorId").getAsString();
            String creatorKey = json.get("creatorKey").getAsString();
            String userId = json.get("userId").getAsString();
            String creatorName = personDaoImpl.getPerson(creatorId).getUserName();
            if(!isAuthorized(creatorId, creatorKey)){
                resp.setResponse("false");
                resp.setMessage("Lack authorization.");
            }else if(!activityMemberDaoImpl.newActivityMember(new ActivityMember(activityId, userId))){
                resp.setResponse("false");
                resp.setMessage("Fail to add a new member to the activity.");
            }else if(!pendingJoinActivityRequestDaoImpl.deletePendingRequest(new PendingJoinActivityRequest(activityId, userId, creatorId))){
                resp.setResponse("false");
                resp.setMessage("Fail to delete pending join activity application.");
            }else{
                String notificationId  = UUID.randomUUID().toString();
                String notificationTitle = "Join Activity Application Accepted";
                String notificationDetail = "Your application for joining the following activity has been accepted by" + creatorName;
                String notificationType = "INTERACTION";
                Date time = new Date(System.currentTimeMillis());
                int notificationState = 1;
                int notificationPriority = 1;
                Notification notification = new Notification(creatorId,notificationId,notificationTitle,notificationDetail,notificationType,
                        userId,time,notificationState,notificationPriority);
                if(!notificationDaoImpl.newNotification(notification)){
                    resp.setResponse("false");
                    resp.setMessage("Fail to store notification.");
                }
                else if(!gcmHelper.SendGCMNotification(notification)){
                    resp.setResponse("false");
                    resp.setMessage("Fail to send GCM.");
                }
                else {
                    resp.setResponse("true");
                }
            }
        }catch (Exception ex) {
            throw new Exception("acceptJoinActivityRequest error", ex);
        }
        return resp;

    }

    /**
     * Creator of the activity decline join application.
     * The requestor will receive a request and the notification will disappear from creator's notification center.
     * @param activityId The UUID of the activity.
     * @param body The Json string from controller, containing "creatorId", "creatorKey", "userId".
     * @return JsonResponse Object
     * @throws Exception
     */

    public JsonResponse declineJoinActivityRequest(String activityId, String body) throws Exception {
        JsonResponse resp = new JsonResponse();
        GCMHelper gcmHelper = new GCMHelper();
        try{
            JsonObject json = new Gson().fromJson(body, JsonObject.class);
            String creatorId = json.get("creatorId").getAsString();
            String creatorKey = json.get("creatorKey").getAsString();
            String userId = json.get("userId").getAsString();
            String creatorName = personDaoImpl.getPerson(creatorId).getUserName();
            if(!isAuthorized(creatorId, creatorKey)){
                resp.setResponse("false");
                resp.setMessage("Lack authorization.");
            }else if(!pendingJoinActivityRequestDaoImpl.deletePendingRequest(new PendingJoinActivityRequest(activityId, userId, creatorId))){
                resp.setResponse("false");
                resp.setMessage("Fail to delete pending join activity application.");
            }else{
                String notificationId  = UUID.randomUUID().toString();
                String notificationTitle = "Join Activity Application Declined";
                String notificationDetail = "Your application for joining the following activity has been declined by" + creatorName;
                String notificationType = "INTERACTION";
                Date time = new Date(System.currentTimeMillis());
                int notificationState = 1;
                int notificationPriority = 1;
                Notification notification = new Notification(creatorId,notificationId,notificationTitle,notificationDetail,notificationType,
                        userId,time,notificationState,notificationPriority);
                if(!notificationDaoImpl.newNotification(notification)){
                    resp.setResponse("false");
                    resp.setMessage("Fail to store notification.");
                }
                else if(!gcmHelper.SendGCMNotification(notification)){
                    resp.setResponse("false");
                    resp.setMessage("Fail to send GCM.");
                }
                else {
                    resp.setResponse("true");
                }
            }
        }catch (Exception ex) {
            throw new Exception("declineJoinActivityRequest error", ex);
        }
        return resp;

    }

    /**
     * Check whether a user is authorized
     * @param userId Id of a user
     * @param key login key of a user
     * @return true means the user is authorized,  false means the user isn't authorized
     */
    public boolean isAuthorized(String userId, String key) {
        Authorization authorization = new Authorization(userId, key);
        AuthorizationDaoImpl authorizationDaoImpl = new AuthorizationDaoImpl();
        boolean authorized = false;
        try{
            authorized = authorizationDaoImpl.hasAuthorization(authorization);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return authorized;
    }



}
