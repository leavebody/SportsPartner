package com.sportspartner.util;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.json.JSONException;
import com.sportspartner.model.*;
import com.sportspartner.modelvo.*;

import java.util.ArrayList;
import java.util.List;

/**
 *  Class for sending the response back to the front-end
 */
public class JsonResponse {
    private String response;
    private String message;
    private String key;
    private String userId;
    private String password;
    private JsonObject profile;
    private JsonObject userOutline;
    private JsonObject activity;
    private String type;
    private FacilityOutlineVO facilityOutline;

    private JsonArray interests;
    private String authorization;
    private JsonArray profileComments;
    private JsonArray sports;
    private JsonObject activityOutline;
    private JsonArray activityOutlines;
    private JsonArray friendlist;

    private String image;
    private String iconUUID;
    private String userType;
    private JsonArray members;
    private String activityId;

    private JsonArray facilities;

    public JsonResponse() {
    }

    public JsonResponse(boolean response){
        if (response){
            setResponse("true");
        }else{
            setResponse("false");
            setMessage("default error message");
        }
    }

    public JsonResponse(String errorMessage) {
        this.response = "false";
        this.message = errorMessage;
    }

    public JsonResponse(String response, String message) {
        this.response = response;
        this.message = message;
    }

    public void setProfile(JsonObject profile) {
        this.profile = profile;
    }

    public void setUserOutline(JsonObject userOutline) {
        this.userOutline = userOutline;
    }

    public void setActivity(JsonObject activity) {
        this.activity = activity;
    }

    public FacilityOutlineVO getFacilityOutline() {
        return facilityOutline;
    }

    public void setFacilityOutline(FacilityOutlineVO facilityOutline) {
        this.facilityOutline = facilityOutline;
    }

    public void setInterests(JsonArray interests) {
        this.interests = interests;
    }

    public void setProfileComments(JsonArray profileComments) {
        this.profileComments = profileComments;
    }

    public JsonArray getSports() {
        return sports;
    }

    public void setSports(JsonArray sports) {
        this.sports = sports;
    }

    public JsonObject getActivityOutline() {
        return activityOutline;
    }

    public void setActivityOutline(JsonObject activityOutline) {
        this.activityOutline = activityOutline;
    }

    public JsonArray getActivityOutlines() {
        return activityOutlines;
    }

    public void setActivityOutlines(JsonArray activityOutlines) {
        this.activityOutlines = activityOutlines;
    }

    public JsonArray getFriendlist() {
        return friendlist;
    }

    public void setFriendlist(JsonArray friendlist) {
        this.friendlist = friendlist;
    }

    public JsonArray getMembers() {
        return members;
    }

    public void setMembers(JsonArray members) {
        this.members = members;
    }

    public void setFacilities(JsonArray facilities) {
        this.facilities = facilities;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public JsonObject getProfile() {
        return profile;
    }


    public void setProfile(PersonVO personVO){
        Gson gson = new Gson();
        try {
            String jsonString = gson.toJson(personVO);
            this.profile = gson.fromJson(jsonString, JsonObject.class);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
        }

    }

    public JsonObject getUserOutline(){return userOutline;}

    public void setUserOutline(UserOutlineVO userOutlineVO){
        Gson gson = new Gson();
        try {
            String jsonString = gson.toJson(userOutlineVO);
            this.userOutline = gson.fromJson(jsonString, JsonObject.class);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
        }
    }

    public JsonArray getInterests() {
        return interests;
    }

    public void setInterests(List<Sport> sports) {
        this.interests = new JsonArray();
        Gson gson = new Gson();
        try{
            for(Sport sport: sports){
                String jsonString = gson.toJson(sport);
                this.interests.add(gson.fromJson(jsonString, JsonObject.class));
            }
        }catch(JSONException e){
            // TODO Auto-generated catch block
        }
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public JsonArray getProfileComments(){return profileComments;}

    public void setProfileComments(List<ProfileComment> profileComments){
        this.profileComments = new JsonArray();
        Gson gson = new Gson();
        try {
            for(ProfileComment profileComment: profileComments){
                String jsonString = gson.toJson(profileComment);
                this.profileComments.add(gson.fromJson(jsonString, JsonObject.class));
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
        }
    }

    public JsonObject getActivity() {
        return activity;
    }

    public void setActivity(ActivityVO activityVO) {
        Gson gson = new Gson();
        try {
            String jsonString = gson.toJson(activityVO);
            this.activity = gson.fromJson(jsonString, JsonObject.class);
        } catch (JSONException e) {
        // TODO Auto-generated catch block
        }
    }

    public void setSports(List<Sport> sports){
        this.sports = new JsonArray();
        Gson gson = new Gson();
        try {
            for(Sport sport: sports){
                String jsonString = gson.toJson(sport);
                this.sports.add(gson.fromJson(jsonString, JsonObject.class));
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
        }
    }

    public void setActivityOutline(ActivityOutlineVO activityOutlineVO){
        Gson gson = new Gson();
        try {
            String jsonString = gson.toJson(activityOutlineVO);
            this.activityOutline = gson.fromJson(jsonString, JsonObject.class);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
        }
    }

    public void setActivityOutlines(List<ActivityOutlineVO> activityOutlineVOs){
        this.activityOutlines = new JsonArray();
        Gson gson = new Gson();
        try{
            for(ActivityOutlineVO activityOutlineVO: activityOutlineVOs){
                String jsonString = gson.toJson(activityOutlineVO);
                this.activityOutlines.add(gson.fromJson(jsonString, JsonObject.class));
            }
        }catch(JSONException e){
            // TODO Auto-generated catch block
        }
    }

    public void setMembers(List<UserOutlineVO> userOutlineVOs){
        this.members = new JsonArray();
        Gson gson = new Gson();
        try{
            for(UserOutlineVO userOutlineVO: userOutlineVOs){
                String jsonString = gson.toJson(userOutlineVO);
                this.members.add(gson.fromJson(jsonString, JsonObject.class));
            }
        }catch(JSONException e){
            // TODO Auto-generated catch block
        }
    }

    public void setFriendlist( List <UserOutlineVO> userOutlineVOList){
        this.friendlist = new JsonArray();
        Gson gson = new Gson();
        try{
            for (UserOutlineVO userOutlineVO : userOutlineVOList){
                String jsonString = gson.toJson(userOutlineVO);
                this.friendlist.add(gson.fromJson(jsonString, JsonObject.class));
            }
        }catch(JSONException e){
            // TODO Auto-generated catch block
        }
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIconUUID() {
        return iconUUID;
    }

    public void setIconUUID(String iconUUID) {
        this.iconUUID = iconUUID;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public JsonArray getFacilities() {
        return facilities;
    }

    public void setFacilities(List<FacilityMarkerVO> facilities) {
        this.facilities = new JsonArray();
        Gson gson = new Gson();
        try{
            for (FacilityMarkerVO facilityMarkerVO : facilities){
                String jsonString = gson.toJson(facilityMarkerVO);
                this.facilities.add(gson.fromJson(jsonString, JsonObject.class));
            }
        }catch(JSONException e){
            // TODO Auto-generated catch block
        }
    }

    public static JsonResponse combineBinaryJsonResponses(ArrayList<JsonResponse> jsonResponses) {
        JsonResponse result = new JsonResponse();
        result.setResponse("true");
        for (JsonResponse jsonResponse:
             jsonResponses) {
            if (jsonResponse.getResponse().equals("false")) {
                result.setResponse("false");
                result.setMessage(jsonResponse.getMessage());
                break;
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "JsonResponse{" +
                "response='" + response + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
