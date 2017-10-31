package com.sportspartner.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sportspartner.dao.impl.*;
import com.sportspartner.model.*;
import com.sportspartner.modelvo.*;
import com.sportspartner.util.JsonResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProfileService {
    private PersonDaoImpl personDaoImpl = new PersonDaoImpl();
    public JsonResponse getProfile(String userId) throws ProfileServiceException {

        JsonResponse resp = new JsonResponse();
        try {

            if (!hasUser(userId)) {
                resp.setResponse("false");
                resp.setMessage("No such user");
            }else{
                PersonVO personVO = new PersonVO();
                Person person = personDaoImpl.getPerson(userId);
                personVO.setFromPerson(person);
                if(personVO.isMissingField()) {
                    resp.setResponse("false");
                    resp.setMessage("UserId is missing");
                }else{
                    resp.setResponse("true");
                    resp.setProfile(personVO);
                }
            }
        } catch(Exception ex){
            throw new ProfileServiceException("Json format error", ex);
        }
//      System.out.println(resp);
        return resp;
    }

    public JsonResponse updateProfile(String userId, String body) throws ProfileServiceException {

        JsonResponse resp = new JsonResponse();
        try {
            JsonObject json = new Gson().fromJson(body, JsonObject.class);
            String requestId = json.get("userId").getAsString();
            String requestKey = json.get("key").getAsString();
            if (!userId.equals(requestId)) {
                resp.setResponse("false");
                resp.setMessage("Lack authorization to update profile");
                resp.setAuthorization("false");
            } else {
                if (!hasUser(userId)) {
                    resp.setResponse("false");
                    resp.setMessage("No such user");
                } else {
                    if (!isAuthorized(requestId, requestKey)) {
                        resp.setAuthorization("false");
                        resp.setResponse("false");
                        resp.setMessage("Lack authorization to update profile");
                    } else {
                        resp.setAuthorization("true");
                        PersonVO personVO = new Gson().fromJson(json.get("profile").getAsJsonObject(), PersonVO.class);
                        Person person = personDaoImpl.getPerson(personVO.getUserId());
                        // Update the fields that are members of personVO
                        personVO.cast2Person(person);
                        boolean isUpdate = personDaoImpl.updatePerson(person);
                        if (!isUpdate) {
                            resp.setResponse("false");
                            resp.setMessage("Update failed");
                        } else {
                            resp.setResponse("true");
                        }
                        }
                    }
                }
        }catch(Exception ex){
            throw new ProfileServiceException("Json format error", ex);
        }
//      System.out.println(resp);
        return resp;
    }

    public JsonResponse getInterests(String userId) throws ProfileServiceException {
        /* Return the interests of a user as a comma-separated string */
        JsonResponse resp = new JsonResponse();
        try{
            if(!hasUser(userId)){
                resp.setResponse("false");
                resp.setMessage("No such user");
            }
            else{
                InterestDaoImpl interestDaoImpl = new InterestDaoImpl();
                List<Interest> interests = interestDaoImpl.getInterest(userId);
                List<String> interestNames = new ArrayList<String>();
                SportDaoImpl sportDaoImpl = new SportDaoImpl();
                for(Interest interest : interests){
                    interestNames.add(sportDaoImpl.getSport(interest.getSportId()).getSportName());
                }
                resp.setInterest(interestNames);
            }
        }catch(Exception ex){
            throw new ProfileServiceException("Get interests error", ex);
        }
        return resp;
    }

    public JsonResponse updateInterest(String userId, String body) throws ProfileServiceException {
        JsonResponse resp = new JsonResponse();
        try{
            if(!hasUser(userId)){
                resp.setResponse("false");
                resp.setMessage("no such user");
            }
            else{
                // Convert the comma separated string for sportId to list
                JsonObject json = new Gson().fromJson(body, JsonObject.class);
                String sportIdString = json.get("interests").getAsString();
                String []sportIds = sportIdString.split(",");
                // Delete the old interests of a user
                InterestDaoImpl interestDaoImpl = new InterestDaoImpl();
                List<Interest> interests = interestDaoImpl.getInterest(userId);
                for(Interest interest: interests){
                    interestDaoImpl.deleteInterest(interest);
                }
                // Update interests for the user
                for(String sportId : sportIds){
                    Interest interest = new Interest(userId, sportId);
                    interestDaoImpl.newInterest(interest);
                }
                resp.setResponse("true");
            }
        } catch(Exception ex){
            throw new ProfileServiceException("Update interests error", ex);
        }
        return resp;
    }

    public JsonResponse getProfileComment(String userId) throws ProfileServiceException{
        JsonResponse resp = new JsonResponse();
        try{
            if(!hasUser(userId)){
                resp.setResponse("false");
                resp.setMessage("No such user");
            }else{
                ProfileCommentDaoImpl profileCommentDaoImpl = new ProfileCommentDaoImpl();
                List<ProfileComment> profileComments = profileCommentDaoImpl.getAllProfileComments(userId);
                resp.setProfileComments(profileComments);
                resp.setResponse("true");
            }
        }catch(Exception ex){
            throw new ProfileServiceException("Get user outline error", ex);
        }
//      System.out.println(resp);
        return resp;
    }

    public JsonResponse newProfileComment(String userId, String body) throws ProfileServiceException {
        JsonResponse resp = new JsonResponse();
        try{
            JsonObject json = new Gson().fromJson(body, JsonObject.class);
            String authorId = json.get("authorId").getAsString();
            String key = json.get("key").getAsString();
            String activityId = json.get("activityId").getAsString();
            JsonObject commentJson = json.get("comment").getAsJsonObject();
            ProfileComment profileComment = new Gson().fromJson(commentJson, ProfileComment.class);
            ProfileCommentDaoImpl profileCommentDaoImpl = new ProfileCommentDaoImpl();
            String commentUUID = UUID.randomUUID().toString();
            profileComment.setCommentId(commentUUID);

            if(!hasUser(userId) || !hasUser(authorId)) {
                resp.setResponse("false");
                resp.setMessage("No such user");
            }else if((!isAuthorized(authorId, key)) || (!isMember(authorId, activityId)) || (userId.equals(authorId))){
                resp.setResponse("false");
                resp.setMessage("Lack authorization to leave a comment");
            }else{
                if(profileCommentDaoImpl.newProfileComment(profileComment)){
                    resp.setResponse("true");
                }else{
                    resp.setResponse("false");
                    resp.setMessage("Failed to create a new comment");
                }
            }
        }catch(Exception ex){
            throw new ProfileServiceException("Get user outline error", ex);
        }
//      System.out.println(resp);
        return resp;
    }

    public JsonResponse getUserOutline(String userId) throws ProfileServiceException {

        JsonResponse resp = new JsonResponse();
        try {
            if(!hasUser(userId)){
                resp.setResponse("false");
                resp.setMessage("No such user");
            } else{
                PersonDaoImpl  personDaoImpl = new PersonDaoImpl();
                Person person = personDaoImpl.getPerson(userId);
                UserOutlineVO userOutlineVO = new UserOutlineVO();
                userOutlineVO.setFromPerson(person);
                resp.setUserOutline(userOutlineVO);
                resp.setResponse("true");
            }
        } catch(Exception ex){
            throw new ProfileServiceException("Get user outline error", ex);
        }
//      System.out.println(resp);
        return resp;
    }

    public JsonResponse getAllSports() throws ProfileServiceException{
        JsonResponse resp = new JsonResponse();
        try {
            List<Sport> sports = new SportDaoImpl().getAllSports();
            resp.setSports(sports);
            resp.setResponse("true");
        } catch(Exception ex){
            throw new ProfileServiceException("Get all sports error", ex);
        }
//      System.out.println(resp);
        return resp;
    }

    public boolean hasUser(String userId){
        UserDaoImpl userDaoImpl = new UserDaoImpl();
        User user = userDaoImpl.getUser(userId);
        return user!= null;
    }

    public boolean isAuthorized(String userId, String key){
        Authorization authorization = new Authorization(userId, key);
        AuthorizationDaoImpl authorizationDaoImpl = new AuthorizationDaoImpl();
        return authorizationDaoImpl.hasAuthorization(authorization);
    }

    public boolean isMember(String userId, String activityId){
        ActivityMemberDaoImpl activityMemberDaoimpl = new ActivityMemberDaoImpl();
        ActivityMember activityMember = new ActivityMember(activityId, userId);
        return activityMemberDaoimpl.hasActivityMember(activityMember);
    }

    public static class ProfileServiceException extends Exception {
        public ProfileServiceException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}