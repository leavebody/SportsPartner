package com.sportspartner.controllers;

import com.sportspartner.model.Facility;
import com.sportspartner.service.FacilityService;
import com.sportspartner.util.JsonResponse;
import com.sportspartner.util.JsonTransformer;

import static spark.Spark.*;

public class FacilityController {
    private static final String API_CONTEXT = "/api.sportspartner.com/v1";
    private FacilityService facilityService;
    public FacilityController(FacilityService facilityService){
        this.facilityService= facilityService;
        setupEndpoints();
    }

    /**
     * Set all endpoints for facility
     */
    private void setupEndpoints() {
        // get all facilities for map display
        get(API_CONTEXT + "/facility_markers", "application/json", (request, response) -> {
            JsonResponse reps = new JsonResponse(false);
            try {
                double longitude_small = Double.parseDouble(request.queryParams("los"));
                double longitude_large = Double.parseDouble(request.queryParams("lol"));
                double latitude_small = Double.parseDouble(request.queryParams("las"));
                double latitude_large = Double.parseDouble(request.queryParams("lal"));
                reps = facilityService.getFacilityMarkers(longitude_small, longitude_large, latitude_small, latitude_large);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            response.status(200);
            return reps;
        }, new JsonTransformer());

        // get facility outline
        get(API_CONTEXT + "/facility/outline/:id", "application/json", (request, response) -> {
            JsonResponse reps = new JsonResponse(false);
            try {
                String id = request.params("id");
                reps = facilityService.getFacilityOutline(id);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            response.status(200);
            return reps;
        }, new JsonTransformer());
    }
}
