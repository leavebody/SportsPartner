package com.sportspartner.unittest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sportspartner.controllers.NotificationController;
import com.sportspartner.dao.impl.ActivityMemberDaoImpl;
import com.sportspartner.dao.impl.FriendDaoImpl;
import com.sportspartner.dao.impl.PendingFriendRequestDaoImpl;
import com.sportspartner.dao.impl.PendingJoinActivityRequestDaoImpl;
import com.sportspartner.main.Bootstrap;
import com.sportspartner.model.ActivityMember;
import com.sportspartner.model.PendingFriendRequest;
import com.sportspartner.model.PendingJoinActivityRequest;
import com.sportspartner.service.NotificationService;
import org.junit.*;
import spark.Spark;
import spark.utils.IOUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static spark.Spark.ipAddress;
import static spark.Spark.port;
import static spark.Spark.staticFileLocation;

public class NotificationTest {
    HttpURLConnection connection = null;
    @BeforeClass
    public static void setUpBeforeClass() throws Exception{
        ipAddress(Bootstrap.IP_ADDRESS);
        port(Bootstrap.PORT);
        staticFileLocation("/public");
        new NotificationController(new NotificationService());
        Thread.sleep(2000);
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception{

        new PendingFriendRequestDaoImpl().deletePendingRequest(new PendingFriendRequest("u5","u1"));
        new PendingFriendRequestDaoImpl().deletePendingRequest(new PendingFriendRequest("u3","u4"));
        new FriendDaoImpl().deleteFriend("u3","u24");
        new PendingFriendRequestDaoImpl().newPendingRequest(new PendingFriendRequest("u24","u3"));
        new PendingFriendRequestDaoImpl().newPendingRequest(new PendingFriendRequest("u4","u3"));

        new PendingJoinActivityRequestDaoImpl().deletePendingRequest(new PendingJoinActivityRequest("04cb3e3b-ad38-4d1c-920b-c64f4b6c5780", "u1","xuanzhang@jhu.edu"));
        new ActivityMemberDaoImpl().deleteActivityMember(new ActivityMember("213cfde5-ee24-43b5-9d46-f8317c8fab3b", "u1"));
        new PendingJoinActivityRequestDaoImpl().newPendingRequest(new PendingJoinActivityRequest("213cfde5-ee24-43b5-9d46-f8317c8fab3b", "u1", "xuanzhang@jhu.edu"));
        new PendingJoinActivityRequestDaoImpl().newPendingRequest(new PendingJoinActivityRequest("71f75bef-9a5c-45ce-b833-ed1de572eb74", "u1", "xuanzhang@jhu.edu"));
        Spark.stop();

        Thread.sleep(2000);
    }

    @Before
    public void setUp(){}

    @After
    public void teardown(){

    }

    @Test
    public void SendFriendRequestSuccess()
    {
        String responseBody = new String();
        String API_CONTEXT = "/api.sportspartner.com/v1";
        String userId1 = "u3";
        String userId2 = "u4";

        try{
            URL url = new URL("http", Bootstrap.IP_ADDRESS, Bootstrap.PORT, API_CONTEXT + "/friendrequest/" + userId1+ "/"+ userId2);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
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
    public void SendFriendRequestFailureAlreadyFriends()
    {
        String responseBody = new String();
        String API_CONTEXT = "/api.sportspartner.com/v1";
        String userId1 = "u1";
        String userId2 = "u3";

        try{
            URL url = new URL("http", Bootstrap.IP_ADDRESS, Bootstrap.PORT, API_CONTEXT + "/friendrequest/" + userId1+ "/"+ userId2);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
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

        assertEquals("{\"response\":\"false\",\"message\":\"They are already friends.\"}", responseBody);
    }

    @Test
    public void SendFriendRequestFailurePendingRequest()
    {
        String responseBody = new String();
        String API_CONTEXT = "/api.sportspartner.com/v1";
        String userId1 = "u5";
        String userId2 = "u1";

        try{
            URL url = new URL("http", Bootstrap.IP_ADDRESS, Bootstrap.PORT, API_CONTEXT + "/friendrequest/" + userId1+ "/"+ userId2);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
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

        assertEquals("{\"response\":\"false\",\"message\":\"Fail to send GCM.\"}", responseBody);
    }

    @Test
    public void AcceptFriendRequestSucceess()
    {
        String responseBody = new String();
        String API_CONTEXT = "/api.sportspartner.com/v1";
        String userId1 = "u24";
        String userId2 = "u3";

        try{
            URL url = new URL("http", Bootstrap.IP_ADDRESS, Bootstrap.PORT, API_CONTEXT + "/acceptfriendrequest/" + userId1+ "/"+ userId2);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
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
    public void AcceptFriendRequestAllReadyFriends()
    {
        String responseBody = new String();
        String API_CONTEXT = "/api.sportspartner.com/v1";
        String userId1 = "u3";
        String userId2 = "u1";

        try{
            URL url = new URL("http", Bootstrap.IP_ADDRESS, Bootstrap.PORT, API_CONTEXT + "/acceptfriendrequest/" + userId1+ "/"+ userId2);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
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

        assertEquals("{\"response\":\"false\",\"message\":\"They are already friends.\"}", responseBody);
    }
    @Test
    public void DeclineFriendRequestNotFriends()
    {
        String responseBody = new String();
        String API_CONTEXT = "/api.sportspartner.com/v1";
        String userId1 = "u24";
        String userId2 = "u1";

        try{
            URL url = new URL("http", Bootstrap.IP_ADDRESS, Bootstrap.PORT, API_CONTEXT + "/declinefriendrequest/" + userId1+ "/"+ userId2);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
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

        assertEquals("{\"response\":\"false\",\"message\":\"No such pending request.\"}", responseBody);
    }
    @Test
    public void DeclineFriendRequestSuccess()
    {
        String responseBody = new String();
        String API_CONTEXT = "/api.sportspartner.com/v1";
        String userId1 = "u4";
        String userId2 = "u3";

        try{
            URL url = new URL("http", Bootstrap.IP_ADDRESS, Bootstrap.PORT, API_CONTEXT + "/declinefriendrequest/" + userId1+ "/"+ userId2);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
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
    public void SendJoinActivityApplicationSuccess()
    {
        String responseBody = new String();
        String API_CONTEXT = "/api.sportspartner.com/v1";
        String activityId = "04cb3e3b-ad38-4d1c-920b-c64f4b6c5780";
        String creatorId = "xuanzhang@jhu.edu";
        String senderId = "u1";

        JsonObject json = new JsonObject();
        json.addProperty("creatorId", creatorId);
        json.addProperty("senderId", senderId);
        String body = new Gson().toJson(json, JsonObject.class);
        try {
            URL url = new URL("http", Bootstrap.IP_ADDRESS, Bootstrap.PORT, API_CONTEXT + "/joinactivityapplication/" + activityId);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
        }catch(Exception e){
            e.printStackTrace();}

        try(DataOutputStream wr = new DataOutputStream( connection.getOutputStream())){
            wr.writeBytes(body);
        }catch (Exception e) {
            e.printStackTrace();
        }

        try {
            responseBody = IOUtils.toString(connection.getInputStream());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        JsonObject responseJson = new Gson().fromJson(responseBody, JsonObject.class);
        assertEquals("true", responseJson.get("response").getAsString());
    }

    @Test
    public void AcceptJoinActivityApplicationSuccess(){
        String responseBody = new String();
        String API_CONTEXT = "/api.sportspartner.com/v1";
        String activityId = "213cfde5-ee24-43b5-9d46-f8317c8fab3b";
        String creatorId = "xuanzhang@jhu.edu";
        String userId = "u1";
        String creatorKey = "a4bd4784-ab92-4000-baa3-2d02c94f5c2f";

        JsonObject json = new JsonObject();
        json.addProperty("creatorId", creatorId);
        json.addProperty("userId", userId);
        json.addProperty("creatorKey", creatorKey);
        String body = new Gson().toJson(json, JsonObject.class);
        try {
            URL url = new URL("http", Bootstrap.IP_ADDRESS, Bootstrap.PORT, API_CONTEXT + "/acceptjoinactivityapplication/" + activityId);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
        }catch(Exception e){
            e.printStackTrace();}

        try(DataOutputStream wr = new DataOutputStream( connection.getOutputStream())){
            wr.writeBytes(body);
        }catch (Exception e) {
            e.printStackTrace();
        }

        try {
            responseBody = IOUtils.toString(connection.getInputStream());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        JsonObject responseJson = new Gson().fromJson(responseBody, JsonObject.class);
        assertEquals("true", responseJson.get("response").getAsString());
    }


    @Test
    public void DeclineJoinActivityApplicationSuccess(){
        String responseBody = new String();
        String API_CONTEXT = "/api.sportspartner.com/v1";
        String activityId = "71f75bef-9a5c-45ce-b833-ed1de572eb74";
        String creatorId = "xuanzhang@jhu.edu";
        String userId = "u1";
        String creatorKey = "a4bd4784-ab92-4000-baa3-2d02c94f5c2f";

        JsonObject json = new JsonObject();
        json.addProperty("creatorId", creatorId);
        json.addProperty("userId", userId);
        json.addProperty("creatorKey", creatorKey);
        String body = new Gson().toJson(json, JsonObject.class);
        try {
            URL url = new URL("http", Bootstrap.IP_ADDRESS, Bootstrap.PORT, API_CONTEXT + "/declinejoinactivityapplication/" + activityId);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
        }catch(Exception e){
            e.printStackTrace();}

        try(DataOutputStream wr = new DataOutputStream( connection.getOutputStream())){
            wr.writeBytes(body);
        }catch (Exception e) {
            e.printStackTrace();
        }

        try {
            responseBody = IOUtils.toString(connection.getInputStream());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        JsonObject responseJson = new Gson().fromJson(responseBody, JsonObject.class);
        assertEquals("true", responseJson.get("response").getAsString());
    }

}
