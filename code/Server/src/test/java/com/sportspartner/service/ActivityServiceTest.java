package com.sportspartner.service;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sportspartner.dao.impl.ActivityMemberDaoImpl;
import com.sportspartner.exceptions.Exceptions;
import com.sportspartner.main.Bootstrap;
import com.sportspartner.model.Activity;
import com.sportspartner.model.ActivityMember;
import com.sportspartner.util.JsonResponse;
import com.sportspartner.util.JsonTransformer;
import org.junit.*;
import spark.Spark;
import spark.utils.IOUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.sportspartner.main.Bootstrap.PORT;
import static org.junit.Assert.assertEquals;
import static spark.Spark.ipAddress;
import static spark.Spark.port;
import static spark.Spark.staticFileLocation;

import com.sportspartner.service.ActivityService;
import com.sportspartner.controllers.ActivityController;

public class ActivityServiceTest {
    private ActivityMemberDaoImpl activityMemberDaoImpl = new ActivityMemberDaoImpl();
    private ActivityService activityService = new ActivityService();
    private JsonResponse resp = new JsonResponse();

    @BeforeClass
    public static void setUpBeforeClass() throws Exception{
    }

    @AfterClass
    public static void tearDownAfterClass()throws Exception{
        Spark.stop();
        Thread.sleep(2000);
    }

    @Before
    public void setUp(){}

    @After
    public void teardown(){
        try {
            String activityId = "a007";
            String userId = "u24";
            ActivityMember activityMember = new ActivityMember(activityId, userId);
            if(activityMemberDaoImpl.hasActivityMember(activityMember)) {
                String body = new Gson().toJson(activityMember);
                activityService.removeActivityMember(activityId, body);
            }
            String activityId1 = "a007";
            String userId1 = "u2";
            ActivityMember activityMember1 = new ActivityMember(activityId1, userId1);
            if(!activityMemberDaoImpl.hasActivityMember(activityMember)) {
                String body = new Gson().toJson(activityMember1);
                activityService.addActivityMember(activityId, body);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    /**
     * Test when getActivityDetail when the requestor is the creator of the activity.
     */
    @Test
    public void testGetActivityDetailAsCreator(){
        String response = null;
        try{
            String activityId = "a001";
            String requestorId = "u3";
            String requestorKey = "whatever";
            resp = activityService.getActivityDetail(activityId, requestorId, requestorKey);
            response = new Gson().toJson(resp);
        }catch(Exception e){
            e.printStackTrace();
        }

        assertEquals("{\"response\":\"true\",\"activity\":{\"activityId\":\"a001\",\"status\":\"FINISHED\",\"sportIconUUID\":\"35c2c8c2-c73a-11e7-abc4-cec278b6b50a\",\"sportName\":\"Tennis\",\"startTime\":\"Oct 25, 2017 12:00:00 PM\",\"endTime\":\"Oct 25, 2017 2:00:00 PM\",\"facilityId\":\"001\",\"facilityName\":\"JHU gym\",\"longitude\":76.312,\"latitude\":38.567,\"address\":\"JHU Gym\",\"capacity\":6,\"size\":5,\"creatorId\":\"u1\",\"members\":[{\"userId\":\"xuanzhang@jhu.edu\",\"userName\":\"Xuan Zhang\",\"iconUUID\":\"20e7d49f-5bb6-431e-a511-bc5e0edb349f\"},{\"userId\":\"zxiao10@jhu.edu\",\"userName\":\"Zihao Xiao\",\"iconUUID\":\"007\"},{\"userId\":\"leavebody@hotmail.com\",\"userName\":\"Xiaochen Li\",\"iconUUID\":\"92cf9134-e40b-40f5-baf3-f7d9990a61bf\"},{\"userId\":\"yujiaxiao0223@gmail.com\",\"userName\":\"Yujia Xiao\",\"iconUUID\":\"6c1e2972-d140-4055-8560-af5bad24448d\"}],\"detail\":\"Join us!\",\"discussion\":[{\"activityId\":\"a001\",\"commentId\":\"001\",\"authorId\":\"u1\",\"time\":\"Oct 26, 2017 5:24:02 PM\",\"content\":\"Good activity!\"}]},\"userType\":\"STRANGER\"}", response);
    }



    /**
     * Test getActivityDetail when the requestor is a member of the activity(not creator)
     */
    @Test
    public void testGetActivityDetailAsMember(){
        String response = null;
        try{
            String activityId = "a001";
            String requestorId = "xuanzhang@jhu.edu";
            String requestorKey = "xuanzhangUUID";
            resp = activityService.getActivityDetail(activityId, requestorId, requestorKey);
        }catch(Exception e){
            e.printStackTrace();
        }
        response = new Gson().toJson(resp);
        assertEquals("{\"response\":\"true\",\"activity\":{\"activityId\":\"a001\",\"status\":\"FINISHED\",\"sportIconUUID\":\"35c2c8c2-c73a-11e7-abc4-cec278b6b50a\",\"sportName\":\"Tennis\",\"startTime\":\"Oct 25, 2017 12:00:00 PM\",\"endTime\":\"Oct 25, 2017 2:00:00 PM\",\"facilityId\":\"001\",\"facilityName\":\"JHU gym\",\"longitude\":76.312,\"latitude\":38.567,\"address\":\"JHU Gym\",\"capacity\":6,\"size\":5,\"creatorId\":\"u1\",\"members\":[{\"userId\":\"xuanzhang@jhu.edu\",\"userName\":\"Xuan Zhang\",\"iconUUID\":\"20e7d49f-5bb6-431e-a511-bc5e0edb349f\"},{\"userId\":\"zxiao10@jhu.edu\",\"userName\":\"Zihao Xiao\",\"iconUUID\":\"007\"},{\"userId\":\"leavebody@hotmail.com\",\"userName\":\"Xiaochen Li\",\"iconUUID\":\"92cf9134-e40b-40f5-baf3-f7d9990a61bf\"},{\"userId\":\"yujiaxiao0223@gmail.com\",\"userName\":\"Yujia Xiao\",\"iconUUID\":\"6c1e2972-d140-4055-8560-af5bad24448d\"}],\"detail\":\"Join us!\",\"discussion\":[{\"activityId\":\"a001\",\"commentId\":\"001\",\"authorId\":\"u1\",\"time\":\"Oct 26, 2017 5:24:02 PM\",\"content\":\"Good activity!\"}]},\"userType\":\"MEMBER\"}", response);
    }

    /**
     * Test when getActivityDetail when the requestorKey is not correct,
     * although the requestor is a creator or a member of the activity,
     * he will still be recognized as a stranger
     */
    @Test
    public void testGetActivityDetailWrongKey(){
        String response = null;
        try{
            String activityId = "a001";
            String requestorId = "u1";
            String requestorKey = "wrongkey";
            resp = activityService.getActivityDetail(activityId, requestorId, requestorKey);
        }catch(Exception e){
            e.printStackTrace();
        }
        response = new Gson().toJson(resp);
        assertEquals("{\"response\":\"true\",\"activity\":{\"activityId\":\"a001\",\"status\":\"FINISHED\",\"sportIconUUID\":\"35c2c8c2-c73a-11e7-abc4-cec278b6b50a\",\"sportName\":\"Tennis\",\"startTime\":\"Oct 25, 2017 12:00:00 PM\",\"endTime\":\"Oct 25, 2017 2:00:00 PM\",\"facilityId\":\"001\",\"facilityName\":\"JHU gym\",\"longitude\":76.312,\"latitude\":38.567,\"address\":\"JHU Gym\",\"capacity\":6,\"size\":5,\"creatorId\":\"u1\",\"members\":[{\"userId\":\"xuanzhang@jhu.edu\",\"userName\":\"Xuan Zhang\",\"iconUUID\":\"20e7d49f-5bb6-431e-a511-bc5e0edb349f\"},{\"userId\":\"zxiao10@jhu.edu\",\"userName\":\"Zihao Xiao\",\"iconUUID\":\"007\"},{\"userId\":\"leavebody@hotmail.com\",\"userName\":\"Xiaochen Li\",\"iconUUID\":\"92cf9134-e40b-40f5-baf3-f7d9990a61bf\"},{\"userId\":\"yujiaxiao0223@gmail.com\",\"userName\":\"Yujia Xiao\",\"iconUUID\":\"6c1e2972-d140-4055-8560-af5bad24448d\"}],\"detail\":\"Join us!\",\"discussion\":[{\"activityId\":\"a001\",\"commentId\":\"001\",\"authorId\":\"u1\",\"time\":\"Oct 26, 2017 5:24:02 PM\",\"content\":\"Good activity!\"}]},\"userType\":\"STRANGER\"}", response);
    }

    /**
     * Test getActivityDetail when the requestor is a stranger.
     */
    @Test
    public void testGetActivityDetailAsStranger(){
        String response = null;
        try{
            String activityId = "a001";
            String requestorId = "u3";
            String requestorKey = "91491343-acbc-4feb-aea8-655cd89196c8";
            resp = activityService.getActivityDetail(activityId, requestorId, requestorKey);
        }catch(Exception e){
            e.printStackTrace();
        }
        response = new Gson().toJson(resp);
        assertEquals("{\"response\":\"true\",\"activity\":{\"activityId\":\"a001\",\"status\":\"FINISHED\",\"sportIconUUID\":\"35c2c8c2-c73a-11e7-abc4-cec278b6b50a\",\"sportName\":\"Tennis\",\"startTime\":\"Oct 25, 2017 12:00:00 PM\",\"endTime\":\"Oct 25, 2017 2:00:00 PM\",\"facilityId\":\"001\",\"facilityName\":\"JHU gym\",\"longitude\":76.312,\"latitude\":38.567,\"address\":\"JHU Gym\",\"capacity\":6,\"size\":5,\"creatorId\":\"u1\",\"members\":[{\"userId\":\"xuanzhang@jhu.edu\",\"userName\":\"Xuan Zhang\",\"iconUUID\":\"20e7d49f-5bb6-431e-a511-bc5e0edb349f\"},{\"userId\":\"zxiao10@jhu.edu\",\"userName\":\"Zihao Xiao\",\"iconUUID\":\"007\"},{\"userId\":\"leavebody@hotmail.com\",\"userName\":\"Xiaochen Li\",\"iconUUID\":\"92cf9134-e40b-40f5-baf3-f7d9990a61bf\"},{\"userId\":\"yujiaxiao0223@gmail.com\",\"userName\":\"Yujia Xiao\",\"iconUUID\":\"6c1e2972-d140-4055-8560-af5bad24448d\"}],\"detail\":\"Join us!\",\"discussion\":[{\"activityId\":\"a001\",\"commentId\":\"001\",\"authorId\":\"u1\",\"time\":\"Oct 26, 2017 5:24:02 PM\",\"content\":\"Good activity!\"}]},\"userType\":\"STRANGER\"}", response);

    }

    /**
     * Test getActivityDetail fails when there's no activity of given Id
     */
    @Test
    public void testGetActivityDetailNoId(){
        String response = null;
        try{
            String activityId = "fake";
            String requestorId = "u1";
            String requestorKey = "nonono";
            resp = activityService.getActivityDetail(activityId, requestorId, requestorKey);
        }catch(Exception e){
            e.printStackTrace();
        }
        response = new Gson().toJson(resp);
        assertEquals("{\"response\":\"false\",\"message\":\"No such activity\"}", response);
    }

    /**
     * Test getActivityOutline succeed.
     */
    @Test
    public void testGetActivityOutline(){
        String response = null;
        try{
            String activityId = "a001";
            resp = activityService.getActivityOutline(activityId);
            response = new Gson().toJson(resp);
        }catch(Exception e){
            e.printStackTrace();
        }
        assertEquals("{\"response\":\"true\",\"activityOutline\":{\"activityId\":\"a001\",\"creatorId\":\"u1\",\"status\":\"FINISHED\",\"sportIconUUID\":\"35c2c8c2-c73a-11e7-abc4-cec278b6b50a\",\"sportName\":\"Tennis\",\"startTime\":\"Oct 25, 2017 12:00:00 PM\",\"endTime\":\"Oct 25, 2017 2:00:00 PM\",\"facilityId\":\"001\",\"longitude\":76.312,\"latitude\":38.567,\"address\":\"JHU Gym\",\"capacity\":6,\"size\":5}}", response);
    }

    /**
     * Test getActivityOutline when there's no such activityId.
     */
    @Test
    public void testGetActivityOutlineNoId(){
        String response = null;
        try{
            String activityId = "fake";
            resp = activityService.getActivityOutline(activityId);
            response = new Gson().toJson(resp);
        }catch(Exception e){
            e.printStackTrace();
        }
        assertEquals("{\"response\":\"false\",\"message\":\"No such activity\"}",response);
    }

    /**
     * Test getUpcomingActivity succeed.
     */
    @Test
    public void testGetUpcomingActivity(){
        String response = null;
        try{
            String userId = "u1";
            int offset = 0;
            int limit = 3;
            resp = activityService.getUpcomingActivity(userId, offset, limit);
            response = new Gson().toJson(resp);
        }catch(Exception e){
            e.printStackTrace();
        }
        assertEquals("{\"response\":\"true\",\"activityOutlines\":[{\"activityId\":\"a007\",\"creatorId\":\"u2\",\"status\":\"OPEN\",\"sportIconUUID\":\"3f0c94e4-c73a-11e7-abc4-cec278b6b50a\",\"sportName\":\"Baseball\",\"startTime\":\"Nov 30, 2017 9:00:00 AM\",\"endTime\":\"Nov 30, 2017 8:00:00 AM\",\"facilityId\":\"001\",\"longitude\":0.0,\"latitude\":39.3372406837219,\"address\":\"JHU\",\"capacity\":4,\"size\":3}]}",response);
    }

    /**
     * Test getUpcomingActivity when there's no more upcoming activities for a user.
     */
    @Test
    public void testGetUpcomingActivityNoMore(){
        String response = null;
        try{
            String userId = "xuanzhang@jhu.edu";
            int offset = 0;
            int limit = 3;
            resp = activityService.getUpcomingActivity(userId, offset, limit);
            response = new Gson().toJson(resp);
        }catch(Exception e){
            e.printStackTrace();
        }
        assertEquals("{\"response\":\"true\",\"message\":\"No more upcoming activities\",\"activityOutlines\":[]}", response);
    }


    /**
     * Test getUpcomingActivity when the userId does not exist.
     */
    @Test
    public void testGetUpcomingActivityNoId(){
        String response = null;
        try{
            String userId = "nouser";
            int offset = 0;
            int limit = 3;
            resp = activityService.getUpcomingActivity(userId, offset, limit);
            response = new Gson().toJson(resp);
        }catch(Exception e){
            e.printStackTrace();
         }
         assertEquals("{\"response\":\"false\",\"message\":\"No such user\"}", response);
    }

    /**
     * Test getPastActivity succeed.
     */
    @Test
    public void testGetPastActivity(){
        String response = null;
        try{
            String userId = "u1";
            int offset = 0;
            int limit = 3;
            resp = activityService.getPastActivity(userId, offset, limit);
            response = new Gson().toJson(resp);
        }catch(Exception e){
            e.printStackTrace();
        }
        assertEquals("{\"response\":\"true\",\"activityOutlines\":[{\"activityId\":\"28b45f7e-76e1-4887-838e-27b9b8a7be14\",\"creatorId\":\"u1\",\"status\":\"OPEN\",\"sportIconUUID\":\"35c2c8c2-c73a-11e7-abc4-cec278b6b50a\",\"sportName\":\"Tennis\",\"startTime\":\"Nov 17, 2017 9:31:00 AM\",\"endTime\":\"Nov 17, 2017 10:31:00 AM\",\"facilityId\":\"NULL\",\"longitude\":151.20964117348194,\"latitude\":-33.85115060744295,\"address\":\"Sydney NSW, Australia\",\"capacity\":5,\"size\":1},{\"activityId\":\"b84b0971-0adf-481e-91ea-d48af89f8e92\",\"creatorId\":\"u1\",\"status\":\"OPEN\",\"sportIconUUID\":\"5f4f8ad9-1acb-42ba-8174-744ce9a78cdd\",\"sportName\":\"Badminton\",\"startTime\":\"Nov 15, 2017 3:37:00 PM\",\"endTime\":\"Nov 15, 2017 4:37:00 PM\",\"facilityId\":\"NULL\",\"longitude\":-76.61933228373526,\"latitude\":39.330050886991074,\"address\":\"Johns-Hopkins - Homewood, Baltimore, MD, USA\",\"capacity\":1,\"size\":1},{\"activityId\":\"8cfb92b1-a51d-44d5-8d98-6f80e1db8aaa\",\"creatorId\":\"u1\",\"status\":\"OPEN\",\"sportIconUUID\":\"3f0c94e4-c73a-11e7-abc4-cec278b6b50a\",\"sportName\":\"Baseball\",\"startTime\":\"Nov 15, 2017 11:03:00 AM\",\"endTime\":\"Nov 16, 2017 1:03:00 PM\",\"facilityId\":\"NULL\",\"longitude\":-76.62052720785141,\"latitude\":39.33096401271119,\"address\":\"Baltimore, MD, USA\",\"capacity\":4,\"size\":1}]}",response);
    }

    /**
     * Test getPastActivity when there's no more past activities for a user.
     */
    @Test
    public void testGetPastActivityNoMore(){
        String response = null;
        try{
            String userId = "u3";
            int offset = 0;
            int limit = 3;
            resp = activityService.getPastActivity(userId, offset, limit);
            response = new Gson().toJson(resp);
        }catch(Exception e){
            e.printStackTrace();
        }
        assertEquals("{\"response\":\"true\",\"message\":\"No more past activities\",\"activityOutlines\":[]}", response);
    }


    /**
     * Test getPastActivity when the userId does not exist.
     */
    @Test
    public void testGetPastActivityNoId(){
        String response = null;
        try{
            String userId = "nouser";
            int offset = 0;
            int limit = 3;
            resp = activityService.getPastActivity(userId, offset, limit);
            response = new Gson().toJson(resp);
        }catch(Exception e){
            e.printStackTrace();
        }
        assertEquals("{\"response\":\"false\",\"message\":\"No such user\"}", response);
    }

    /**
     * Test getActivityMembers succeed.
     */
    @Test
    public void testGetActivityMembers(){
        String response = null;
        try{
            String activityId = "a001";
            resp = activityService.getActivityMembers(activityId);
            response = new Gson().toJson(resp);
        }catch(Exception e){
            e.printStackTrace();
        }
        assertEquals("{\"response\":\"true\",\"members\":[{\"userId\":\"u1\",\"userName\":\"Dog\",\"iconUUID\":\"f26be2f0-45fc-4f8f-b93a-40fe114699b4\"},{\"userId\":\"xuanzhang@jhu.edu\",\"userName\":\"Xuan Zhang\",\"iconUUID\":\"20e7d49f-5bb6-431e-a511-bc5e0edb349f\"},{\"userId\":\"zxiao10@jhu.edu\",\"userName\":\"Zihao Xiao\",\"iconUUID\":\"007\"},{\"userId\":\"leavebody@hotmail.com\",\"userName\":\"Xiaochen Li\",\"iconUUID\":\"92cf9134-e40b-40f5-baf3-f7d9990a61bf\"},{\"userId\":\"yujiaxiao0223@gmail.com\",\"userName\":\"Yujia Xiao\",\"iconUUID\":\"6c1e2972-d140-4055-8560-af5bad24448d\"}]}", response);
    }

    /**
     * Test addActivityMember succeed.
     */
    @Test
    public void testAddActivityMember(){
        String response = null;
        try{
            String activityId = "a007";
            String userId = "u24";
            String body = new Gson().toJson(new ActivityMember(activityId, userId));
            resp = activityService.addActivityMember(activityId, body);
            response = new Gson().toJson(resp);
        }catch(Exception e){
            e.printStackTrace();
        }
        assertEquals("{\"response\":\"true\"}", response);
    }

    /**
     * Test removeActivityMember succeed.
     */
    @Test
    public void testRemoveActivityMember(){
        String response = null;
        try{
            String activityId = "a007";
            String userId = "u2";
            String body = new Gson().toJson(new ActivityMember(activityId, userId));
            resp = activityService.removeActivityMember(activityId, body);
            response = new Gson().toJson(resp);
        }catch(Exception e){
            e.printStackTrace();
        }
        assertEquals("{\"response\":\"true\"}", response);
    }



}
