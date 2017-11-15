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
        get(API_CONTEXT + "/facilities", "application/json", (request, response) -> {
            JsonResponse reps = new JsonResponse();
            try {
                reps = facilityService.getAllFacilityOutlineMaps();
                return reps;
            } catch (Exception ex) {
                response.status(200);
                return reps;
            }
        }, new JsonTransformer());


    }
}
