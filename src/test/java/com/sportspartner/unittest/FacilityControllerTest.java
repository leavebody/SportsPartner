package com.sportspartner.unittest;

import com.google.gson.*;
import com.sportspartner.controllers.ActivityController;
import com.sportspartner.controllers.FacilityController;
import com.sportspartner.main.Bootstrap;
import com.sportspartner.service.ActivityService;
import com.google.gson.JsonObject;
import com.sportspartner.service.FacilityService;
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
public class FacilityControllerTest {
    HttpURLConnection connection = null;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        ipAddress(Bootstrap.IP_ADDRESS);
        port(PORT);
        staticFileLocation("/public");
        new FacilityController(new FacilityService());

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
     * test when GetFacilityMarkers succeeds
     */
    @Test
    public void testGetFacilityMarkersSuccess() {
        String responseBody = new String();
        String API_CONTEXT = "/api.sportspartner.com/v1";

        try {
            URL url = new URL("http", Bootstrap.IP_ADDRESS, PORT, API_CONTEXT + "/facility_markers?los=-80&lol=-70&las=30&lal=50");
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
        String response = responseJson.toString();
        assertEquals("{\"response\":\"true\",\"facilities\":[{\"facilityId\":\"001\",\"facilityName\":\"JHU gym\",\"longitude\":-76.6206,\"latitude\":39.3329},{\"facilityId\":\"002\",\"facilityName\":\"Wyman Park\",\"longitude\":-76.6249,\"latitude\":39.3326},{\"facilityId\":\"003\",\"facilityName\":\"Oriole Park\",\"longitude\":-76.6216,\"latitude\":39.2836}]}", response);
    }
}
