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
