package com.sportspartner.service;

import com.sportspartner.dao.impl.FriendDaoImpl;
import com.sportspartner.dao.impl.NotificationDaoImpl;
import com.sportspartner.dao.impl.PendingFriendRequestDaoImpl;
import com.sportspartner.dao.impl.PersonDaoImpl;
import com.sportspartner.model.Notification;
import com.sportspartner.model.PendingFriendRequest;
import com.sportspartner.model.Person;
import com.sportspartner.model.User;
import com.sportspartner.modelvo.UserOutlineVO;
import com.sportspartner.util.JsonResponse;
import com.sportspartner.util.GCMHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class FriendService {
    private PersonDaoImpl personDaoImpl = new PersonDaoImpl();
    private FriendDaoImpl friendDaoImpl = new FriendDaoImpl();
    private PendingFriendRequestDaoImpl pendingFriendRequestDaoImpl = new PendingFriendRequestDaoImpl();
    private NotificationDaoImpl notificationDaoimpl = new NotificationDaoImpl();
    /**
     * Get the friendlist of a person
     * @param userId Id of a User
     * @return JsonResponse to the front-end
     * @throws FriendServiceException throws FriendServiceException
     */
    public JsonResponse getFriendList(String userId) throws  FriendServiceException{
        JsonResponse resp = new JsonResponse();
        try{
            List <UserOutlineVO> userOutlineVOList = new ArrayList<>();
            List<User> users = friendDaoImpl.getAllFriends(userId);
            for (User user :users){
                Person person = personDaoImpl.getPerson(user.getUserId());
                UserOutlineVO userOutlineVO =  new UserOutlineVO();
                userOutlineVO.setFromPerson(person);
                userOutlineVOList.add(userOutlineVO);
            }
            //resp.setResponse("true");
            resp.setResponse("true");
            resp.setFriendlist(userOutlineVOList);
        }catch(Exception ex){
            throw new FriendServiceException("Get friendList error", ex);

        }
        return resp;
    }

    /**
     * Sender send a friend request to receiver
     * @param receiverId Id of receiver
     * @param senderId Id of sender
     * @return JsonResponse to the front-end
     * @throws FriendServiceException throws FriendServiceException
     */
    public JsonResponse sendFriendRequest(String receiverId, String senderId) throws  FriendServiceException{
        JsonResponse resp = new JsonResponse();
        GCMHelper gcmHelper = new GCMHelper();
        try {
                Person sender = personDaoImpl.getPerson(senderId);
                String senderName  = sender.getUserName();
                PendingFriendRequest request = new PendingFriendRequest(receiverId,senderId);
                if(friendDaoImpl.getFriend(receiverId,senderId)||friendDaoImpl.getFriend(senderId,receiverId)){
                    resp.setResponse("false");
                    resp.setMessage("They are already friends.");
                }else if (pendingFriendRequestDaoImpl.hasPendingRequest(request))
                {
                    resp.setResponse("false");
                    resp.setMessage("Request has already been sent.");
                }else if(!pendingFriendRequestDaoImpl.newPendingRequest(request)){
                    resp.setResponse("false");
                    resp.setMessage("Fail to add pending request.");
                }
                /*
                else if(!gcmHelper.SendGCMData(receiverId,"New Friend Request",senderName+" sends you a new friend request")){
                    resp.setResponse("false");
                    resp.setMessage("Fail to send GCM.");
                }
                */
                else{
                    String notificationId  = UUID.randomUUID().toString();
                    String notificationTitle = " New Friend Request";
                    String notificationDetail = senderName+" sends you a new friend request";
                    String notificationType = "INTERACTION";
                    Date time = new Date(System.currentTimeMillis());
                    int notificationState = 1;
                    int notificationPriority = 1;
                    Notification notification = new Notification(receiverId,notificationId,notificationTitle,notificationDetail,notificationType,
                            senderId,time,notificationState,notificationPriority);
                    if(!notificationDaoimpl.newNotification(notification)){
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
            throw new FriendServiceException("New friendList error", ex);
        }
        return resp;
    }

    /**
     *  Receiver accept the friend request from sender. A GCM message will be sent to the sender.
     * @param receiverId Id of receiver
     * @param senderId Id of sender
     * @return  JsonResponse to the front-end
     * @throws FriendServiceException throws FriendServiceException
     */
    public JsonResponse acceptFriendRequest(String receiverId, String senderId) throws  FriendServiceException{
        JsonResponse resp = new JsonResponse();
        GCMHelper gcmHelper = new GCMHelper();
        try{
            Person receiver = personDaoImpl.getPerson(receiverId);
            String receiverName  = receiver.getUserName();
            if(friendDaoImpl.getFriend(receiverId,senderId)||friendDaoImpl.getFriend(senderId,receiverId)){
                resp.setResponse("false");
                resp.setMessage("They are already friends.");
            }else if(!pendingFriendRequestDaoImpl.hasPendingRequest(new PendingFriendRequest(receiverId,senderId))){
                resp.setResponse("false");
                resp.setMessage("No such pending request.");
            }else if(!friendDaoImpl.newFriend(senderId,receiverId)){
                resp.setResponse("false");
                resp.setMessage("Fail to accept friend request.");
            }else if(!pendingFriendRequestDaoImpl.deletePendingRequest(new PendingFriendRequest(receiverId,senderId))){
                resp.setResponse("false");
                resp.setMessage("Fail to delete pending friend request.");
            }else{
                String notificationId  = UUID.randomUUID().toString();
                String notificationTitle = "Friend Request Accepted";
                String notificationDetail = receiverName+" has accepted your friend request";
                String notificationType = "MESSAGE";
                Date time = new Date(System.currentTimeMillis());
                int notificationState = 1;
                int notificationPriority = 1;
                Notification notification = new Notification(senderId,notificationId,notificationTitle,notificationDetail,notificationType,
                        receiverId,time,notificationState,notificationPriority);
                if(!notificationDaoimpl.newNotification(notification)){
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
            throw new FriendServiceException("New friendList error", ex);
        }
        return resp;
    }
    /**
     *  Receiver decline the friend request from sender. A GCM message will be sent to the sender.
     * @param receiverId Id of receiver
     * @param senderId Id of the sender
     * @return JsonResponse to the front-end
     * @throws FriendServiceException throws FriendServiceException
     */
    public JsonResponse declineFriendRequest(String receiverId, String senderId) throws  FriendServiceException{
        JsonResponse resp = new JsonResponse();
        GCMHelper gcmHelper = new GCMHelper();
        try{
            Person receiver = personDaoImpl.getPerson(receiverId);
            String receiverName  = receiver.getUserName();
            if(friendDaoImpl.getFriend(receiverId,senderId)||friendDaoImpl.getFriend(senderId,receiverId)){
                resp.setResponse("false");
                resp.setMessage("They are already friends.");
            }else if(!pendingFriendRequestDaoImpl.hasPendingRequest(new PendingFriendRequest(receiverId,senderId))){
                resp.setResponse("false");
                resp.setMessage("No such pending request.");
            }else if(!pendingFriendRequestDaoImpl.deletePendingRequest(new PendingFriendRequest(receiverId,senderId))){
                resp.setResponse("false");
                resp.setMessage("Fail to delete pending friend request.");
            }
                /*
                if(!gcmHelper.SendGCMData(senderId,"Friend Request Declined :(",receiverName+" has declined your request :(")){
                resp.setResponse("false");
                resp.setMessage("Fail to send GCM.");
                */
            else{
                String notificationId  = UUID.randomUUID().toString();
                String notificationTitle = "Friend Request Declined";
                String notificationDetail = receiverName+" has declined your friend request";
                String notificationType = "MESSAGE";
                Date time = new Date(System.currentTimeMillis());
                int notificationState = 1;
                int notificationPriority = 1;
                Notification notification = new Notification(senderId,notificationId,notificationTitle,notificationDetail,notificationType,
                        receiverId,time,notificationState,notificationPriority);
                if(!notificationDaoimpl.newNotification(notification)){
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
            throw new FriendServiceException("New friendList error", ex);
        }
        return resp;
    }

    /**
>>>>>>> 9cdb6bc8ad4de4ab6dc17db4577f5b0ed10409b2
     * delete a friend from the friendlist
     * @param userId1 Id of current user
     * @param userId2 Id of target user
     * @return JsonResponse to the front-end
     * @throws FriendServiceException throws FriendServiceException
     */
    public JsonResponse deleteFriend(String userId1, String userId2) throws  FriendServiceException{
        JsonResponse resp = new JsonResponse();
        try{
            if(!(friendDaoImpl.getFriend(userId1,userId2)||friendDaoImpl.getFriend(userId2,userId1))){
                resp.setResponse("false");
                resp.setMessage("They are not friends at all!");
            }else if(!(friendDaoImpl.deleteFriend(userId1,userId2)||friendDaoImpl.deleteFriend(userId2,userId1))){
                resp.setResponse("false");
                resp.setMessage("Fail to delete from friends database");
            }
            else{
                resp.setResponse("true");
            }
        }catch (Exception ex) {
            throw new FriendServiceException("New friendList error", ex);
        }
        return resp;
    }


    /**
     * Exception Class for Friend
     */
    public static class FriendServiceException extends Exception {
        public FriendServiceException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
