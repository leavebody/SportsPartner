package com.sportspartner.unittest;

<<<<<<< HEAD

import com.google.gson.*;
import com.sportspartner.controllers.ActivityController;
import com.sportspartner.controllers.FacilityController;
import com.sportspartner.controllers.ImageController;
import com.sportspartner.main.Bootstrap;
import com.sportspartner.service.ActivityService;
import com.google.gson.JsonObject;
import com.sportspartner.service.FacilityService;
import com.sportspartner.service.ImageService;
import com.sportspartner.util.ImageUtil;
=======
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
>>>>>>> c0f04de7699a99b444d6247aee44e1e3e4a9f603
import org.json.JSONObject;
import org.junit.*;
import spark.Spark;
import spark.utils.IOUtils;

<<<<<<< HEAD
import java.awt.image.BufferedImage;
=======
>>>>>>> c0f04de7699a99b444d6247aee44e1e3e4a9f603
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
<<<<<<< HEAD
import spark.utils.IOUtils;

import static com.sportspartner.main.Bootstrap.PORT;
import static org.junit.Assert.assertEquals;
import static spark.Spark.*;
public class ImageControllerTest {
    HttpURLConnection connection = null;
=======

import static org.junit.Assert.assertEquals;
import static spark.Spark.*;

public class ImageControllerTest {
    HttpURLConnection connection = null;
    static String successUUID = null;
>>>>>>> c0f04de7699a99b444d6247aee44e1e3e4a9f603

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        ipAddress(Bootstrap.IP_ADDRESS);
<<<<<<< HEAD
        port(PORT);
        staticFileLocation("/public");
        new ImageController(new ImageService());

        Thread.sleep(4000);
=======
        port(Bootstrap.PORT);
        staticFileLocation("/public");
        new LoginController(new UserService());
        Thread.sleep(2000);
>>>>>>> c0f04de7699a99b444d6247aee44e1e3e4a9f603
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        Spark.stop();
<<<<<<< HEAD
        Thread.sleep(4000);
=======
        Thread.sleep(2000);
>>>>>>> c0f04de7699a99b444d6247aee44e1e3e4a9f603
    }

    @Before
    public void setUp() {
    }

    @After
    public void teardown() {

    }

<<<<<<< HEAD
    /**
     * test when GetActivityDetail succeeds
     */
    @Test
    public void testGetImage() {
        String responseBody = new String();
        String API_CONTEXT = "/api.sportspartner.com/v1";

        try {
            URL url = new URL("http", Bootstrap.IP_ADDRESS, PORT, API_CONTEXT + "/resource/20e7d49f-5bb6-431e-a511-bc5e0edb349f?type=small");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
        } catch (Exception e) {
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

    /**
     * test when updateIcon succeeds
     */
    @Test
    public void testUpdateIcon() {
=======
    @Test
    /**
     *  Test login for right input of userId and password
     */
    public void testLoginSuccess() {
        JSONObject parameters;
>>>>>>> c0f04de7699a99b444d6247aee44e1e3e4a9f603
        String responseBody = new String();
        String API_CONTEXT = "/api.sportspartner.com/v1";

        try {
<<<<<<< HEAD
            String spId = "shirish@gmail.com";
            String key = "handsome";
            String object = "USER";
            ImageUtil imageUtil = new ImageUtil();
            BufferedImage img = imageUtil.getImage("./res/usericon/shirish@gmail.com_origin.png");
            BufferedImage resizedimage = imageUtil.resizeImage(img);
            String image = imageUtil.imageToBase64(resizedimage);
            JsonObject json = new JsonObject();
            json.addProperty("userId", spId);
            json.addProperty("key", key);
            json.addProperty("object", object);
            json.addProperty("image", image);
            String body = new Gson().toJson(json, JsonObject.class);

            URL url = new URL("http", Bootstrap.IP_ADDRESS, PORT, API_CONTEXT + "/resource/icon/shirish@gmail.com");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            try(DataOutputStream wr = new DataOutputStream( connection.getOutputStream())){
                wr.writeBytes(body);
=======
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
>>>>>>> c0f04de7699a99b444d6247aee44e1e3e4a9f603
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
<<<<<<< HEAD
=======

>>>>>>> c0f04de7699a99b444d6247aee44e1e3e4a9f603
        try {
            responseBody = IOUtils.toString(connection.getInputStream());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
<<<<<<< HEAD
        JsonObject responseJson = new Gson().fromJson(responseBody, JsonObject.class);
        assertEquals("true", responseJson.get("response").getAsString());
    }
}

=======

        JsonObject responseJson = new Gson().fromJson(responseBody, JsonObject.class);
        String response = responseJson.get("response").getAsString();
        successUUID = responseJson.get("key").getAsString();
        assertEquals("true", response);
    }
}


>>>>>>> c0f04de7699a99b444d6247aee44e1e3e4a9f603
