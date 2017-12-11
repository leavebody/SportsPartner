package com.sportspartner.unittest;

import com.sportspartner.controllers.FriendController;
import com.sportspartner.dao.impl.FriendDaoImpl;
import com.sportspartner.main.Bootstrap;
import com.sportspartner.service.FriendService;
import org.junit.*;
import spark.Spark;
import spark.utils.IOUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static spark.Spark.ipAddress;
import static spark.Spark.port;
import static spark.Spark.staticFileLocation;

public class FriendTest {

    HttpURLConnection connection = null;
    @BeforeClass
    public static void setUpBeforeClass() throws Exception{
        ipAddress(Bootstrap.IP_ADDRESS);
        port(Bootstrap.PORT);
        staticFileLocation("/public");
        new FriendController(new FriendService());
        Thread.sleep(2000);
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception{

        new FriendDaoImpl().newFriend("u1","u3");
        Spark.stop();
        Thread.sleep(2000);
    }

    @Before
    public void setUp(){}

    @After
    public void teardown(){

    }

    @Test
    public void getFriendSuccess(){
        String responseBody = new String();
        String API_CONTEXT = "/api.sportspartner.com/v1";
        String userId = "test";

        try{
            URL url = new URL("http", Bootstrap.IP_ADDRESS, Bootstrap.PORT, API_CONTEXT + "/"+userId+"/friends" );
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
        assertEquals("{\"response\":\"true\",\"friendlist\":[{\"userId\":\"test2\"},{\"userId\":\"test1\"}]}", responseBody);
    }

    @Test
    public void deleteFriendSuccess(){
        String responseBody = new String();
        String API_CONTEXT = "/api.sportspartner.com/v1";
        String userId1 = "u1";
        String userId2 = "u3";

        try{
            URL url = new URL("http", Bootstrap.IP_ADDRESS, Bootstrap.PORT, API_CONTEXT + "/deletefriend/"+userId1+"/"+userId2 );
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
    public void deleteFriendFailure(){
        String responseBody = new String();
        String API_CONTEXT = "/api.sportspartner.com/v1";
        String userId1 = "u4";
        String userId2 = "u3";

        try{
            URL url = new URL("http", Bootstrap.IP_ADDRESS, Bootstrap.PORT, API_CONTEXT + "/deletefriend/"+userId1+"/"+userId2 );
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

        assertEquals("{\"response\":\"false\",\"message\":\"They are not friends at all!\"}", responseBody);
    }

}
