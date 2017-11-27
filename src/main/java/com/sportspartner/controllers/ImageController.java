package com.sportspartner.controllers;

import com.sportspartner.service.ImageService;
import com.sportspartner.util.JsonResponse;
import com.sportspartner.util.JsonTransformer;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

public class ImageController {
    private static final String API_CONTEXT = "/api.sportspartner.com/v1";
    private ImageService imageService;

    public ImageController(ImageService imageService){
        this.imageService = imageService;
        setupEndpoints();
    }

    /**
     * Set all endpoints for profile
     */

    private void setupEndpoints() {
        //get an image
        get(API_CONTEXT + "/resource/:iconUUID", "application/json", (request, response) -> {
            JsonResponse reps = new JsonResponse(true);
            try {
                reps = imageService.getImage(request.params(":iconUUID"), request.queryParams("type"));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            response.status(200);
            return reps;
        }, new JsonTransformer());

        //upload an image
        post(API_CONTEXT + "/resource/icon/:spId", "application/json", (request, response)->{
            JsonResponse reps = new JsonResponse(true);
            try{
                reps = imageService.updateIcon(request.params("spId"), request.body());
            }catch(Exception ex) {
                ex.printStackTrace();
            }
            response.status(200);
            return reps;
        }, new JsonTransformer());
    }

}
