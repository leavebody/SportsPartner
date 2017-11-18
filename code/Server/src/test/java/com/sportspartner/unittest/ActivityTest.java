package com.sportspartner.unittest;
import com.google.gson.*;
import com.sportspartner.controllers.ActivityController;
import com.sportspartner.dao.impl.ActivityDaoImpl;
import com.sportspartner.dao.impl.ActivityMemberDaoImpl;
import com.sportspartner.main.Bootstrap;
import com.sportspartner.model.Activity;
import com.sportspartner.model.ActivityMember;
import com.sportspartner.service.ActivityService;
import com.google.gson.JsonObject;
import org.json.JSONObject;
import org.junit.*;
import spark.Spark;
import spark.utils.IOUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import spark.utils.IOUtils;

import static com.sportspartner.main.Bootstrap.PORT;
import static org.junit.Assert.assertEquals;
import static spark.Spark.*;
public class ActivityTest {
    HttpURLConnection connection = null;
    private ActivityMemberDaoImpl activityMemberDaoImpl = new ActivityMemberDaoImpl();

    private ActivityService activityService = new ActivityService();


    @BeforeClass
    public static void setUpBeforeClass() throws Exception{
        ipAddress(Bootstrap.IP_ADDRESS);
        port(PORT);
        staticFileLocation("/public");
        new ActivityController(new ActivityService());

        ActivityDaoImpl activityDao = new ActivityDaoImpl();
        ActivityMemberDaoImpl activityMemberDao = new ActivityMemberDaoImpl();
        try {
            Activity toUpdate = new Activity();
            toUpdate.setActivityId("toUpdate001");
            toUpdate.setCreatorId("u24");
            toUpdate.setSportId("001");
            toUpdate.setEndTime(new Date());
            toUpdate.setStartTime(new Date());
            activityDao.newActivity(toUpdate);
            activityMemberDao.newActivityMember(new ActivityMember("toUpdate001", "u24"));

            Activity toDelete = new Activity();
            toDelete.setActivityId("toDelete001");
            toDelete.setCreatorId("u24");
            toDelete.setSportId("001");
            toDelete.setEndTime(new Date());
            toDelete.setStartTime(new Date());
            activityDao.newActivity(toDelete);
            activityMemberDao.newActivityMember(new ActivityMember("toDelete001", "u24"));

        } catch(Exception e){
            e.printStackTrace();
        }

        Thread.sleep(4000);
    }

    @AfterClass
    public static void tearDownAfterClass()throws Exception{
        ActivityDaoImpl activityDao = new ActivityDaoImpl();
        ActivityMemberDaoImpl activityMemberDao = new ActivityMemberDaoImpl();
        try {
            activityMemberDao.deleteAllActivityMembers("toUpdate001");
            activityDao.deleteActivity("toUpdate001");
        } catch(Exception e){
            e.printStackTrace();
        }
        Spark.stop();
        Thread.sleep(4000);
    }

    @Before
    public void setUp(){
    }

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
     * test when GetActivityDetail succeeds
     */
    @Test
    public void testGetActivityDetailSuccess(){
        String responseBody = new String();
        String API_CONTEXT = "/api.sportspartner.com/v1";

        try{
            String activityId = "a001";
            URL url = new URL("http", Bootstrap.IP_ADDRESS, PORT, API_CONTEXT + "/activity/" + activityId+ "?content=full");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
        }catch(Exception e){
            e.printStackTrace();
        }

        try{
            responseBody = IOUtils.toString(connection.getInputStream());
        }catch(IOException ioe){
            ioe.printStackTrace();
        }

        //JsonObject responseJson = new Gson().fromJson(responseBody, JsonObject.class);
        //String response =responseJson.getAsString();
        //String respond = responseJson.get("response").getAsString();
        //assertEquals ("true",respond);
        JsonObject responseJson = new Gson().fromJson(responseBody, JsonObject.class);
        String response =responseJson.toString();
        assertEquals("{\"response\":\"true\",\"activity\":{\"activityId\":\"a001\",\"status\":\"FINISHED\",\"sportIconUUID\":\"35c2c8c2-c73a-11e7-abc4-cec278b6b50a\",\"sportName\":\"Tennis\",\"startTime\":\"Oct 25, 2017 12:00:00 PM\",\"endTime\":\"Oct 25, 2017 2:00:00 PM\",\"facilityId\":\"001\",\"facilityName\":\"JHU gym\",\"longitude\":76.312,\"latitude\":38.567,\"address\":\"JHU Gym\",\"capacity\":6,\"size\":5,\"creatorId\":\"u1\",\"members\":[{\"userId\":\"xuanzhang@jhu.edu\",\"userName\":\"Xuan Zhang\",\"iconUUID\":\"20e7d49f-5bb6-431e-a511-bc5e0edb349f\"},{\"userId\":\"zxiao10@jhu.edu\",\"userName\":\"Zihao Xiao\",\"iconUUID\":\"007\"},{\"userId\":\"leavebody@hotmail.com\",\"userName\":\"Xiaochen Li\",\"iconUUID\":\"92cf9134-e40b-40f5-baf3-f7d9990a61bf\"},{\"userId\":\"yujiaxiao0223@gmail.com\",\"userName\":\"Yujia Xiao\",\"iconUUID\":\"6c1e2972-d140-4055-8560-af5bad24448d\"}],\"detail\":\"Join us!\",\"discussion\":[{\"activityId\":\"a001\",\"commentId\":\"001\",\"authorId\":\"u1\",\"time\":\"Oct 26, 2017 5:24:02 PM\",\"content\":\"Good activity!\"}]},\"userType\":\"STRANGER\"}", response);
    }

    /**
     * test when GetActivityDetail fails
     */
    @Test
    public void testGetActivityDetailFailture(){
        String responseBody = new String();
        String API_CONTEXT = "/api.sportspartner.com/v1";

        try{
            String activityId = "a1000";
            URL url = new URL("http", Bootstrap.IP_ADDRESS, PORT, API_CONTEXT + "/activity/" + activityId+ "?content=full");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
        }catch(Exception e){
            e.printStackTrace();
        }

        try{
            responseBody = IOUtils.toString(connection.getInputStream());
        }catch(IOException ioe){
            ioe.printStackTrace();
        }

        //JsonObject responseJson = new Gson().fromJson(responseBody, JsonObject.class);
        //String response =responseJson.getAsString();
        assertEquals("{\"response\":\"false\",\"message\":\"No such activity\"}",responseBody);
    }

    /**
     * test when GetActivityOutline succeeds
     */
    @Test
    public void testGetActivityOutlineSuccess(){
        String responseBody = new String();
        String API_CONTEXT = "/api.sportspartner.com/v1";

        try{
            String activityId = "a001";
            URL url = new URL("http", Bootstrap.IP_ADDRESS, PORT, API_CONTEXT + "/activity/" + activityId+ "?content=outline");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
        }catch(Exception e){
            e.printStackTrace();
        }

        try{
            responseBody = IOUtils.toString(connection.getInputStream());

        }catch(IOException ioe){
            ioe.printStackTrace();
        }

        //JsonObject responseJson = new Gson().fromJson(responseBody, JsonObject.class);
        //String respond = responseJson.get("response").getAsString();
        //assertEquals ("\"true\"",respond);
        //String response =responseJson.toString();
        assertEquals("{\"response\":\"true\",\"activityOutline\":{\"activityId\":\"a001\",\"creatorId\":\"u1\",\"status\":\"FINISHED\",\"sportIconUUID\":\"35c2c8c2-c73a-11e7-abc4-cec278b6b50a\",\"sportName\":\"Tennis\",\"startTime\":\"Oct 25, 2017 12:00:00 PM\",\"endTime\":\"Oct 25, 2017 2:00:00 PM\",\"facilityId\":\"001\",\"longitude\":76.312,\"latitude\":38.567,\"address\":\"JHU Gym\",\"capacity\":6,\"size\":5}}", responseBody);
    }
    /**
     * test when GetActivityOutline fails
     */
    @Test
    public void testGetActivityOutlineFailure(){
        String responseBody = new String();
        String API_CONTEXT = "/api.sportspartner.com/v1";

        try{
            String activityId = "a100";
            URL url = new URL("http", Bootstrap.IP_ADDRESS, PORT, API_CONTEXT + "/activity/" + activityId+ "?content=outline");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
        }catch(Exception e){
            e.printStackTrace();
        }

        try{
            responseBody = IOUtils.toString(connection.getInputStream());
        }catch(IOException ioe){
            ioe.printStackTrace();
        }

        //JsonObject responseJson = new Gson().fromJson(responseBody, JsonObject.class);
        //String response =responseJson.toString();
        assertEquals("{\"response\":\"false\",\"message\":\"No such activity\"}", responseBody);
    }


    /**
     * TEST When testGetUpcommingActivity succeeds
     */
    @Test
    public void testGetUpcommingActivitySuccess(){
        String responseBody = new String();
        String API_CONTEXT = "/api.sportspartner.com/v1";

        try{
            URL url = new URL("http", Bootstrap.IP_ADDRESS, PORT, API_CONTEXT + "/activity_upcoming" + "?id=u1&offset=0&limit=5");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
        }catch(Exception e){
            e.printStackTrace();
        }

        try{
            responseBody = IOUtils.toString(connection.getInputStream());
        }catch(IOException ioe){
            ioe.printStackTrace();
        }

        //JsonObject responseJson = new Gson().fromJson(responseBody, JsonObject.class);
        //String response =responseJson.getAsString();
        //String respond = responseJson.get("response").toString();
        //assertEquals ("\"true\"",respond);
        assertEquals("{\"response\":\"true\",\"activityOutlines\":[{\"activityId\":\"28b45f7e-76e1-4887-838e-27b9b8a7be14\",\"creatorId\":\"u1\",\"status\":\"OPEN\",\"sportIconUUID\":\"35c2c8c2-c73a-11e7-abc4-cec278b6b50a\",\"sportName\":\"Tennis\",\"startTime\":\"Nov 17, 2017 9:31:00 AM\",\"endTime\":\"Nov 17, 2017 10:31:00 AM\",\"facilityId\":\"NULL\",\"longitude\":151.20964117348194,\"latitude\":-33.85115060744295,\"address\":\"Sydney NSW, Australia\",\"capacity\":5,\"size\":1},{\"activityId\":\"a007\",\"creatorId\":\"u2\",\"status\":\"OPEN\",\"sportIconUUID\":\"3f0c94e4-c73a-11e7-abc4-cec278b6b50a\",\"sportName\":\"Baseball\",\"startTime\":\"Nov 30, 2017 9:00:00 AM\",\"endTime\":\"Nov 30, 2017 8:00:00 AM\",\"facilityId\":\"001\",\"longitude\":0.0,\"latitude\":39.3372406837219,\"address\":\"JHU\",\"capacity\":4,\"size\":3}]}", responseBody);
    }

    /**
     * test when GetUpcommingActivity fails because there's no such activity
     */
    @Test
    public void testGetUpcommingActivityFailureNoactivity(){
        String responseBody = new String();
        String API_CONTEXT = "/api.sportspartner.com/v1";

        try{
            URL url = new URL("http", Bootstrap.IP_ADDRESS, PORT, API_CONTEXT + "/activity_upcoming" + "?id=u24&offset=0&limit=5");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
        }catch(Exception e){
            e.printStackTrace();
        }

        try{
            responseBody = IOUtils.toString(connection.getInputStream());
        }catch(IOException ioe){
            ioe.printStackTrace();
        }

        //JsonObject responseJson = new Gson().fromJson(responseBody, JsonObject.class);
        //String response =responseJson.toString();
        assertEquals("{\"response\":\"true\",\"message\":\"No more upcoming activities\",\"activityOutlines\":[]}", responseBody);
    }
    /**
     * test when GetUpcommingActivity fails because there's no such user
     */
    @Test
    public void testGetUpcommingActivityFailureNoUser(){
        String responseBody = new String();
        String API_CONTEXT = "/api.sportspartner.com/v1";

        try{
            URL url = new URL("http", Bootstrap.IP_ADDRESS, PORT, API_CONTEXT + "/activity_upcoming" + "?id=u10&offset=0&limit=5");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
        }catch(Exception e){
            e.printStackTrace();
        }

        try{
            responseBody = IOUtils.toString(connection.getInputStream());
        }catch(IOException ioe){
            ioe.printStackTrace();
        }

        //JsonObject responseJson = new Gson().fromJson(responseBody, JsonObject.class);
        //String response =responseJson.toString();
        assertEquals("{\"response\":\"false\",\"message\":\"No such user\"}", responseBody);
    }

    /**
     *  Test when testGetPastActivity succeeds
     */
    @Test
    public void testGetPastActivitySuccess(){
        String responseBody = new String();
        String API_CONTEXT = "/api.sportspartner.com/v1";

        try{
            URL url = new URL("http", Bootstrap.IP_ADDRESS, PORT, API_CONTEXT + "/activity_past" + "?id=u1&offset=0&limit=5");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
        }catch(Exception e){
            e.printStackTrace();
        }

        try{
            responseBody = IOUtils.toString(connection.getInputStream());
        }catch(IOException ioe){
            ioe.printStackTrace();
        }

        JsonObject responseJson = new Gson().fromJson(responseBody, JsonObject.class);
        assertEquals("{\"response\":\"true\",\"activityOutlines\":[{\"activityId\":\"b84b0971-0adf-481e-91ea-d48af89f8e92\",\"creatorId\":\"u1\",\"status\":\"OPEN\",\"sportIconUUID\":\"5f4f8ad9-1acb-42ba-8174-744ce9a78cdd\",\"sportName\":\"Badminton\",\"startTime\":\"Nov 15, 2017 3:37:00 PM\",\"endTime\":\"Nov 15, 2017 4:37:00 PM\",\"facilityId\":\"NULL\",\"longitude\":-76.61933228373526,\"latitude\":39.330050886991074,\"address\":\"Johns-Hopkins - Homewood, Baltimore, MD, USA\",\"capacity\":1,\"size\":1},{\"activityId\":\"8cfb92b1-a51d-44d5-8d98-6f80e1db8aaa\",\"creatorId\":\"u1\",\"status\":\"OPEN\",\"sportIconUUID\":\"3f0c94e4-c73a-11e7-abc4-cec278b6b50a\",\"sportName\":\"Baseball\",\"startTime\":\"Nov 15, 2017 11:03:00 AM\",\"endTime\":\"Nov 16, 2017 1:03:00 PM\",\"facilityId\":\"NULL\",\"longitude\":-76.62052720785141,\"latitude\":39.33096401271119,\"address\":\"Baltimore, MD, USA\",\"capacity\":4,\"size\":1},{\"activityId\":\"890254f7-ed0f-42a7-9a13-e1e88c958125\",\"creatorId\":\"u1\",\"status\":\"OPEN\",\"sportIconUUID\":\"3f0c94e4-c73a-11e7-abc4-cec278b6b50a\",\"sportName\":\"Baseball\",\"startTime\":\"Nov 14, 2017 10:00:00 AM\",\"endTime\":\"Nov 14, 2017 11:00:00 AM\",\"facilityId\":\"NULL\",\"longitude\":-76.61960788071156,\"latitude\":39.329714264504204,\"address\":\"JHU gym\",\"capacity\":4,\"size\":1},{\"activityId\":\"7543903a-c084-4fa7-b8c0-04065fd30c47\",\"creatorId\":\"u1\",\"status\":\"OPEN\",\"sportIconUUID\":\"3f0c94e4-c73a-11e7-abc4-cec278b6b50a\",\"sportName\":\"Baseball\",\"startTime\":\"Nov 14, 2017 6:30:00 AM\",\"endTime\":\"Nov 14, 2017 7:40:00 AM\",\"facilityId\":\"NULL\",\"longitude\":0.0,\"latitude\":0.0,\"address\":\"JHU gym\",\"capacity\":4,\"size\":1},{\"activityId\":\"ce6d061b-ca68-4d93-8b95-793aef0824e5\",\"creatorId\":\"u1\",\"status\":\"OPEN\",\"sportIconUUID\":\"3f0c94e4-c73a-11e7-abc4-cec278b6b50a\",\"sportName\":\"Baseball\",\"startTime\":\"Nov 12, 2017 11:37:00 PM\",\"endTime\":\"Nov 14, 2017 4:37:00 AM\",\"facilityId\":\"NULL\",\"longitude\":-122.08058506250381,\"latitude\":37.42675521881782,\"address\":\"Mountain View, CA, USA\",\"capacity\":4,\"size\":1}]}",responseBody);
    }

    /**
     * test when GetPastActivity fails because there's no activity
     */
    @Test
    public void testGetPastActivityFailureNoactivity(){
        String responseBody = new String();
        String API_CONTEXT = "/api.sportspartner.com/v1";

        try{
            URL url = new URL("http", Bootstrap.IP_ADDRESS, Bootstrap.PORT, API_CONTEXT + "/activity_past" + "?id=estelle7w7@gmail.com&offset=0&limit=5");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
        }catch(Exception e){
            e.printStackTrace();
        }

        try{
            responseBody = IOUtils.toString(connection.getInputStream());
        }catch(IOException ioe){
            ioe.printStackTrace();
        }

        JsonObject responseJson = new Gson().fromJson(responseBody, JsonObject.class);
        String response =responseJson.toString();
        assertEquals("{\"response\":\"true\",\"message\":\"No more past activities\",\"activityOutlines\":[]}", response);
    }

    /**
     *  test when GetPastActivity fails because there's no user
     */
    @Test
    public void testGetPastActivityFailureNoUser(){
        String responseBody = new String();
        String API_CONTEXT = "/api.sportspartner.com/v1";

        try{
            URL url = new URL("http", Bootstrap.IP_ADDRESS, PORT, API_CONTEXT + "/activity_past" + "?id=u10&offset=0&limit=5");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
        }catch(Exception e){
            e.printStackTrace();
        }

        try{
            responseBody = IOUtils.toString(connection.getInputStream());
        }catch(IOException ioe){
            ioe.printStackTrace();
        }

        //JsonObject responseJson = new Gson().fromJson(responseBody, JsonObject.class);
        //String response =responseJson.toString();
        assertEquals("{\"response\":\"false\",\"message\":\"No such user\"}", responseBody);
    }

    /**
     *  test when get activity members success
     */
    @Test
    public void testGetActivityMembersSuccess(){
        String responseBody = new String();
        String API_CONTEXT = "/api.sportspartner.com/v1";

        try{
            URL url = new URL("http", Bootstrap.IP_ADDRESS, PORT, API_CONTEXT + "/activity_members/a001");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
        }catch(Exception e){
            e.printStackTrace();
        }

        try{
            responseBody = IOUtils.toString(connection.getInputStream());
        }catch(IOException ioe){
            ioe.printStackTrace();
        }

        //JsonObject responseJson = new Gson().fromJson(responseBody, JsonObject.class);
        //String response =responseJson.toString();
        assertEquals("{\"response\":\"true\",\"members\":[{\"userId\":\"u1\",\"userName\":\"Dog\",\"iconUUID\":\"f26be2f0-45fc-4f8f-b93a-40fe114699b4\"},{\"userId\":\"xuanzhang@jhu.edu\",\"userName\":\"Xuan Zhang\",\"iconUUID\":\"20e7d49f-5bb6-431e-a511-bc5e0edb349f\"},{\"userId\":\"zxiao10@jhu.edu\",\"userName\":\"Zihao Xiao\",\"iconUUID\":\"007\"},{\"userId\":\"leavebody@hotmail.com\",\"userName\":\"Xiaochen Li\",\"iconUUID\":\"92cf9134-e40b-40f5-baf3-f7d9990a61bf\"},{\"userId\":\"yujiaxiao0223@gmail.com\",\"userName\":\"Yujia Xiao\",\"iconUUID\":\"6c1e2972-d140-4055-8560-af5bad24448d\"}]}", responseBody);
    }

    /**
     *  test when get activity members failed
     */
    @Test
    public void testGetActivityMembersFailure(){
        String responseBody = new String();
        String API_CONTEXT = "/api.sportspartner.com/v1";

        try{
            URL url = new URL("http", Bootstrap.IP_ADDRESS, PORT, API_CONTEXT + "/activity_members/b00001");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
        }catch(Exception e){
            e.printStackTrace();
        }

        try{
            responseBody = IOUtils.toString(connection.getInputStream());
        }catch(IOException ioe){
            ioe.printStackTrace();
        }

        assertEquals("{\"response\":\"false\",\"message\":\"No such activity\"}", responseBody);

    }

    /**
     *  test when add activity members success
     */
    @Test
    public void testAddActivityMemberSuccess(){
        String responseBody = new String();
        String API_CONTEXT = "/api.sportspartner.com/v1";
        JSONObject parameters = new JSONObject();

        try{
            URL url = new URL("http", Bootstrap.IP_ADDRESS, PORT, API_CONTEXT + "/activity_members/a007");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            String activityId = "a007";
            String userId = "u24";
            String body = new Gson().toJson(new ActivityMember(activityId, userId));

            try(DataOutputStream wr = new DataOutputStream( connection.getOutputStream())){
                wr.writeBytes(body);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        try{
            responseBody = IOUtils.toString(connection.getInputStream());
        }catch(IOException ioe){
            ioe.printStackTrace();
        }

        assertEquals("{\"response\":\"true\"}", responseBody);
    }


    /**
     *  test when remove activity members success
     */
    @Test
    public void testRemoveActivityMemberSuccess(){
        String responseBody = new String();
        String API_CONTEXT = "/api.sportspartner.com/v1";
        JSONObject parameters = new JSONObject();

        try{
            URL url = new URL("http", Bootstrap.IP_ADDRESS, PORT, API_CONTEXT + "/activity_members/a007");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            String activityId = "a007";
            String userId = "u2";
            String body = new Gson().toJson(new ActivityMember(activityId, userId));

            try(DataOutputStream wr = new DataOutputStream( connection.getOutputStream())){
                wr.writeBytes(body);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        try{
            responseBody = IOUtils.toString(connection.getInputStream());
        }catch(IOException ioe){
            ioe.printStackTrace();
        }

        assertEquals("{\"response\":\"true\"}", responseBody);
    }

    /**
     *  test when create a new activity success
     */
    @Test
    public void testCreateActivitySuccess(){
        String responseBody = new String();
        String API_CONTEXT = "/api.sportspartner.com/v1";
        JSONObject parameters = new JSONObject();

        try{
            URL url = new URL("http", Bootstrap.IP_ADDRESS, PORT, API_CONTEXT + "/activity");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            String body = "{\"userId\":\"u1\",\"key\":\"12382e7c-d0e9-4035-9c11-730a7dc262ae\",\"activity\":{\"activityId\":\"NULL\",\"address\":\"Sydney NSW, Australia\",\"capacity\":10,\"creatorId\":\"u1\",\"description\":\"t\",\"endTime\":\"Nov 18, 2017 3:11:00 AM\",\"facilityId\":\"NULL\",\"latitude\":-33.850770532400865,\"longitude\":151.2113021314144,\"size\":1,\"sportId\":\"004\",\"startTime\":\"Nov 18, 2017 2:11:00 AM\",\"status\":\"OPEN\",\"zipcode\":\"2000\"}}";

            try(DataOutputStream wr = new DataOutputStream( connection.getOutputStream())){
                wr.writeBytes(body);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        try{
            responseBody = IOUtils.toString(connection.getInputStream());
        }catch(IOException ioe){
            ioe.printStackTrace();
        }

        assertEquals("{\"response\":\"true\"", responseBody.substring(0,18));
    }

    /**
     *  test when update a new activity success
     */
    @Test
    public void testUpdateActivitySuccess(){
        String responseBody = new String();
        String API_CONTEXT = "/api.sportspartner.com/v1";

        try{
            URL url = new URL("http", Bootstrap.IP_ADDRESS, PORT, API_CONTEXT + "/activity/toUpdate001");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            String body = "{\"requestorId\":\"u24\",\"requestorKey\":\"ASD\",\"activity\":{\"facilityId\":\"NULL\",\"activityId\":\"toUpdate001\",\"creatorId\":\"u24\",\"endTime\":\"Nov 18, 2017 3:11:00 AM\",\"sportId\":\"004\",\"startTime\":\"Nov 18, 2017 2:11:00 AM\"}}";


            try(DataOutputStream wr = new DataOutputStream( connection.getOutputStream())){
                wr.writeBytes(body);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        try{
            responseBody = IOUtils.toString(connection.getInputStream());
        }catch(IOException ioe){
            ioe.printStackTrace();
        }

        assertEquals("{\"response\":\"true\"}", responseBody);
    }
    /**
     *  test when delete an activity success
     */
    @Test
    public void testDeleteActivitySuccess(){
        String responseBody = new String();
        String API_CONTEXT = "/api.sportspartner.com/v1";

        try{
            URL url = new URL("http", Bootstrap.IP_ADDRESS, PORT, API_CONTEXT + "/activity/toDelete001/u24/ASD");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

        }catch(Exception e){
            e.printStackTrace();
        }

        try{
            responseBody = IOUtils.toString(connection.getInputStream());
        }catch(IOException ioe){
            ioe.printStackTrace();
        }

        assertEquals("{\"response\":\"true\"}", responseBody);

    }
}
