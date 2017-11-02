package com.sportspartner.service;
import com.sportspartner.dao.impl.*;
import com.sportspartner.model.*;
import com.sportspartner.modelvo.*;
import com.sportspartner.util.JsonResponse;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.min;

public class ActivityService {
    private  ActivityDaoImpl activityDaoImpl = new ActivityDaoImpl();
    private ActivityMemberDaoImpl activityMemberDaoImpl = new ActivityMemberDaoImpl();
    private ActivityCommentDaoImpl activityCommentDaoImpl = new ActivityCommentDaoImpl();
    private SportDaoImpl sportDaoImpl = new SportDaoImpl();
    private UserDaoImpl userDaoImpl = new UserDaoImpl();
    private PersonDaoImpl personDaoImpl = new PersonDaoImpl();
    private FacilityDaoImpl facilityDaoImpl = new FacilityDaoImpl();

    /**
     * Get the detail of an activity
     * @param activityId return an Id of an activity
     * @return JsonResponse to the front-end
     * @throws ActivityServiceException throws ActivityServiceException
     */
    public JsonResponse getActivityDetail(String activityId) throws ActivityServiceException{
        JsonResponse resp = new JsonResponse();
        try {
            if (!hasActivity(activityId)) {
                resp.setResponse("false");
                resp.setMessage("No such activity");
            }
            else {
                ActivityVO activityVO = new ActivityVO();
                // Basic Info
                Activity activity = activityDaoImpl.getActivity(activityId);
                activityVO.setFromActivity(activity);
                // Sports Info
                Sport sport = sportDaoImpl.getSport(activity.getSportId());
                activityVO.setFromSport(sport);
                // Facility Info
                Facility facility = facilityDaoImpl.getFacility(activity.getFacilityId());
                activityVO.setFromFacility(facility);
                // Members Info
                List<ActivityMember> activityMembers = activityMemberDaoImpl.getAllActivitymembers(activityId);
                List<UserOutlineVO> members = new ArrayList<UserOutlineVO>();
                for(ActivityMember activityMember:activityMembers){
                    String memberId = activityMember.getUserId();
                    if(!activityVO.getCreatorId().equals(memberId)){
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
            }
        }
        catch(Exception ex){
            throw new ActivityServiceException("Json format error", ex);
        }
        return resp;
    }

    /**
     * Get the outline of an activity
     * @param activityId Id of an activity
     * @return JsonResponse to the front-end
     * @throws ActivityServiceException throws ActivityServiceException
     */
    public JsonResponse getActivityOutline(String activityId) throws ActivityServiceException{
        JsonResponse resp = new JsonResponse();
        try{
            ActivityOutlineVO activityOutlineVO = new ActivityOutlineVO();
            if(!hasActivity(activityId)){
                resp.setResponse("false");
                resp.setMessage("No such activity");
            }else{
                Activity activity = activityDaoImpl.getActivity(activityId);
                Sport sport = sportDaoImpl.getSport(activity.getSportId());
                activityOutlineVO.setFromActivity(activity);
                activityOutlineVO.setFromSport(sport);
                resp.setActivityOutline(activityOutlineVO);
                resp.setResponse("true");
            }
        }catch(Exception ex){
            throw new ActivityServiceException("Json format error", ex);
        }
        return resp;
    }

    /**
     * Get the upcoming activity of a user
     * @param userId Id of a user
     * @param offset The index of the first result to return. Default: 0 (i.e., the first result). Maximum offset: 200. Use with limit to get the next page of search results.
     * @param limit The maximum number of results to return. Default: 3. Minimum: 1. Maximum: 10.
     * @return Json Response to the front-end
     * @throws ActivityServiceException throws ActivityServiceException
     */
    public JsonResponse getUpcomingActivity(String userId, int offset, int limit) throws ActivityServiceException{
        JsonResponse resp = new JsonResponse();
        try{
            if(!hasUser(userId)){
                resp.setResponse("false");
                resp.setMessage("No such user");
            }else{
                List<ActivityOutlineVO> activityOutlineVOs = new ArrayList<ActivityOutlineVO>();
                List<Activity> upcomingActivities = activityDaoImpl.getUpcomingActivities(userId);
                if(upcomingActivities.size()<=offset){
                    resp.setResponse("true");
                    resp.setMessage("No more upcoming activities");
                    resp.setActivityOutlines(new ArrayList<>());
                }else{
                    List<Activity> upcomingActivitiesSubset = upcomingActivities.subList(offset, min(offset+limit,upcomingActivities.size()));
                    for(Activity upcomingActivity: upcomingActivitiesSubset){
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
        }catch(Exception ex){
            throw new ActivityServiceException("Json format error", ex);
        }
        return resp;
    }
    /**
     * Get the past activity of a user
     * @param userId Id of a user
     * @param offset The index of the first result to return. Default: 0 (i.e., the first result). Maximum offset: 200. Use with limit to get the next page of search results.
     * @param limit The maximum number of results to return. Default: 3. Minimum: 1. Maximum: 10.
     * @return Json Response to the front-end
     * @throws ActivityServiceException throws ActivityServiceException
     */
    public JsonResponse getPastActivity(String userId, int offset, int limit) throws ActivityServiceException{
        JsonResponse resp = new JsonResponse();
        try{
            if(!hasUser(userId)){
                resp.setResponse("false");
                resp.setMessage("No such user");
            }else{
                List<ActivityOutlineVO> activityOutlineVOs = new ArrayList<ActivityOutlineVO>();
                List<Activity> pastActivities = activityDaoImpl.getPastActivities(userId);
                if(pastActivities.size()<=offset){
                    resp.setResponse("true");
                    resp.setMessage("No more past activities");
                    resp.setActivityOutlines(new ArrayList<>());
                }else {
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
        }catch(Exception ex){
            throw new ActivityServiceException("Json format error", ex);
        }
        return resp;
    }



    public void createActivity(){
        //TODO
    }
    public void searchActivity(){
        //TODO
    }
    public void joinActivity(){
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
     *  Exception class for Activity
     */
    public static class ActivityServiceException extends Exception {
        public ActivityServiceException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
