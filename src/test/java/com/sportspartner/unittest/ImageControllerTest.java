package com.sportspartner.unittest;

import com.sportspartner.controllers.LoginController;
import com.sportspartner.dao.impl.AuthorizationDaoImpl;
import com.sportspartner.dao.impl.DeviceRegistrationDaoImpl;
import com.sportspartner.main.Bootstrap;
import com.sportspartner.model.Authorization;
import com.sportspartner.model.DeviceRegistration;
import com.sportspartner.service.UserService;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import spark.Spark;

import java.net.HttpURLConnection;

import static spark.Spark.ipAddress;
import static spark.Spark.port;
import static spark.Spark.staticFileLocation;

import com.google.gson.*;
import com.sportspartner.controllers.LoginController;
import com.sportspartner.dao.impl.AuthorizationDaoImpl;
import com.sportspartner.dao.impl.DeviceRegistrationDaoImpl;
import com.sportspartner.dao.impl.PersonDaoImpl;
import com.sportspartner.dao.impl.UserDaoImpl;
import com.sportspartner.main.Bootstrap;
import com.sportspartner.model.Authorization;
import com.sportspartner.model.DeviceRegistration;
import com.sportspartner.service.UserService;
import com.google.gson.JsonObject;
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

public class ImageControllerTest {
    HttpURLConnection connection = null;
    static String successUUID = null;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        ipAddress(Bootstrap.IP_ADDRESS);
        port(Bootstrap.PORT);
        staticFileLocation("/public");
        new LoginController(new UserService());
        Thread.sleep(2000);
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        Spark.stop();
        Thread.sleep(2000);
    }

    @Before
    public void setUp() {
    }

    @After
    public void teardown() {

    }

    @Test
    /**
     *  Test login for right input of userId and password
     */
    public void testLoginSuccess() {
        JSONObject parameters;
        String responseBody = new String();
        String API_CONTEXT = "/api.sportspartner.com/v1";

        try {
            URL url = new URL("http", Bootstrap.IP_ADDRESS, Bootstrap.PORT, API_CONTEXT + "/login");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            parameters = new JSONObject();
            parameters.put("userId", "u1");
            parameters.put("password", "p1");
            parameters.put("registrationId", "string!");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
                wr.writeBytes(parameters.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            responseBody = IOUtils.toString(connection.getInputStream());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        JsonObject responseJson = new Gson().fromJson(responseBody, JsonObject.class);
        String response = responseJson.get("response").getAsString();
        successUUID = responseJson.get("key").getAsString();
        assertEquals("true", response);
    }
}


