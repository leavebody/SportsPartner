package com.sportspartner.unittest;
import com.google.gson.*;
import com.sportspartner.controllers.ActivityController;
import com.sportspartner.dao.impl.ActivityDaoImpl;
import com.sportspartner.dao.impl.ActivityMemberDaoImpl;
import com.sportspartner.main.Bootstrap;
import com.sportspartner.model.Activity;
import com.sportspartner.model.ActivityMember;
import com.sportspartner.modelvo.ActivitySearchVO;
import com.sportspartner.service.ActivityService;
import com.google.gson.JsonObject;
import org.junit.*;
import spark.Spark;
import spark.utils.IOUtils;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import org.json.JSONObject;

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

            ActivityMember addMember = new ActivityMember("aTest", "test2");
            activityMemberDao.newActivityMember(addMember);
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
            String activityId = "aTest";
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
        assertEquals(true, !responseBody.isEmpty());
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
            String activityId = "aTest";
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
        assertEquals("{\"response\":\"true\",\"activityOutline\":{\"activityId\":\"aTest\",\"creatorId\":\"test\",\"status\":\"OPEN\",\"sportIconUUID\":\"27858272-c73a-11e7-abc4-cec278b6b50a\",\"sportName\":\"Swimming\",\"startTime\":\"Nov 18, 2018 2:11:00 AM\",\"endTime\":\"Nov 18, 2018 3:11:00 AM\",\"facilityId\":\"NULL\",\"longitude\":151.211302131414,\"latitude\":-33.8507705324009,\"address\":\"JHU\",\"capacity\":4,\"size\":3}}", responseBody);
    }

    @Test
    public void testGetActivityOutlineNoSuchContent(){
        String responseBody = new String();
        String API_CONTEXT = "/api.sportspartner.com/v1";

        try{
            String activityId = "a001";
            URL url = new URL("http", Bootstrap.IP_ADDRESS, PORT, API_CONTEXT + "/activity/" + activityId+ "?content=other");
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
        assertEquals("{\"response\":\"false\",\"message\":\"No such content\"}", responseBody);
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
            URL url = new URL("http", Bootstrap.IP_ADDRESS, PORT, API_CONTEXT + "/activity_upcoming" + "?id=test&offset=0&limit=5");
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
        assertEquals("{\"response\":\"true\",\"activityOutlines\":[{\"activityId\":\"aTest\",\"creatorId\":\"test\",\"status\":\"OPEN\",\"sportIconUUID\":\"27858272-c73a-11e7-abc4-cec278b6b50a\",\"sportName\":\"Swimming\",\"startTime\":\"Nov 18, 2018 2:11:00 AM\",\"endTime\":\"Nov 18, 2018 3:11:00 AM\",\"facilityId\":\"NULL\",\"longitude\":151.211302131414,\"latitude\":-33.8507705324009,\"address\":\"JHU\",\"capacity\":4,\"size\":2}]}", responseBody);
    }

    /**
     * test when GetUpcommingActivity fails because there's no such activity
     */
    @Test
    public void testGetUpcommingActivityFailureNoactivity(){
        String responseBody = new String();
        String API_CONTEXT = "/api.sportspartner.com/v1";

        try{
            URL url = new URL("http", Bootstrap.IP_ADDRESS, PORT, API_CONTEXT + "/activity_upcoming" + "?id=test&offset=0&limit=5");
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
        assertEquals("{\"response\":\"true\",\"activityOutlines\":[{\"activityId\":\"aTest\",\"creatorId\":\"test\",\"status\":\"OPEN\",\"sportIconUUID\":\"27858272-c73a-11e7-abc4-cec278b6b50a\",\"sportName\":\"Swimming\",\"startTime\":\"Nov 18, 2018 2:11:00 AM\",\"endTime\":\"Nov 18, 2018 3:11:00 AM\",\"facilityId\":\"NULL\",\"longitude\":151.211302131414,\"latitude\":-33.8507705324009,\"address\":\"JHU\",\"capacity\":4,\"size\":2}]}", responseBody);
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
            URL url = new URL("http", Bootstrap.IP_ADDRESS, PORT, API_CONTEXT + "/activity_past" + "?id=test&offset=0&limit=5");
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
        assertEquals("{\"response\":\"true\",\"activityOutlines\":[{\"activityId\":\"aTestPast\",\"creatorId\":\"test\",\"status\":\"OPEN\",\"sportIconUUID\":\"27858272-c73a-11e7-abc4-cec278b6b50a\",\"sportName\":\"Swimming\",\"startTime\":\"Nov 18, 2017 2:11:00 AM\",\"endTime\":\"Nov 18, 2017 3:11:00 AM\",\"facilityId\":\"001\",\"longitude\":-79.55,\"latitude\":36.0,\"address\":\"JHU\",\"capacity\":4,\"size\":2}]}",responseBody);
    }

    /**
     * test when GetPastActivity fails because there's no activity
     */
    @Test
    public void testGetPastActivityFailureNoactivity(){
        String responseBody = new String();
        String API_CONTEXT = "/api.sportspartner.com/v1";

        try{
            URL url = new URL("http", Bootstrap.IP_ADDRESS, Bootstrap.PORT, API_CONTEXT + "/activity_past" + "?id=test2&offset=0&limit=5");
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
            URL url = new URL("http", Bootstrap.IP_ADDRESS, PORT, API_CONTEXT + "/activity_members/aTest");
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
        assertEquals(true, !responseBody.isEmpty());
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

    @Test
    public void testRemoveActivityMemberFailureNouser(){
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
            String userId = "u100";
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

        assertEquals("{\"response\":\"false\",\"message\":\"The user is not a member\"}", responseBody);
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

            String body = "{\"userId\":\"u24\",\"key\":\"ASD\",\"activity\":{\"activityId\":\"NULL\",\"address\":\"Sydney NSW, Australia\",\"capacity\":10,\"creatorId\":\"u24\",\"description\":\"t\",\"endTime\":\"Nov 18, 2017 3:11:00 AM\",\"facilityId\":\"NULL\",\"latitude\":-33.850770532400865,\"longitude\":151.2113021314144,\"size\":1,\"sportId\":\"004\",\"startTime\":\"Nov 18, 2017 2:11:00 AM\",\"status\":\"OPEN\",\"zipcode\":\"2000\"}}";

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

    @Test
    public void testCreateActivityFailureLackAuthorization(){
        String responseBody = new String();
        String API_CONTEXT = "/api.sportspartner.com/v1";
        JSONObject parameters = new JSONObject();

        try{
            URL url = new URL("http", Bootstrap.IP_ADDRESS, PORT, API_CONTEXT + "/activity");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            String body = "{\"userId\":\"u24\",\"key\":\"AAA\",\"activity\":{\"activityId\":\"NULL\",\"address\":\"Sydney NSW, Australia\",\"capacity\":10,\"creatorId\":\"u1\",\"description\":\"t\",\"endTime\":\"Nov 18, 2017 3:11:00 AM\",\"facilityId\":\"NULL\",\"latitude\":-33.850770532400865,\"longitude\":151.2113021314144,\"size\":1,\"sportId\":\"004\",\"startTime\":\"Nov 18, 2017 2:11:00 AM\",\"status\":\"OPEN\",\"zipcode\":\"2000\"}}";

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

        assertEquals("{\"response\":\"false\",\"message\":\"Lack Authorization\"}", responseBody);
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
    @Test
    public void testUpdateActivityFailureLackAuthorization(){
        String responseBody = new String();
        String API_CONTEXT = "/api.sportspartner.com/v1";

        try{
            URL url = new URL("http", Bootstrap.IP_ADDRESS, PORT, API_CONTEXT + "/activity/toUpdate001");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            String body = "{\"requestorId\":\"u24\",\"requestorKey\":\"DSA\",\"activity\":{\"facilityId\":\"NULL\",\"activityId\":\"toUpdate001\",\"creatorId\":\"u24\",\"endTime\":\"Nov 18, 2017 3:11:00 AM\",\"sportId\":\"004\",\"startTime\":\"Nov 18, 2017 2:11:00 AM\"}}";


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

        assertEquals("{\"response\":\"false\",\"message\":\"Lack authorization to update activity info\"}", responseBody);
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

    @Test
    public void testDeleteActivityFailureNoActivity(){
        String responseBody = new String();
        String API_CONTEXT = "/api.sportspartner.com/v1";

        try{
            URL url = new URL("http", Bootstrap.IP_ADDRESS, PORT, API_CONTEXT + "/activity/nottoDelete001/u24/ASD");
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

        assertEquals("{\"response\":\"false\",\"message\":\"No such activity\"}", responseBody);

    }

    @Test
    public void testDeleteActivityFailureLackAuthorization(){
        String responseBody = new String();
        String API_CONTEXT = "/api.sportspartner.com/v1";

        try{
            URL url = new URL("http", Bootstrap.IP_ADDRESS, PORT, API_CONTEXT + "/activity/toUpdate001/u24/DSA");
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

        assertEquals("{\"response\":\"false\",\"message\":\"Lack authorization to cancel the activity\"}", responseBody);

    }


    @Test
    public void testSearchActivityDateAndTime(){
        String responseBody = new String();
        String API_CONTEXT = "/api.sportspartner.com/v1";

        try{
            URL url = new URL("http", Bootstrap.IP_ADDRESS, PORT, API_CONTEXT + "/search?type=activity&limit=3&offset=0");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            String body = "{\"sportId\":\"NULL\", \"startTime\":\"2017.11.18 AD at 07:11:00 UTC\", \"endTime\":\"2017.11.18 AD at 09:11:00 UTC\", \"longitude\":\"1000\", \"latitude\":\"1000\", \"capacity\":\"-1\"}";

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
        assertEquals(true, !responseBody.isEmpty());
    }


    @Test
    public void testSearchActivityOnlyTime(){
        String responseBody = new String();
        String API_CONTEXT = "/api.sportspartner.com/v1";

        try{
            URL url = new URL("http", Bootstrap.IP_ADDRESS, PORT, API_CONTEXT + "/search?type=activity&limit=3&offset=0");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            //String body = new Gson().toJson(new ActivitySearchVO("NULL","2018.11.18 AD at 07:11:00 UTC",  "1900.11.18 AD at 07:11:00 UTC",  "NULL",  1000, 1000, -1));
            String body = "{\"sportId\":\"NULL\", \"startTime\":\"1900.11.18 AD at 07:11:00 UTC\", \"endTime\":\"1900.11.18 AD at 09:11:00 UTC\", \"longitude\":\"1000\", \"latitude\":\"1000\", \"capacity\":\"-1\"}";

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
        assertEquals(true, !responseBody.isEmpty());
    }

    @Test
    public void testSearchActivityOnlyStartOnlyTime(){
        String responseBody = new String();
        String API_CONTEXT = "/api.sportspartner.com/v1";

        try{
            URL url = new URL("http", Bootstrap.IP_ADDRESS, PORT, API_CONTEXT + "/search?type=activity&limit=3&offset=0");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            //String body = new Gson().toJson(new ActivitySearchVO("NULL","2018.11.18 AD at 07:11:00 UTC",  "1900.11.18 AD at 07:11:00 UTC",  "NULL",  1000, 1000, -1));
            String body = "{\"sportId\":\"NULL\", \"startTime\":\"1900.11.18 AD at 07:11:00 UTC\", \"endTime\":\"NULL\", \"longitude\":\"1000\", \"latitude\":\"1000\", \"capacity\":\"-1\"}";

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
        assertEquals(true, !responseBody.isEmpty());
    }

    @Test
    public void testSearchActivityOnlyStart(){
        String responseBody = new String();
        String API_CONTEXT = "/api.sportspartner.com/v1";

        try{
            URL url = new URL("http", Bootstrap.IP_ADDRESS, PORT, API_CONTEXT + "/search?type=activity&limit=3&offset=0");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            //String body = new Gson().toJson(new ActivitySearchVO("NULL","2018.11.18 AD at 07:11:00 UTC",  "1900.11.18 AD at 07:11:00 UTC",  "NULL",  1000, 1000, -1));
            String body = "{\"sportId\":\"NULL\", \"startTime\":\"1900.11.18 AD at 07:11:00 UTC\", \"endTime\":\"NULL\", \"longitude\":\"1000\", \"latitude\":\"1000\", \"capacity\":\"-1\"}";

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
        assertEquals(true, !responseBody.isEmpty());
    }

    @Test
    public void testSearchActivityOnlyEnd(){
        String responseBody = new String();
        String API_CONTEXT = "/api.sportspartner.com/v1";

        try{
            URL url = new URL("http", Bootstrap.IP_ADDRESS, PORT, API_CONTEXT + "/search?type=activity&limit=3&offset=0");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            //String body = new Gson().toJson(new ActivitySearchVO("NULL","2018.11.18 AD at 07:11:00 UTC",  "1900.11.18 AD at 07:11:00 UTC",  "NULL",  1000, 1000, -1));
            String body = "{\"sportId\":\"NULL\", \"startTime\":\"NULL\", \"endTime\":\"2017.11.18 AD at 09:11:00 UTC\", \"longitude\":\"1000\", \"latitude\":\"1000\", \"capacity\":\"-1\"}";

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
        assertEquals(true, !responseBody.isEmpty());
    }@Test
    public void testSearchActivityOnlyEndOnlyTime(){
        String responseBody = new String();
        String API_CONTEXT = "/api.sportspartner.com/v1";

        try{
            URL url = new URL("http", Bootstrap.IP_ADDRESS, PORT, API_CONTEXT + "/search?type=activity&limit=3&offset=0");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            //String body = new Gson().toJson(new ActivitySearchVO("NULL","2018.11.18 AD at 07:11:00 UTC",  "1900.11.18 AD at 07:11:00 UTC",  "NULL",  1000, 1000, -1));
            String body = "{\"sportId\":\"NULL\", \"startTime\":\"NULL\", \"endTime\":\"1900.11.18 AD at 09:11:00 UTC\", \"longitude\":\"1000\", \"latitude\":\"1000\", \"capacity\":\"-1\"}";

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
        assertEquals(true, !responseBody.isEmpty());
    }



    @Test
    public void testRecommendActivity(){
        String responseBody = new String();
        String API_CONTEXT = "/api.sportspartner.com/v1";
        // test GET /activity_recommend?id=u2&latitude=38.567&langitude=76.312&limit=10&offset=0
        try{
            URL url = new URL("http", Bootstrap.IP_ADDRESS, PORT, API_CONTEXT + "/activity_recommend?id=u2&latitude=38.567&longitude=76.312&limit=10&offset=0");
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
        assertEquals(true, !responseBody.isEmpty());
    }

    // {"userId":"u24","key":"ASD","facilityreview":{"activityid":"a002", "reviewer":"u24", "reviewee":"001", "score":5, "comments":"test facility comment 057"}, "userreviews":[{"activityid":"a002", "reviewer":"u24", "reviewee":"u1", "punctuality":5,"participation":5, "comments":"test comment 057"}]}
    @Test
    public void testReviewActivity(){
        String responseBody = new String();
        String API_CONTEXT = "/api.sportspartner.com/v1";

        try{
            URL url = new URL("http", Bootstrap.IP_ADDRESS, PORT, API_CONTEXT + "/activityreview/aTestPast");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            String body = "{\"userId\":\"test\",\"key\":\"testKey\",\"facilityreview\":{\"activityid\":\"aTestPast\", \"reviewer\":\"test\", \"reviewee\":\"001\", \"score\":5, \"comments\":\"test facility comment 057\"}, \"userreviews\":[{\"activityid\":\"aTestPast\", \"reviewer\":\"test\", \"reviewee\":\"test1\", \"punctuality\":5,\"participation\":5, \"comments\":\"test comment 057\"}]}";

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

    //DELETE https://api.sportspartner.com/v1/activity_leave/:activityId?userId=userId&key=key
    @Test
    public void testLeaveActivity(){
        String responseBody = new String();
        String API_CONTEXT = "/api.sportspartner.com/v1";

        try{
            URL url = new URL("http", Bootstrap.IP_ADDRESS, PORT, API_CONTEXT + "/activity_leave/aTest?userId=test2&key=testKey2");
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
