package com.sportspartner.unittest;
import com.google.gson.*;
import com.sportspartner.controllers.ActivityController;
import com.sportspartner.main.Bootstrap;
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
import spark.utils.IOUtils;

import static com.sportspartner.main.Bootstrap.PORT;
import static org.junit.Assert.assertEquals;
import static spark.Spark.*;
public class ActivityTest {
    HttpURLConnection connection = null;



    @BeforeClass
    public static void setUpBeforeClass() throws Exception{
        ipAddress(Bootstrap.IP_ADDRESS);
        port(PORT);
        staticFileLocation("/public");
        new ActivityController(new ActivityService());

        Thread.sleep(4000);
    }

    @AfterClass
    public static void tearDownAfterClass()throws Exception{
        Spark.stop();
        Thread.sleep(4000);
    }

    @Before
    public void setUp(){}

    @After
    public void teardown(){

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
        assertEquals("{\"response\":\"true\",\"activity\":{\"activityId\":\"a001\",\"status\":\"FINISHED\",\"sportIconUUID\":\"01fb3462-bc1c-11e7-abc4-cec278b6b50a\",\"sportName\":\"Tennis\",\"startTime\":\"Oct 25, 2017 12:00:00 PM\",\"endTime\":\"Oct 25, 2017 2:00:00 PM\",\"facilityId\":\"001\",\"facilityName\":\"JHU gym\",\"longitude\":76.312,\"latitude\":38.567,\"address\":\"JHU Gym\",\"capacity\":6,\"size\":5,\"creatorId\":\"u1\",\"members\":[{\"userId\":\"xuanzhang@jhu.edu\",\"userName\":\"Xuan Zhang\",\"iconUUID\":\"20e7d49f-5bb6-431e-a511-bc5e0edb349f\"},{\"userId\":\"zxiao10@jhu.edu\",\"userName\":\"Zihao Xiao\",\"iconUUID\":\"007\"},{\"userId\":\"leavebody@hotmail.com\",\"userName\":\"Xiaochen Li\",\"iconUUID\":\"92cf9134-e40b-40f5-baf3-f7d9990a61bf\"},{\"userId\":\"yujiaxiao0223@gmail.com\",\"userName\":\"Yujia Xiao\",\"iconUUID\":\"6c1e2972-d140-4055-8560-af5bad24448d\"}],\"detail\":\"Join us!\",\"discussion\":[{\"activityId\":\"a001\",\"commentId\":\"001\",\"authorId\":\"u1\",\"time\":\"Oct 26, 2017 5:24:02 PM\",\"content\":\"Good activity!\"}]},\"userType\":\"STRANGER\"}", response);


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
        assertEquals("{\"response\":\"true\",\"activityOutline\":{\"activityId\":\"a001\",\"creatorId\":\"u1\",\"status\":\"FINISHED\",\"sportIconUUID\":\"01fb3462-bc1c-11e7-abc4-cec278b6b50a\",\"sportName\":\"Tennis\",\"startTime\":\"Oct 25, 2017 12:00:00 PM\",\"endTime\":\"Oct 25, 2017 2:00:00 PM\",\"facilityId\":\"001\",\"longitude\":76.312,\"latitude\":38.567,\"address\":\"JHU Gym\",\"capacity\":6,\"size\":5}}", responseBody);
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
        assertEquals("{\"response\":\"true\",\"activityOutlines\":[{\"activityId\":\"e781007a-b58a-4596-bec0-23f28e907b9f\",\"creatorId\":\"u1\",\"status\":\"OPEN\",\"sportIconUUID\":\"01fb2db4-bc1c-11e7-abc4-cec278b6b50a\",\"sportName\":\"Swimming\",\"startTime\":\"Nov 11, 2017 4:31:00 PM\",\"endTime\":\"Nov 12, 2017 5:31:00 PM\",\"facilityId\":\"NULL\",\"longitude\":-122.08470895886423,\"latitude\":37.4277097302242,\"capacity\":2,\"size\":1},{\"activityId\":\"a003\",\"creatorId\":\"u1\",\"status\":\"OPEN\",\"sportIconUUID\":\"01fb3462-bc1c-11e7-abc4-cec278b6b50a\",\"sportName\":\"Tennis\",\"startTime\":\"Oct 25, 2019 10:00:00 AM\",\"endTime\":\"Oct 25, 2019 11:30:00 AM\",\"facilityId\":\"001\",\"longitude\":0.0,\"latitude\":0.0,\"capacity\":2,\"size\":1}]}", responseBody);
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
        assertEquals("{\"response\":\"true\",\"activityOutlines\":[{\"activityId\":\"0113faac-8317-4a1a-b4f1-43bcd5d1718a\",\"creatorId\":\"u1\",\"status\":\"OPEN\",\"sportIconUUID\":\"01fb3462-bc1c-11e7-abc4-cec278b6b50a\",\"sportName\":\"Tennis\",\"startTime\":\"Nov 9, 2017 4:26:00 PM\",\"endTime\":\"Nov 9, 2017 6:26:00 PM\",\"facilityId\":\"NULL\",\"longitude\":-122.08191677927972,\"latitude\":37.422482000301194,\"capacity\":9,\"size\":1},{\"activityId\":\"a006\",\"creatorId\":\"zihao@jhu.edu\",\"status\":\"FINISHED\",\"sportIconUUID\":\"01fb2db4-bc1c-11e7-abc4-cec278b6b50a\",\"sportName\":\"Swimming\",\"startTime\":\"Oct 28, 2017 10:00:00 AM\",\"endTime\":\"Oct 28, 2017 2:00:00 PM\",\"facilityId\":\"001\",\"longitude\":0.0,\"latitude\":0.0,\"capacity\":2,\"size\":2},{\"activityId\":\"a005\",\"creatorId\":\"xuanzhang666@jhu.edu\",\"status\":\"FINISHED\",\"sportIconUUID\":\"01fb3200-bc1c-11e7-abc4-cec278b6b50a\",\"sportName\":\"Basketball\",\"startTime\":\"Oct 27, 2017 10:00:00 AM\",\"endTime\":\"Oct 27, 2017 2:00:00 PM\",\"facilityId\":\"001\",\"longitude\":0.0,\"latitude\":0.0,\"capacity\":6,\"size\":5},{\"activityId\":\"a004\",\"creatorId\":\"xuanzhang666@jhu.edu\",\"status\":\"FINISHED\",\"sportIconUUID\":\"01fb2db4-bc1c-11e7-abc4-cec278b6b50a\",\"sportName\":\"Swimming\",\"startTime\":\"Oct 26, 2017 10:00:00 AM\",\"endTime\":\"Oct 26, 2017 2:00:00 PM\",\"facilityId\":\"001\",\"longitude\":0.0,\"latitude\":0.0,\"capacity\":3,\"size\":3},{\"activityId\":\"a001\",\"creatorId\":\"u1\",\"status\":\"FINISHED\",\"sportIconUUID\":\"01fb3462-bc1c-11e7-abc4-cec278b6b50a\",\"sportName\":\"Tennis\",\"startTime\":\"Oct 25, 2017 12:00:00 PM\",\"endTime\":\"Oct 25, 2017 2:00:00 PM\",\"facilityId\":\"001\",\"longitude\":76.312,\"latitude\":38.567,\"address\":\"JHU Gym\",\"capacity\":6,\"size\":5}]}",responseBody);
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
}
