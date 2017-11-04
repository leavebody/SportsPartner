package com.sportspartner.service;

import com.sportspartner.dao.impl.FriendDaoImpl;
import com.sportspartner.dao.impl.PendingFriendRequestDaoImpl;
import com.sportspartner.dao.impl.PersonDaoImpl;
import com.sportspartner.model.PendingFriendRequest;
import com.sportspartner.model.Person;
import com.sportspartner.model.User;
import com.sportspartner.modelvo.UserOutlineVO;
import com.sportspartner.util.JsonResponse;
import com.google.android.gcm.server.*;

import java.util.ArrayList;
import java.util.List;

public class FriendService {
    private PersonDaoImpl personDaoImpl = new PersonDaoImpl();
    private FriendDaoImpl friendDaoImpl = new FriendDaoImpl();
    private PendingFriendRequestDaoImpl pendingFriendRequestDao = new PendingFriendRequestDaoImpl();
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
        try {
                boolean succeed = pendingFriendRequestDao.newPendingRequest(new PendingFriendRequest(recieverId,senderId));
                Sender sender = new Sender("AIzaSyDd1V1gGOoRAiDO3WmKiaEEKWlO1Snc2WY");
                Message message = new Message.Builder().build();
                String devices = "";
                Result result = sender.send(message, devices, 5);
                if (result.getMessageId() != null) { String canonicalRegId = result.getCanonicalRegistrationId();
                    if (canonicalRegId != null) {
                    // same device has more than on registration ID: update database
                        System.out.println("same device has more than on registration ID: update database");
                    }
                } else {
                    String error = result.getErrorCodeName();
                    if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
                    // application has been removed from device - unregister database
                        System.out.println("application has been removed from device - unregister database");
                }
            }

            } catch (Exception ex) {
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
