package com.sportspartner.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sportspartner.model.ProfileComment;
import com.sportspartner.model.Sport;
import com.sportspartner.modelvo.ActivityVO;
import com.sportspartner.modelvo.FacilityMarkerVO;
import com.sportspartner.modelvo.PersonVO;
import com.sportspartner.modelvo.UserOutlineVO;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by xuanzhang on 11/29/17.
 */
public class JsonResponseTest {
    @BeforeClass
    public static void setUpBeforeClass() throws Exception{
    }

    @AfterClass
    public static void tearDownAfterClass()throws Exception{
    }

    @Before
    public void setUp(){}

    @After
    public void teardown(){
    }

    /**
     * Test when JsonResponse is called
     */
    @Test
    public void testJsonResponse(){
        JsonResponse resp = new JsonResponse();
        resp.setType("PERSON");
        String type = resp.getType();
        resp.setMessage("HELLO");
        String message = resp.getMessage();
        resp.setKey("key");
        String key = resp.getKey();
        resp.setUserId("001");
        String userId = resp.getUserId();
        resp.setPassword("pass");
        String password = resp.getPassword();
        PersonVO personVO = new PersonVO();
        personVO.setUserId("personId");
        resp.setProfile(personVO);
        JsonObject profile = resp.getProfile();
        UserOutlineVO userOutlineVO = new UserOutlineVO();
        userOutlineVO.setUserId("userOutlineId");
        resp.setUserOutline(userOutlineVO);
        JsonObject userOutline = resp.getUserOutline();
        Sport sport = new Sport();
        sport.setSportId("001");
        List<Sport> sports = new ArrayList<>();
        sports.add(sport);
        resp.setInterests(sports);
        JsonArray interests = resp.getInterests();
        resp.setAuthorization("asd");
        String authorization =resp.getAuthorization();
        ProfileComment profileComment = new ProfileComment();
        profileComment.setUserId("009");
        List<ProfileComment> profileComments = new ArrayList<>();
        profileComments.add(profileComment);
        resp.setProfileComments(profileComments);
        JsonArray comments = resp.getProfileComments();
        ActivityVO activityVO = new ActivityVO();
        activityVO.setActivityId("008");
        resp.setActivity(activityVO);
        JsonObject activity = resp.getActivity();
        resp.setImage("image");
        String image = resp.getImage();
        resp.setIconUUID("uuid");
        String iconUUID = resp.getIconUUID();
        resp.setUserType("STRANGE");
        String userType = resp.getUserType();
        resp.setActivityId("004");
        String activityId = resp.getActivityId();
        FacilityMarkerVO facilityMarkerVO = new FacilityMarkerVO();
        facilityMarkerVO.setFacilityId("005");
        List<FacilityMarkerVO> facilityMarkerVOs = new ArrayList<>();
        facilityMarkerVOs.add(facilityMarkerVO);
        resp.setFacilities(facilityMarkerVOs);
        JsonArray facilities = resp.getFacilities();

        JsonResponse r1 = new JsonResponse("error");
        JsonResponse r2 = new JsonResponse("false", "error");
        r1.setProfile(new JsonObject());
        r2.setUserOutline(new JsonObject());
        r1.getUserOutline();
        r1.setActivity(new JsonObject());
        r1.setInterests(new JsonArray());
        r1.setProfileComments(new JsonArray());
        r1.getSports();
        r1.setSports(new JsonArray());
        r1.setActivityOutline(new JsonObject());
        r1.getActivityOutlines();
        r1.setActivityOutlines(new JsonArray());
        r1.getFriendlist();
        r1.setFriendlist(new JsonArray());
        r1.getMembers();
        r1.setMembers(new JsonArray());
        r1.setFacilities(new JsonArray());
        r1.toString();
        ArrayList<JsonResponse> jslist = new ArrayList<>();
        jslist.add(new JsonResponse(false));
        JsonResponse.combineBinaryJsonResponses(jslist);
        r1.getFacilityOutline();
        r1.getActivityOutline();

//        assertEquals("PERSON", type);
//        assertEquals("HELLO", message);
//        assertEquals("key", key);
//        assertEquals("001", userId);
//        assertEquals("pass", password);
//        assertEquals("{\"userId\":\"personId\",\"age\":0,\"punctuality\":0.0,\"participation\":0.0}", new Gson().toJson(profile));
//        assertEquals("{\"userId\":\"userOutlineId\"}", new Gson().toJson(userOutline));
//        assertEquals("[{\"sportId\":\"001\"}]",new Gson().toJson(interests));
//        assertEquals("asd", authorization);
//        assertEquals("[{\"userId\":\"009\"}]", new Gson().toJson(comments));
//        assertEquals("{\"activityId\":\"008\",\"longitude\":0.0,\"latitude\":0.0,\"capacity\":0,\"size\":0}", new Gson().toJson(activity));
//        assertEquals("image", image);
//        assertEquals("uuid", iconUUID);
//        assertEquals("STRANGE", userType);
//        assertEquals("004", activityId);
//        assertEquals("[{\"facilityId\":\"005\",\"longitude\":0.0,\"latitude\":0.0}]", new Gson().toJson(facilities));
        assertEquals("PERSON HELLO key 001 pass {\"userId\":\"personId\",\"age\":0,\"punctuality\":0.0,\"participation\":0.0} {\"userId\":\"userOutlineId\"} [{\"sportId\":\"001\"}] asd [{\"userId\":\"009\"}] {\"activityId\":\"008\",\"longitude\":0.0,\"latitude\":0.0,\"capacity\":0,\"size\":0} image uuid STRANGE 004 [{\"facilityId\":\"005\",\"longitude\":0.0,\"latitude\":0.0}]", type+" "+message+" "+key+" "+userId+" "+password+" "+new Gson().toJson(profile) +" "+ new Gson().toJson(userOutline)+" "+ new Gson().toJson(interests)+" " + authorization+" " + new Gson().toJson(comments)+" " + new Gson().toJson(activity)+" "+ image+" " + iconUUID+" " + userType+" " + activityId+" " + new Gson().toJson(facilities));


    }
}
