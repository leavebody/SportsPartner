package com.sportspartner.controllers;

import com.sportspartner.service.ImageService;
import com.sportspartner.util.JsonResponse;
import com.sportspartner.util.JsonTransformer;

import static spark.Spark.get;
import static spark.Spark.post;
//
//public class ImageController {
//    private static final String API_CONTEXT = "/api.sportspartner.com/v1";
//    private ImageService imageService;
//
//    public ImageController(ImageService imageService){
//        this.imageService = imageService;
//        setupEndpoints();
//    }
//
//    /**
//     * Set all endpoints for profile
//     */
//
//    private void setupEndpoints() {
//        //get an image
//        get(API_CONTEXT + "/profile/:userId", "application/json", (request, response) -> {
//            JsonResponse reps = new JsonResponse();
//            //System.out.println(request.queryParams("id") + " " + request.queryParams("type"));
//            try {
//                reps = profileService.getProfile(request.params(":userId"), request.body());
//                response.status(200);
//                return reps;
//            } catch (Exception ex) {
//                response.status(200);
//                return reps;
//            }
//        }, new JsonTransformer());
//
//}
