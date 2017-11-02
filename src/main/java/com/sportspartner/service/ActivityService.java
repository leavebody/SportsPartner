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
                // Members Info
                List<ActivityMember> members = activityMemberDaoImpl.getAllActivitymembers(activityId);
                activityVO.setFromMembers(members, activity.getCreatorId());
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

    public boolean hasUser(String userId){
        User user = userDaoImpl.getUser(userId);
        return user!=null;
    }
    public boolean hasActivity(String activityId){
        Activity activity = activityDaoImpl.getActivity(activityId);
        return activity!= null;

    }
    public static class ActivityServiceException extends Exception {
        public ActivityServiceException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
