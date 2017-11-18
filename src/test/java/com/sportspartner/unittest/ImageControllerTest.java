package com.sportspartner.unittest;

import com.sportspartner.controllers.ImageController;
import com.sportspartner.controllers.LoginController;
import com.sportspartner.dao.impl.AuthorizationDaoImpl;
import com.sportspartner.dao.impl.DeviceRegistrationDaoImpl;
import com.sportspartner.main.Bootstrap;
import com.sportspartner.model.Authorization;
import com.sportspartner.model.DeviceRegistration;
import com.sportspartner.service.ImageService;
import com.sportspartner.service.UserService;
import com.sportspartner.util.ImageUtil;
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
import static org.junit.Assert.assertEquals;
import static spark.Spark.*;

import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import spark.utils.IOUtils;

import static com.sportspartner.main.Bootstrap.PORT;
import static org.junit.Assert.assertEquals;
import static spark.Spark.*;

public class ImageControllerTest {
    HttpURLConnection connection = null;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        ipAddress(Bootstrap.IP_ADDRESS);
        port(PORT);
        staticFileLocation("/public");
        new ImageController(new ImageService());

        Thread.sleep(4000);
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        Spark.stop();
        Thread.sleep(4000);
    }

    @Before
    public void setUp() {
    }

    @After
    public void teardown() {

    }


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
        String responseBody = new String();
        String API_CONTEXT = "/api.sportspartner.com/v1";

        try {
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
        assertEquals("true", responseJson.get("response").getAsString());
    }
}

