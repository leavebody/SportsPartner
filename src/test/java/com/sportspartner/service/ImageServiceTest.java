package com.sportspartner.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sportspartner.dao.impl.ActivityMemberDaoImpl;
import com.sportspartner.main.Bootstrap;
import com.sportspartner.model.Activity;
import com.sportspartner.model.ActivityMember;
import com.sportspartner.util.ImageUtil;
import com.sportspartner.util.JsonResponse;
import com.sportspartner.util.JsonTransformer;
import org.junit.*;
import spark.Spark;
import spark.utils.IOUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.sportspartner.main.Bootstrap.PORT;
import static org.junit.Assert.assertEquals;
import static spark.Spark.ipAddress;
import static spark.Spark.port;
import static spark.Spark.staticFileLocation;

import com.sportspartner.service.ActivityService;
import com.sportspartner.controllers.ActivityController;

public class ImageServiceTest {
    private ImageService imageService = new ImageService();
    private JsonResponse resp = new JsonResponse();

    @BeforeClass
    public static void setUpBeforeClass() throws Exception{
    }

    @AfterClass
    public static void tearDownAfterClass()throws Exception{
        Spark.stop();
        Thread.sleep(2000);
    }

    @Before
    public void setUp(){}

    @After
    public void teardown(){

    }

    /**
     * Test getImage as an icon succeed.
     */
    @Test
    public void testGetImage(){
        String response = null;
        try{
            String iconUUID = "20e7d49f-5bb6-431e-a511-bc5e0edb349f";
            String type = "small";
            resp = imageService.getImage(iconUUID, type);
        }catch(Exception e){
            e.printStackTrace();
        }
        assertEquals("true", resp.getResponse());
    }

    /**
     * Test get original image succeed.
     */
    @Test
    public void testGetOriginalImage() {
        String response = null;
        try {
            String iconUUID = "20e7d49f-5bb6-431e-a511-bc5e0edb349f";
            String type = "origin";
            resp = imageService.getImage(iconUUID, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals("true", resp.getResponse());
    }

    /**
     * Test get an nonexistent image.
     */
    @Test
    public void testGetImageNoId() {
        String response = null;
        try {
            String iconUUID = "noimage";
            String type = "origin";
            resp = imageService.getImage(iconUUID, type);
            response = new Gson().toJson(resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals("{\"response\":\"false\",\"message\":\"no such icon\"}",response);
    }

    /**
     * Test get an image with wrong type.
     */
    @Test
    public void testGetImageNoType() {
        String response = null;
        try {
            String iconUUID = "20e7d49f-5bb6-431e-a511-bc5e0edb349f";
            String type = "notype";
            resp = imageService.getImage(iconUUID, type);
            response = new Gson().toJson(resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals("{\"response\":\"false\",\"message\":\"wrong type\"}",response);
    }

    /**
     * Test update an icon succeed.
     */
    @Test
    public void testUpdateIcon(){
        String response = null;
        try{
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
            resp = imageService.updateIcon(spId, body);
        }catch(Exception e){
            e.printStackTrace();
        }

        assertEquals("true", resp.getResponse());
    }

    /**
     * Test fail to update an icon when the user has no authorization.
     */
    @Test
    public void testUpdateIconNoAuthorization(){
        String response = null;
        try{
            String spId = "shirish@gmail.com";
            String key = "beautiful";
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
            resp = imageService.updateIcon(spId, body);
            response = new Gson().toJson(resp);
        }catch(Exception e){
            e.printStackTrace();
        }

        assertEquals("{\"response\":\"false\",\"message\":\"Lack authorization to upload an icon\"}", response);
    }
}
