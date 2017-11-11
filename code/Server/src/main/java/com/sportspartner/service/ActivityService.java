package com.sportspartner.service;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sportspartner.dao.impl.*;
import com.sportspartner.model.*;
import com.sportspartner.modelvo.*;
import com.sportspartner.util.GCMHelper;
import com.sportspartner.util.JsonResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static java.lang.Math.min;

public class ActivityService {
    private ActivityDaoImpl activityDaoImpl = new ActivityDaoImpl();
    private ActivityMemberDaoImpl activityMemberDaoImpl = new ActivityMemberDaoImpl();
    private ActivityCommentDaoImpl activityCommentDaoImpl = new ActivityCommentDaoImpl();
    private SportDaoImpl sportDaoImpl = new SportDaoImpl();
    private UserDaoImpl userDaoImpl = new UserDaoImpl();
    private PersonDaoImpl personDaoImpl = new PersonDaoImpl();
    private FacilityDaoImpl facilityDaoImpl = new FacilityDaoImpl();
    private PendingJoinActivityRequestDaoImpl pendingJoinActivityRequestDaoImpl = new PendingJoinActivityRequestDaoImpl();
    private NotificationDaoImpl notificationDaoImpl = new NotificationDaoImpl();
    /**
     * Get the detail of an activity
     *
     * @param activityId return an Id of an activity
     * @return JsonResponse to the front-end
     * @throws ActivityServiceException throws ActivityServiceException
     */
    public JsonResponse getActivityDetail(String activityId, String requestorId, String requestorKey) throws ActivityServiceException {
        JsonResponse resp = new JsonResponse();
        try {
            if (!hasActivity(activityId)) {
                resp.setResponse("false");
                resp.setMessage("No such activity");
            } else {
                ActivityVO activityVO = new ActivityVO();
                // Basic Info
                Activity activity = activityDaoImpl.getActivity(activityId);
                activityVO.setFromActivity(activity);
                // Sports Info
                Sport sport = sportDaoImpl.getSport(activity.getSportId());
                activityVO.setFromSport(sport);
                // Facility Info
                Facility facility = facilityDaoImpl.getFacility(activity.getFacilityId());
                if(facility!=null){
                    activityVO.setFromFacility(facility);
                }
                // Members Info
                List<ActivityMember> activityMembers = activityMemberDaoImpl.getAllActivitymembers(activityId);
                List<UserOutlineVO> members = new ArrayList<UserOutlineVO>();
                for (ActivityMember activityMember : activityMembers) {
                    String memberId = activityMember.getUserId();
                    if (!activityVO.getCreatorId().equals(memberId)) {
                        Person person = personDaoImpl.getPerson(memberId);
                        UserOutlineVO userOutlineVO = new UserOutlineVO();
                        userOutlineVO.setFromPerson(person);
                        members.add(userOutlineVO);
                    }
                }
                activityVO.setMembers(members);
                //Comment Info
                List<ActivityComment> comments = activityCommentDaoImpl.getAllActivityComments(activityId);
                activityVO.setFromComments(comments);
                if (activityVO.isMissingField()) {
                    resp.setResponse("false");
                    resp.setMessage("Activity is missing");
                } else {
                    resp.setResponse("true");
                    resp.setActivity(activityVO);
                }

                // check relationship between the user and the requestor
                if(isAuthorized(requestorId, requestorKey)) {
                    if (requestorId.equals(activityVO.getCreatorId())) {
                        resp.setUserType("CREATOR");
                        return resp;
                    } else if (activityMemberDaoImpl.hasActivityMember(new ActivityMember(activityId, requestorId))) {
                        resp.setUserType("MEMBER");
                        return resp;
                    }
                }
                resp.setUserType("STRANGER");
            }
        } catch (Exception ex) {
            throw new ActivityServiceException("Activity Service getActivityDetail Exception", ex);
        }
        return resp;
    }

    /**
     * Get the outline of an activity
     *
     * @param activityId Id of an activity
     * @return JsonResponse to the front-end
     * @throws ActivityServiceException throws ActivityServiceException
     */
    public JsonResponse getActivityOutline(String activityId) throws ActivityServiceException {
        JsonResponse resp = new JsonResponse();
        try {
            ActivityOutlineVO activityOutlineVO = new ActivityOutlineVO();
            if (!hasActivity(activityId)) {
                resp.setResponse("false");
                resp.setMessage("No such activity");
            } else {
                Activity activity = activityDaoImpl.getActivity(activityId);
                Sport sport = sportDaoImpl.getSport(activity.getSportId());
                activityOutlineVO.setFromActivity(activity);
                activityOutlineVO.setFromSport(sport);
                resp.setActivityOutline(activityOutlineVO);
                resp.setResponse("true");
            }
        } catch (Exception ex) {
            throw new ActivityServiceException("Activity Service getActivityOutline Exception", ex);
        }
        return resp;
    }

    /**
     * Get the upcoming activity of a user
     *
     * @param userId Id of a user
     * @param offset The index of the first result to return. Default: 0 (i.e., the first result). Maximum offset: 200. Use with limit to get the next page of search results.
     * @param limit  The maximum number of results to return. Default: 3. Minimum: 1. Maximum: 10.
     * @return Json Response to the front-end
     * @throws ActivityServiceException throws ActivityServiceException
     */
    public JsonResponse getUpcomingActivity(String userId, int offset, int limit) throws ActivityServiceException {
        JsonResponse resp = new JsonResponse();
        try {
            if (!hasUser(userId)) {
                resp.setResponse("false");
                resp.setMessage("No such user");
            } else {
                List<ActivityOutlineVO> activityOutlineVOs = new ArrayList<ActivityOutlineVO>();
                List<Activity> upcomingActivities = activityDaoImpl.getUpcomingActivities(userId);
                if (upcomingActivities.size() <= offset) {
                    resp.setResponse("true");
                    resp.setMessage("No more upcoming activities");
                    resp.setActivityOutlines(new ArrayList<>());
                } else {
                    List<Activity> upcomingActivitiesSubset = upcomingActivities.subList(offset, min(offset + limit, upcomingActivities.size()));
                    for (Activity upcomingActivity : upcomingActivitiesSubset) {
                        Sport sport = sportDaoImpl.getSport(upcomingActivity.getSportId());
                        ActivityOutlineVO activityOutlineVO = new ActivityOutlineVO();
                        activityOutlineVO.setFromActivity(upcomingActivity);
                        activityOutlineVO.setFromSport(sport);
                        activityOutlineVOs.add(activityOutlineVO);
                    }
                    resp.setActivityOutlines(activityOutlineVOs);
                    resp.setResponse("true");
                }
            }
        } catch (Exception ex) {
            throw new ActivityServiceException("Activity Service getUpcomingActivity Exception", ex);
        }
        return resp;
    }

    /**
     * Get the past activity of a user
     *
     * @param userId Id of a user
     * @param offset The index of the first result to return. Default: 0 (i.e., the first result). Maximum offset: 200. Use with limit to get the next page of search results.
     * @param limit  The maximum number of results to return. Default: 3. Minimum: 1. Maximum: 10.
     * @return Json Response to the front-end
     * @throws ActivityServiceException throws ActivityServiceException
     */
    public JsonResponse getPastActivity(String userId, int offset, int limit) throws ActivityServiceException {
        JsonResponse resp = new JsonResponse();
        try {
            if (!hasUser(userId)) {
                resp.setResponse("false");
                resp.setMessage("No such user");
            } else {
                List<ActivityOutlineVO> activityOutlineVOs = new ArrayList<ActivityOutlineVO>();
                List<Activity> pastActivities = activityDaoImpl.getPastActivities(userId);
                if (pastActivities.size() <= offset) {
                    resp.setResponse("true");
                    resp.setMessage("No more past activities");
                    resp.setActivityOutlines(new ArrayList<>());
                } else {
                    List<Activity> pastActivitiesSubset = pastActivities.subList(offset, min(offset + limit, pastActivities.size()));
                    for (Activity pastActivity : pastActivitiesSubset) {
                        Sport sport = sportDaoImpl.getSport(pastActivity.getSportId());
                        ActivityOutlineVO activityOutlineVO = new ActivityOutlineVO();
                        activityOutlineVO.setFromActivity(pastActivity);
                        activityOutlineVO.setFromSport(sport);
                        activityOutlineVOs.add(activityOutlineVO);
                    }
                    resp.setActivityOutlines(activityOutlineVOs);
                    resp.setResponse("true");
                }
            }
        } catch (Exception ex) {
            throw new ActivityServiceException("Activity Service getPastActivity Exception", ex);
        }
        return resp;
    }

    /**
     * Get all members of an activity.
     *
     * @param activityId The UUID of the activity.
     * @return JsonResponse object
     * @throws ActivityServiceException
     */
    public JsonResponse getActivityMembers(String activityId) throws ActivityServiceException {
        JsonResponse resp = new JsonResponse();
        try {
            List<UserOutlineVO> userOutlineVOs = new ArrayList<UserOutlineVO>();
            List<ActivityMember> activityMembers = activityMemberDaoImpl.getAllActivitymembers(activityId);
            for (ActivityMember activityMember : activityMembers) {
                UserOutlineVO userOutlineVO = new UserOutlineVO();
                userOutlineVO.setFromPerson(personDaoImpl.getPerson(activityMember.getUserId()));
                userOutlineVOs.add(userOutlineVO);
            }
            resp.setMembers(userOutlineVOs);
            resp.setResponse("true");
        } catch (Exception ex) {
            throw new ActivityServiceException("Activity Service getActivityMembers Exception", ex);
        }
        return resp;
    }

    /**
     * Add a member to the activity.
     *
     * @param activityId The UUID of the activity.
     * @param body       The request body from controller.
     * @return JsonResponse object
     * @throws ActivityServiceException
     */
    public JsonResponse addActivityMember(String activityId, String body) throws ActivityServiceException {
        JsonResponse resp = new JsonResponse();
        try {
            JsonObject json = new Gson().fromJson(body, JsonObject.class);
            String userId = json.get("userId").getAsString();
            if (activityMemberDaoImpl.newActivityMember(new ActivityMember(activityId, userId))) {
                resp.setResponse("true");
            } else {
                resp.setMessage("Unable to create a new entry in database");
                resp.setResponse("false");
            }
        } catch (Exception ex) {
            throw new ActivityServiceException("Activity Service addActivityMember Exception", ex);
        }
        return resp;
    }


    /**
     * Remove one member from an activity.
     *
     * @param activityId The UUID of the activity.
     * @param body       The request body from controller.
     * @return JsonResponse object.
     * @throws ActivityServiceException
     */
    public JsonResponse removeActivityMember(String activityId, String body) throws ActivityServiceException {
        JsonResponse resp = new JsonResponse();
        try {
            JsonObject json = new Gson().fromJson(body, JsonObject.class);
            String userId = json.get("userId").getAsString();
            if (activityMemberDaoImpl.deleteActivityMember(new ActivityMember(activityId, userId))) {
                resp.setResponse("true");
            } else {
                resp.setMessage("Unable to delete the entry from database");
                resp.setResponse("false");
            }
        } catch (Exception ex) {
            throw new ActivityServiceException("Activity Service removeActivityMember Exception", ex);
        }
        return resp;
    }

    /**
     * Create a new activity.
     * @param body Json string containing fields of userId, key and activity
     * @return JsonResponse object.
     * @throws ActivityServiceException
     */
    public JsonResponse newActivity(String body) throws ActivityServiceException {
        JsonResponse resp = new JsonResponse();
        try {
            JsonObject json = new Gson().fromJson(body, JsonObject.class);
            String userId = json.get("userId").getAsString();
            String key = json.get("key").getAsString();
            if (!isAuthorized(userId, key)) {
                resp.setResponse("false");
                resp.setMessage("Lack Authorization");
            } else {
                Activity activity = new Gson().fromJson(json.get("activity").getAsJsonObject(), Activity.class);

                String activityId = UUID.randomUUID().toString();
                activity.setActivityId(activityId);

                // set the location of the activity
                String facilityId = activity.getFacilityId();
                if (!facilityId.equals("NULL")) {
                    Facility facility = facilityDaoImpl.getFacility(facilityId);
                    activity.setFromFacility(facility);
                }

                // create a new activity in database
                String creatorId = activity.getCreatorId();
                if (activityDaoImpl.newActivity(activity) && activityMemberDaoImpl.newActivityMember(new ActivityMember(activityId, creatorId))) {
                    resp.setResponse("true");
                    resp.setActivityId(activityId);
                } else {
                    resp.setResponse("false");
                    resp.setMessage("Fail to create a new entry in database");
                }
            }
        } catch (Exception ex) {
            throw new ActivityServiceException("Activity Service newActivityException", ex);
        }
        return resp;
    }

    /**
     * Update an activity's info.
     * @param activityId The UUID of an activity.
     * @param body The Json string from controller, containing an ActivityVO object, requestorId and requestorKey.
     * @return JsonResponse object
     * @throws ActivityServiceException
     */
    public JsonResponse updateActivity(String activityId, String body) throws ActivityServiceException{
        JsonResponse resp = new JsonResponse();
        try {
            JsonObject json = new Gson().fromJson(body, JsonObject.class);
            String requestorId = json.get("requestorId").getAsString();
            String requestorKey = json.get("requestorKey").getAsString();

            if (!isAuthorized(requestorId, requestorKey) || requestorId.equals(activityDaoImpl.getActivity(activityId).getCreatorId())) {
                resp.setResponse("false");
                resp.setMessage("Lack authorization to update activity info");
            } else {
                Activity activity = new Gson().fromJson(json.get("activity").getAsJsonObject(), Activity.class);
                boolean isUpdate = activityDaoImpl.updateActivity(activity);
                if (!isUpdate) {
                    resp.setResponse("false");
                    resp.setMessage("Update failed");
                } else {
                    resp.setResponse("true");
                }
            }
        }catch(Exception ex){
            throw new ActivityServiceException("Activity Service updateActivity error", ex);
        }
//      System.out.println(resp);
        return resp;
    }

    /**
     * Creator of an activity accept a new join application.
     * A join activity request notification will be removed from notification list.
     * And a new member will be added to the activity.
     * @param activityId The UUID of the acitivity.
     * @param body The Json string from controller, containing "creatorId", "creatorKey", "userId".
     * @return JsonResponse object
     * @throws ActivityServiceException
     */
    public JsonResponse acceptJoinActivityRequest(String activityId, String body) throws ActivityServiceException{
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
            throw new ActivityServiceException("acceptJoinActivityRequest error", ex);
        }
        return resp;

    }

    /**
     * Creator of the activity decline join application.
     * The requestor will receive a request and the notification will disappear from creator's notification center.
     * @param activityId The UUID of the activity.
     * @param body The Json string from controller, containing "creatorId", "creatorKey", "userId".
     * @return JsonResponse Object
     * @throws ActivityServiceException
     */
    public JsonResponse declineJoinActivityRequest(String activityId, String body) throws ActivityServiceException{
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
            throw new ActivityServiceException("declineJoinActivityRequest error", ex);
        }
        return resp;

    }

    public void searchActivity(){
        //TODO
    }

    public void cancelActivity(){
        //TODO
    }
    public void reviewctivity(){
        //TODO
    }
    public void activityInfoUpdate(){
        //TODO
    }
    public void userIdentityCheck(){
        //TODO
    }
    public void inviteMember(){
        //TODO
    }
    public void deleteMember(){
        //TODO
    }
    public void addActivityDiscussion(){
        //TODO
    }
    /**
     * Check whether a user exists
     * @param userId Id of a user
     * @return true means the user exists,  false means the user doesn't exist
     */
    public boolean hasUser(String userId){
        User user = userDaoImpl.getUser(userId);
        return user!=null;
    }
    /**
     * Check whether an activity exists
     * @param activityId Id of an activity
     * @return true means the user exists,  false means the user doesn't exist
     */
    public boolean hasActivity(String activityId){
        Activity activity = activityDaoImpl.getActivity(activityId);
        return activity!= null;

    }

    /**
     * Check whether a user is authorized
     * @param userId Id of a user
     * @param key login key of a user
     * @return true means the user is authorized,  false means the user isn't authorized
     */
    public boolean isAuthorized(String userId, String key){
        Authorization authorization = new Authorization(userId, key);
        AuthorizationDaoImpl authorizationDaoImpl = new AuthorizationDaoImpl();
        return authorizationDaoImpl.hasAuthorization(authorization);
    }

    /**
     *  Exception class for Activity
     */
    public static class ActivityServiceException extends Exception {
        public ActivityServiceException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
