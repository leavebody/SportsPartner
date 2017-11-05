package com.sportspartner.service;

import com.sportspartner.dao.impl.FriendDaoImpl;
import com.sportspartner.dao.impl.PendingFriendRequestDaoImpl;
import com.sportspartner.dao.impl.PersonDaoImpl;
import com.sportspartner.model.PendingFriendRequest;
import com.sportspartner.model.Person;
import com.sportspartner.model.User;
import com.sportspartner.modelvo.UserOutlineVO;
import com.sportspartner.util.JsonResponse;
import com.sportspartner.util.GCMHelper;

import java.util.ArrayList;
import java.util.List;

public class FriendService {
    private PersonDaoImpl personDaoImpl = new PersonDaoImpl();
    private FriendDaoImpl friendDaoImpl = new FriendDaoImpl();
    private PendingFriendRequestDaoImpl pendingFriendRequestDaoImpl = new PendingFriendRequestDaoImpl();
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
            resp.setFriendlist(userOutlineVOList);
        }catch(Exception ex){
            throw new FriendServiceException("Get friendList error", ex);
        }
        return resp;
    }

    public JsonResponse sendFriendRequest(String recieverId, String senderId) throws  FriendServiceException{
        JsonResponse resp = new JsonResponse();
        GCMHelper gcmHelper = new GCMHelper();
        try {
                Person sender = personDaoImpl.getPerson(senderId);
                String senderName  = sender.getUserName();
                PendingFriendRequest request = new PendingFriendRequest(recieverId,senderId);
                if (pendingFriendRequestDaoImpl.hasPendingRequest(request))
                {
                    resp.setResponse("false");
                    resp.setMessage("Request has already been sent.");
                }
                else if(!gcmHelper.SendGCMData(recieverId,"New Friend Request",senderName+" sends you a new friend request")){
                    resp.setResponse("false");
                    resp.setMessage("Fail to send GCM.");
                } else if(!pendingFriendRequestDaoImpl.newPendingRequest(request)){
                    resp.setResponse("false");
                    resp.setMessage("Fail to add pending request.");
                }else if(!gcmHelper.SendGCMData(recieverId,"New Friend Request",senderName+" sends you a new friend request")){
                    resp.setResponse("false");
                    resp.setMessage("Fail to send GCM.");
                }else{
                    resp.setResponse("true");
                }
                /*
                    {
                    boolean sendPendingRequestSucceed = pendingFriendRequestDaoImpl.newPendingRequest(request);
                    boolean sendGCMSucceed = gcmHelper.SendGCMData(recieverId,"New Friend Request",senderName+" sends you a new friend request");
                    if (sendPendingRequestSucceed && sendGCMSucceed){
                        resp.setResponse("true");
                    }
                    else{
                        resp.setResponse("false");
                        resp.setMessage("Fail to send friend request.");
                    }
                }
                */

            } catch (Exception ex) {
            throw new FriendServiceException("New friendList error", ex);
        }
        return resp;
    }
    public JsonResponse acceptFriendRequest(String recieverId, String senderId) throws  FriendServiceException{
        JsonResponse resp = new JsonResponse();
        GCMHelper gcmHelper = new GCMHelper();
        try{
            Person receiver = personDaoImpl.getPerson(recieverId);
            String receiverName  = receiver.getUserName();
            if(friendDaoImpl.getFriend(recieverId,senderId)||friendDaoImpl.getFriend(senderId,recieverId)){
                resp.setResponse("false");
                resp.setMessage("They are already friends.");
            }
            else if(!pendingFriendRequestDaoImpl.hasPendingRequest(new PendingFriendRequest(recieverId,senderId))){
                resp.setResponse("false");
                resp.setMessage("No such pending request.");
            }
            else if(!friendDaoImpl.newFriend(senderId,recieverId)){
                resp.setResponse("false");
                resp.setMessage("Fail to accept friendrequest.");
            }
            else if(!pendingFriendRequestDaoImpl.deletePendingRequest(new PendingFriendRequest(recieverId,senderId))){
                resp.setResponse("false");
                resp.setMessage("Fail to delete pendingfriendrequest.");
            }else if(!gcmHelper.SendGCMData(senderId,"Friend Request Accepted",receiverName+" has accepted your request")){
                resp.setResponse("false");
                resp.setMessage("Fail to send GCM.");
            }else{
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
