package com.sportspartner.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sportspartner.dao.impl.*;
import com.sportspartner.model.*;
import com.sportspartner.modelvo.*;
import com.sportspartner.util.GCMHelper;
import com.sportspartner.util.JsonResponse;

import java.sql.SQLException;
import java.text.ParseException;
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

    /**
     * Get the detail of an activity
     *
     * @param activityId return an Id of an activity
     * @return JsonResponse to the front-end
     */
    public JsonResponse getActivityDetail(String activityId, String requestorId, String requestorKey) throws Exception {
        JsonResponse resp = new JsonResponse();

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
            if (facility != null) {
                activityVO.setFromFacility(facility);
            }
            // Members Info
            List<ActivityMember> activityMembers = activityMemberDaoImpl.getAllActivitymembers(activityId);
            List<UserOutlineVO> members = new ArrayList<UserOutlineVO>();
            for (ActivityMember activityMember : activityMembers) {
                String memberId = activityMember.getUserId();
                Person person = personDaoImpl.getPerson(memberId);
                UserOutlineVO userOutlineVO = new UserOutlineVO();
                userOutlineVO.setFromPerson(person);
                members.add(userOutlineVO);

            }
            activityVO.setMembers(members);
            //Comment Info
            List<ActivityComment> comments = activityCommentDaoImpl.getAllActivityComments(activityId);
            activityVO.setFromComments(comments);

            resp.setResponse("true");
            resp.setActivity(activityVO);

            // check relationship between the user and the requestor
            if (isAuthorized(requestorId, requestorKey)) {
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

        return resp;
    }

    /**
     * Get the outline of an activity
     *
     * @param activityId Id of an activity
     * @return JsonResponse to the front-end
     */
    public JsonResponse getActivityOutline(String activityId) throws Exception {
        JsonResponse resp = new JsonResponse();

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

        return resp;
    }

    /**
     * Get the upcoming activity of a user
     *
     * @param userId Id of a user
     * @param offset The index of the first result to return. Default: 0 (i.e., the first result). Maximum offset: 200. Use with limit to get the next page of search results.
     * @param limit  The maximum number of results to return. Default: 3. Minimum: 1. Maximum: 10.
     * @return Json Response to the front-end
     */
    public JsonResponse getUpcomingActivity(String userId, int offset, int limit) throws Exception {
        JsonResponse resp = new JsonResponse();

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

        return resp;
    }

    /**
     * Get the past activity of a user
     *
     * @param userId Id of a user
     * @param offset The index of the first result to return. Default: 0 (i.e., the first result). Maximum offset: 200. Use with limit to get the next page of search results.
     * @param limit  The maximum number of results to return. Default: 3. Minimum: 1. Maximum: 10.
     * @return Json Response to the front-end
     */
    public JsonResponse getPastActivity(String userId, int offset, int limit) throws Exception {
        JsonResponse resp = new JsonResponse();

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

        return resp;
    }

    /**
     * Get all members of an activity.
     *
     * @param activityId The UUID of the activity.
     * @return JsonResponse object
     */
    public JsonResponse getActivityMembers(String activityId) throws Exception {
        JsonResponse resp = new JsonResponse();

        if (activityDaoImpl.getActivity(activityId) == null) {
            resp.setResponse("false");
            resp.setMessage("No such activity");
            return resp;
        }
        List<UserOutlineVO> userOutlineVOs = new ArrayList<UserOutlineVO>();
        List<ActivityMember> activityMembers = activityMemberDaoImpl.getAllActivitymembers(activityId);
        for (ActivityMember activityMember : activityMembers) {
            UserOutlineVO userOutlineVO = new UserOutlineVO();
            userOutlineVO.setFromPerson(personDaoImpl.getPerson(activityMember.getUserId()));
            userOutlineVOs.add(userOutlineVO);
        }
        resp.setMembers(userOutlineVOs);
        resp.setResponse("true");

        return resp;
    }

    /**
     * Add a member to the activity.
     *
     * @param activityId The UUID of the activity.
     * @param body       The request body from controller.
     * @return JsonResponse object
     */
    public JsonResponse addActivityMember(String activityId, String body) throws Exception {
        JsonResponse resp = new JsonResponse();

        JsonObject json = new Gson().fromJson(body, JsonObject.class);
        String userId = json.get("userId").getAsString();
        ActivityMember activityMember = new ActivityMember(activityId, userId);
        if (activityMemberDaoImpl.hasActivityMember(activityMember)) {
            resp.setResponse("false");
            resp.setMessage("This user is already a member");
        } else if (activityMemberDaoImpl.newActivityMember(activityMember)) {
            resp.setResponse("true");
        }
//        else {
//            resp.setMessage("Unable to create a new entry in database");
//            resp.setResponse("false");
//        }

        return resp;
    }


    /**
     * Remove one member from an activity.
     *
     * @param activityId The UUID of the activity.
     * @param body       The request body from controller.
     * @return JsonResponse object.
     */
    public JsonResponse removeActivityMember(String activityId, String body) throws Exception {
        JsonResponse resp = new JsonResponse();

        JsonObject json = new Gson().fromJson(body, JsonObject.class);
        String userId = json.get("userId").getAsString();
        ActivityMember activityMember = new ActivityMember(activityId, userId);
        if (!activityMemberDaoImpl.hasActivityMember(activityMember)) {
            resp.setResponse("false");
            resp.setMessage("The user is not a member");
        } else if (activityMemberDaoImpl.deleteActivityMember(activityMember)) {
            resp.setResponse("true");
        }
//        else {
//            resp.setMessage("Unable to delete the entry from database");
//            resp.setResponse("false");
//        }

        return resp;
    }

    /**
     * Create a new activity.
     *
     * @param body Json string containing fields of userId, key and activity
     * @return JsonResponse object.
     */
    public JsonResponse newActivity(String body) throws Exception {
        JsonResponse resp = new JsonResponse();

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
            }
//            else {
//                resp.setResponse("false");
//                resp.setMessage("Fail to create a new entry in database");
//            }
        }

        return resp;
    }

    /**
     * Update an activity's info.
     *
     * @param activityId The UUID of an activity.
     * @param body       The Json string from controller, containing an ActivityVO object, requestorId and requestorKey.
     * @return JsonResponse object
     */
    public JsonResponse updateActivity(String activityId, String body) throws Exception {
        JsonResponse resp = new JsonResponse();

        JsonObject json = new Gson().fromJson(body, JsonObject.class);
        String requestorId = json.get("requestorId").getAsString();
        String requestorKey = json.get("requestorKey").getAsString();

        if (!isAuthorized(requestorId, requestorKey) || !requestorId.equals(activityDaoImpl.getActivity(activityId).getCreatorId())) {
            resp.setResponse("false");
            resp.setMessage("Lack authorization to update activity info");
        } else {
            Activity activity = new Gson().fromJson(json.get("activity").getAsJsonObject(), Activity.class);

            // set the location info
            String facilityId = activity.getFacilityId();
            if (!facilityId.equals("NULL")) {
                Facility facility = facilityDaoImpl.getFacility(facilityId);
                activity.setFromFacility(facility);
            }

            boolean isUpdate = activityDaoImpl.updateActivity(activity);
            if (!isUpdate) {
                resp.setResponse("false");
                resp.setMessage("Update failed");
            } else {
                resp.setResponse("true");
            }
        }

//      System.out.println(resp);
        return resp;
    }


    /**
     * Delete an activity.
     *
     * @param activityId   The UUID of the activity.
     * @param requestorId  The email of the requestor of deleting the activity.
     * @param requestorKey The authentication key of the requestor.
     */
    public JsonResponse deleteActivity(String activityId, String requestorId, String requestorKey) throws Exception {
        JsonResponse resp = new JsonResponse();

        if (activityDaoImpl.getActivity(activityId) == null) {
            resp.setResponse("false");
            resp.setMessage("No such activity");
        } else if (!isAuthorized(requestorId, requestorKey) || !requestorId.equals(activityDaoImpl.getActivity(activityId).getCreatorId())) {
            resp.setResponse("false");
            resp.setMessage("Lack authorization to cancel the activity");
        } else {
            boolean isDelete = activityMemberDaoImpl.deleteAllActivityMembers(activityId) && activityDaoImpl.deleteActivity(activityId);
            if (isDelete) {
                resp.setResponse("true");
//                resp.setResponse("false");
//                resp.setMessage("Cancel failed");
            }
//            else {
//                resp.setResponse("true");
//            }
        }

        return resp;
    }

    public JsonResponse searchActivity(int limit, int offset, String body) throws SQLException, ParseException {
        //TODO
        JsonResponse resp = new JsonResponse();
        ActivitySearchVO activitySearchVO = new Gson().fromJson(body, ActivitySearchVO.class);

        List<ActivityOutlineVO> activityOutlineVOs = new ArrayList<ActivityOutlineVO>();
        List<Activity> searchResults = activityDaoImpl.searchActivity(activitySearchVO);
        if (searchResults.size() <= offset) {
            resp.setResponse("true");
            resp.setMessage("No more search results");
            resp.setActivityOutlines(new ArrayList<>());
        } else {
            List<Activity> searchResultsSubset = searchResults.subList(offset, min(offset + limit, searchResults.size()));
            for (Activity resultActivity : searchResultsSubset) {
                Sport sport = sportDaoImpl.getSport(resultActivity.getSportId());
                ActivityOutlineVO activityOutlineVO = new ActivityOutlineVO();
                activityOutlineVO.setFromActivity(resultActivity);
                activityOutlineVO.setFromSport(sport);
                activityOutlineVOs.add(activityOutlineVO);
            }
            resp.setActivityOutlines(activityOutlineVOs);
            resp.setResponse("true");
        }

        return resp;
    }


    public JsonResponse reviewActivity(String activityId, String body) throws Exception {
        JsonResponse response;

        ReviewActivityVO reviewActivityVO = new Gson().fromJson(body, ReviewActivityVO.class);
        String userId = reviewActivityVO.getUserId();
        String key = reviewActivityVO.getKey();

        if (!isAuthorized(userId, key)) {
            return new JsonResponse("not authorized: did you log in?");
        }
        if (!activityMemberDaoImpl.hasActivityMember(new ActivityMember(activityId, userId))) {
            return new JsonResponse("this user is not a member of the activity");
        } else {
            ArrayList<JsonResponse> allResponse = new ArrayList<>();
            FacilityReviewVO facilityReviewVO = reviewActivityVO.getFacilityReviewVO();
            if (facilityReviewVO != null) {
                allResponse.add(new FacilityService().reviewFacility(facilityReviewVO));
            }
            ArrayList<UserReviewVO> userReviewVOS = reviewActivityVO.getUserReviewVOs();
            if (userReviewVOS != null) {
                for (UserReviewVO userReviewVO :
                        reviewActivityVO.getUserReviewVOs()) {
                    allResponse.add(new ProfileService().reviewPerson(userReviewVO));
                }
            }
            response = JsonResponse.combineBinaryJsonResponses(allResponse);
        }
        return response;
    }

    public JsonResponse leaveActivity(String activityId, String userId, String key) throws SQLException {
        JsonResponse response = new JsonResponse();

        if (!isAuthorized(userId, key)) {
            return new JsonResponse("not authorized: did you log in?");
        }
        if (!activityMemberDaoImpl.hasActivityMember(new ActivityMember(activityId, userId))) {
            return new JsonResponse("this user is not a member of the activity");
        } else {
            Boolean boolResult = activityMemberDaoImpl.deleteActivityMember(new ActivityMember(activityId, userId));
            if (boolResult){
                response.setResponse("true");
            }
            else {
                response.setResponse("false");
            }
        }

        return response;
    }
//    public void activityInfoUpdate(){
//        //TODO
//    }
//    public void userIdentityCheck(){
//        //TODO
//    }
//    public void addActivityDiscussion(){
//        //TODO
//    }

    /**
     * Check whether a user exists
     *
     * @param userId Id of a user
     * @return true means the user exists,  false means the user doesn't exist
     */
    public boolean hasUser(String userId) throws SQLException {
        User user = userDaoImpl.getUser(userId);
        return user != null;
    }

    /**
     * Check whether an activity exists
     *
     * @param activityId Id of an activity
     * @return true means the user exists,  false means the user doesn't exist
     */
    public boolean hasActivity(String activityId) throws SQLException {
        Activity activity = activityDaoImpl.getActivity(activityId);
        return activity != null;

    }

    /**
     * Check whether a user is authorized
     *
     * @param userId Id of a user
     * @param key    login key of a user
     * @return true means the user is authorized,  false means the user isn't authorized
     */
    public boolean isAuthorized(String userId, String key) throws SQLException {
        Authorization authorization = new Authorization(userId, key);
        AuthorizationDaoImpl authorizationDaoImpl = new AuthorizationDaoImpl();
        return authorizationDaoImpl.hasAuthorization(authorization);
    }

}
