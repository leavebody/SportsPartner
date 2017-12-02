package com.sportspartner.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sportspartner.dao.ProfileCommentDao;
import com.sportspartner.dao.impl.*;
import com.sportspartner.model.*;
import com.sportspartner.modelvo.*;
import com.sportspartner.util.JsonResponse;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


public class ProfileService {
    private PersonDaoImpl personDaoImpl = new PersonDaoImpl();
    private FriendDaoImpl friendDaoImpl = new FriendDaoImpl();
    private ProfileCommentDao profileCommentDao = new ProfileCommentDaoImpl();

    /**
     * Get the profile of a user
     *
     * @param userId Id of a user
     * @return JsonResponse to the front-end
     */
    public JsonResponse getProfile(String userId, String requestorId, String requestorKey) throws Exception {

        JsonResponse resp = new JsonResponse();

        if (!hasUser(userId) || !hasUser(requestorId)) {
            resp.setResponse("false");
            resp.setMessage("No such user");
        } else {
            PersonVO personVO = new PersonVO();
            Person person = personDaoImpl.getPerson(userId);
            personVO.setFromPerson(person);
            if (personVO.isMissingField()) {
                resp.setResponse("false");
                resp.setMessage("UserId is missing");
            } else {
                resp.setResponse("true");
                resp.setProfile(personVO);
            }
            // check relationship between the user and the requestor
            if (isAuthorized(requestorId, requestorKey)) {
                if (requestorId.equals(userId)) {
                    resp.setUserType("SELF");
                    return resp;
                } else if (friendDaoImpl.getFriend(userId, requestorId)
                        || friendDaoImpl.getFriend(requestorId, userId)) {
                    resp.setUserType("FRIEND");
                    return resp;
                }
            }
            resp.setUserType("STRANGER");
        }

        return resp;
    }

    /**
     * Update profile of a user
     *
     * @param userId Id of a user
     * @param body   String from the front-end
     * @return JsonResponse to the front-end
     */
    public JsonResponse updateProfile(String userId, String body) throws Exception {

        JsonResponse resp = new JsonResponse();

        JsonObject json = new Gson().fromJson(body, JsonObject.class);
        String requestId = json.get("userId").getAsString();
        String requestKey = json.get("key").getAsString();
        // Check
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

//      System.out.println(resp);
        return resp;
    }

    /**
     * Get all the interests of a user
     *
     * @param userId Id of a user
     * @return JsonResponse to the front-end
     */
    public JsonResponse getInterests(String userId) throws Exception {
        /* Return the interests of a user as a comma-separated string */
        JsonResponse resp = new JsonResponse();

        if (!hasUser(userId)) {
            resp.setResponse("false");
            resp.setMessage("No such user");
        } else {
            InterestDaoImpl interestDaoImpl = new InterestDaoImpl();
            List<Interest> interests = interestDaoImpl.getInterest(userId);
            List<Sport> sports = new ArrayList<Sport>();
            SportDaoImpl sportDaoImpl = new SportDaoImpl();
            for (Interest interest : interests) {
                sports.add(sportDaoImpl.getSport(interest.getSportId()));
            }
            resp.setInterests(sports);
            resp.setResponse("true");
        }

        return resp;
    }

    public JsonResponse updateInterest(String userId, String body) throws Exception {
        JsonResponse resp = new JsonResponse();
        if (!hasUser(userId)) {
            resp.setResponse("false");
            resp.setMessage("no such user");
        } else {
            // Convert the comma separated string for sportId to list
            JsonObject json = new Gson().fromJson(body, JsonObject.class);
            String sportIdString = json.get("interests").getAsString();
            String[] sportIds = sportIdString.split(",");
            // Delete the old interests of a user
            InterestDaoImpl interestDaoImpl = new InterestDaoImpl();
            List<Interest> interests = interestDaoImpl.getInterest(userId);
            for (Interest interest : interests) {
                interestDaoImpl.deleteInterest(interest);
            }
            // Update interests for the user
            for (String sportId : sportIds) {
                Interest interest = new Interest(userId, sportId);
                interestDaoImpl.newInterest(interest);
            }
            resp.setResponse("true");
        }
        return resp;
    }

    /**
     * Get all comments on the profile os a user
     *
     * @param userId Id of user
     * @return JsonResponse to the front-end
     */
    public JsonResponse getProfileComment(String userId) throws Exception {
        JsonResponse resp = new JsonResponse();

        if (!hasUser(userId)) {
            resp.setResponse("false");
            resp.setMessage("No such user");
        } else {
            ProfileCommentDaoImpl profileCommentDaoImpl = new ProfileCommentDaoImpl();
            List<ProfileComment> profileComments = profileCommentDaoImpl.getAllProfileComments(userId);
            resp.setProfileComments(profileComments);
            resp.setResponse("true");
        }
        return resp;
    }

    /**
     * Create a comment on profile for a User
     *
     * @param userId Id of the target User
     * @param body   String from the front-end, which contains the information about the authorId, login key, activityId and comment
     * @return JsonResponse to the front-end
     */
    public JsonResponse newProfileComment(String userId, String body) throws Exception {
        JsonResponse resp = new JsonResponse();

        JsonObject json = new Gson().fromJson(body, JsonObject.class);
        String authorId = json.get("authorId").getAsString();
        String key = json.get("key").getAsString();
        String activityId = json.get("activityId").getAsString();
        JsonObject commentJson = json.get("comment").getAsJsonObject();
        ProfileComment profileComment = new Gson().fromJson(commentJson, ProfileComment.class);
        ProfileCommentDaoImpl profileCommentDaoImpl = new ProfileCommentDaoImpl();
        String commentUUID = UUID.randomUUID().toString();
        profileComment.setCommentId(commentUUID);

        if (!hasUser(userId) || !hasUser(authorId)) {
            resp.setResponse("false");
            resp.setMessage("No such user");
        } else if ((!isAuthorized(authorId, key)) || (!isMember(authorId, activityId)) || (userId.equals(authorId))) {
            resp.setResponse("false");
            resp.setMessage("Lack authorization to leave a comment");
        } else {
                if(profileCommentDaoImpl.newProfileComment(profileComment)){
                    resp.setResponse("true");
                }else{
                    resp.setResponse("false");
                    resp.setMessage("Failed to create a new comment");
                }
        }

//      System.out.println(resp);
        return resp;
    }

    /**
     * Get the profile outline of a user
     *
     * @param userId Id of a user
     * @return JsonResponse to the front-end
     */
    public JsonResponse getUserOutline(String userId) throws Exception {

        JsonResponse resp = new JsonResponse();

        if (!hasUser(userId)) {
            resp.setResponse("false");
            resp.setMessage("No such user");
        } else {
            PersonDaoImpl personDaoImpl = new PersonDaoImpl();
            Person person = personDaoImpl.getPerson(userId);
            UserOutlineVO userOutlineVO = new UserOutlineVO();
            userOutlineVO.setFromPerson(person);
            resp.setUserOutline(userOutlineVO);
            resp.setResponse("true");
        }

        return resp;
    }

    public JsonResponse reviewPerson(UserReviewVO userReviewVO) throws Exception {
        String userId = userReviewVO.getReviewee();

        Person person = personDaoImpl.getPerson(userId);
        if (person == null) {
            return new JsonResponse("false", "no such user: " + userId);
        }
        {
            double scorePaBefore = person.getParticipation();
            int countPaBefore = person.getParticipationCount();
            person.setParticipation((scorePaBefore * countPaBefore + userReviewVO.getParticipation()) / (countPaBefore + 1));
            person.setParticipationCount(countPaBefore + 1);
        }
        {
            double scorePuBefore = person.getPunctuality();
            int countPuBefore = person.getPunctualityCount();
            person.setPunctuality((scorePuBefore * countPuBefore + userReviewVO.getPunctuality()) / (countPuBefore + 1));
            person.setPunctualityCount(countPuBefore + 1);
        }
        personDaoImpl.updatePerson(person);

        ProfileComment profileComment = new ProfileComment();

        profileComment.setAuthorId(userReviewVO.getReviewer());
        profileComment.setCommentId(UUID.randomUUID().toString());
        profileComment.setContent(userReviewVO.getComments());
        profileComment.setTime(new Date());
        profileComment.setUserId(userReviewVO.getReviewee());
        profileComment.setAuthorName(personDaoImpl.getPerson(userReviewVO.getReviewer()).getUserName());
        profileCommentDao.newProfileComment(profileComment);


        return new JsonResponse(true);
    }


    /**
     * Get information about all of the sports
     *
     * @return JsonResponse to the front-end
     */
    public JsonResponse getAllSports() throws Exception {
        JsonResponse resp = new JsonResponse();

        List<Sport> sports = new SportDaoImpl().getAllSports();
        resp.setSports(sports);
        resp.setResponse("true");

        return resp;
    }

    /**
     * Check whether a user exists
     *
     * @param userId Id of a user
     * @return true means the user exists,  false means the user doesn't exist
     */
    public boolean hasUser(String userId) throws SQLException {
        UserDaoImpl userDaoImpl = new UserDaoImpl();
        User user = userDaoImpl.getUser(userId);
        return user != null;
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

    /**
     * Check whether a user is a member of the activity
     *
     * @param userId     Id of a user
     * @param activityId Id of an activity
     * @return true means the user is a member,  false means the user is not
     */
    public boolean isMember(String userId, String activityId) throws SQLException {
        ActivityMemberDaoImpl activityMemberDaoimpl = new ActivityMemberDaoImpl();
        ActivityMember activityMember = new ActivityMember(activityId, userId);
        return activityMemberDaoimpl.hasActivityMember(activityMember);
    }


}
