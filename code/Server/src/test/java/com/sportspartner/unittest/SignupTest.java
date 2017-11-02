package com.sportspartner.unittest;

import com.sportspartner.controllers.*;
import com.sportspartner.main.Bootstrap;
import com.sportspartner.service.UserService;
import com.sportspartner.dao.impl.*;
import org.json.JSONObject;
import org.junit.*;
import spark.Spark;
import spark.utils.IOUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static spark.Spark.*;

public class SignupTest {
    HttpURLConnection connection = null;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception{
        ipAddress(Bootstrap.IP_ADDRESS);
        port(Bootstrap.PORT);
        staticFileLocation("/public");
        new SignUpController(new UserService());
        Thread.sleep(4000);
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception{
        Spark.stop();
        Thread.sleep(2000);
    }

    @Before
    public void setUp(){}

    @After
    public void teardown(){
        String userId = "xuanzhang123@jhu.edu";
        UserDaoImpl userDaoImpl = new UserDaoImpl();
        if(userDaoImpl.getUser(userId)!=null) {
            new PersonDaoImpl().deletePerson(userId);
            userDaoImpl.deleteUser(userId);
        }
    }

    @Test
    /**
     * Test a successful sign up as a individual
     */
    public void testSignupSuccess(){
        JSONObject parameters;
        String responseBody = new String();
        String API_CONTEXT = "/api.sportspartner.com/v1";

        try{
            URL url = new URL("http", Bootstrap.IP_ADDRESS, Bootstrap.PORT, API_CONTEXT + "/signup/person");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            parameters = new JSONObject();
            parameters.put("userId", "xuanzhang123@jhu.edu");
            parameters.put("password", "lovejhu");
            parameters.put("confirmPassword", "lovejhu");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            try(DataOutputStream wr = new DataOutputStream( connection.getOutputStream())){
                wr.writeBytes( parameters.toString());
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
    /**
     *  Test sign up for an existed userId
     */
    public void testSignupExistedUserId(){
        JSONObject parameters;
        String responseBody = new String();
        String API_CONTEXT = "/api.sportspartner.com/v1";

        try{
            URL url = new URL("http", Bootstrap.IP_ADDRESS, Bootstrap.PORT, API_CONTEXT + "/signup/person");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            parameters = new JSONObject();
            parameters.put("userId", "xuan@jhu.edu");
            parameters.put("password", "lovejhu");
            parameters.put("confirmPassword", "lovejhu");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            try(DataOutputStream wr = new DataOutputStream( connection.getOutputStream())){
                wr.writeBytes( parameters.toString());
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        try{
            responseBody = IOUtils.toString(connection.getInputStream());
        }catch(IOException ioe){
            ioe.printStackTrace();
        }

        assertEquals("{\"response\":\"false\",\"message\":\"The email address has been taken\"}", responseBody);
    }

    @Test
    // Test sign up for when confirm password is not the same as password
    public void testSignupNotConsistentConfirm(){
        JSONObject parameters;
        String responseBody = new String();
        String API_CONTEXT = "/api.sportspartner.com/v1";

        try{
            URL url = new URL("http", Bootstrap.IP_ADDRESS, Bootstrap.PORT, API_CONTEXT + "/signup/person");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            parameters = new JSONObject();
            parameters.put("userId", "xuanzhang123@jhu.edu");
            parameters.put("password", "lovejhu");
            parameters.put("confirmPassword", "lovesleeping");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            try(DataOutputStream wr = new DataOutputStream( connection.getOutputStream())){
                wr.writeBytes( parameters.toString());
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        try{
            responseBody = IOUtils.toString(connection.getInputStream());
        }catch(IOException ioe){
            ioe.printStackTrace();
        }

        assertEquals("{\"response\":\"false\",\"message\":\"Password and confirm password are not consistent\"}", responseBody);
    }
}
