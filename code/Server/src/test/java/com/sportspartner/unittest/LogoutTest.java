package com.sportspartner.unittest;

import com.sportspartner.controllers.LogoutController;
import com.sportspartner.dao.impl.AuthorizationDaoImpl;
import com.sportspartner.main.Bootstrap;
import com.sportspartner.model.Authorization;
import com.sportspartner.service.UserService;
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

public class LogoutTest {
    HttpURLConnection connection = null;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception{
        ipAddress(Bootstrap.IP_ADDRESS);
        port(Bootstrap.PORT);
        staticFileLocation("/public");
        new LogoutController(new UserService());
        Thread.sleep(2000);
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
        AuthorizationDaoImpl authorizationDaoImpl = new AuthorizationDaoImpl();
        Authorization authorization = new Authorization("u1", "nonono");
        if(!authorizationDaoImpl.hasAuthorization(authorization))
            authorizationDaoImpl.newAuthorization(authorization);
    }

    @Test
    // Test a successful log out
    public void testLogoutSuccess(){
        String responseBody = new String();
        String API_CONTEXT = "/api.sportspartner.com/v1";

        try{
            URL url = new URL("http", Bootstrap.IP_ADDRESS, Bootstrap.PORT, API_CONTEXT + "/logout?userId=u1&key=nonono");
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
