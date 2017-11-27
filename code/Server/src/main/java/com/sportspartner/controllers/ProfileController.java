package com.sportspartner.controllers;

import com.sportspartner.service.ProfileService;
import com.sportspartner.util.JsonResponse;
import com.sportspartner.util.JsonTransformer;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

public class ProfileController {
    private static final String API_CONTEXT = "/api.sportspartner.com/v1";
    private ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
        setupEndpoints();
    }
    /**
     * Set all endpoints for profile
     */
    private void setupEndpoints() {
        //get the profile
        get(API_CONTEXT + "/profile/:userId", "application/json", (request, response) -> {
            JsonResponse reps = new JsonResponse(true);
            //System.out.println(request.queryParams("id") + " " + request.queryParams("type"));
            try {
                reps = profileService.getProfile(request.params(":userId"), request.queryParams("requestorId"), request.queryParams("requestorKey"));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            response.status(200);
            return reps;
        }, new JsonTransformer());

        //update the profile
        put(API_CONTEXT + "/profile/:userId", "application/json", (request, response) -> {
            JsonResponse reps = new JsonResponse(true);
            try {
                reps = profileService.updateProfile(request.params(":userId"),request.body());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            response.status(200);
            return reps;
        }, new JsonTransformer());

        // get the interests
        get(API_CONTEXT + "/interests/:userId", "application/json", (request, response) -> {
            JsonResponse reps = new JsonResponse(true);
            //System.out.println(request.queryParams("id") + " " + request.queryParams("type"));
            try {
                reps = profileService.getInterests(request.params(":userId"));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            response.status(200);
            return reps;
        }, new JsonTransformer());

        //update the interests
        put(API_CONTEXT + "/interests/:userId", "application/json", (request, response) -> {
            JsonResponse reps = new JsonResponse(true);
            try {
                reps = profileService.updateInterest(request.params(":userId"),request.body());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            response.status(200);
            return reps;
        }, new JsonTransformer());

        // get the user outline
        get(API_CONTEXT + "/profile/outline/:userId", "application/json", (request, response) -> {
            JsonResponse reps = new JsonResponse(true);
            //System.out.println(request.queryParams("id") + " " + request.queryParams("type"));
            try {
                reps = profileService.getUserOutline(request.params(":userId"));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            response.status(200);
            return reps;
        }, new JsonTransformer());

        // get the profile comments
        get(API_CONTEXT + "/profile_comments/:userId", "application/json", (request, response) ->{
            JsonResponse reps = new JsonResponse(true);
            //System.out.println(request.queryParams("id") + " " + request.queryParams("type"));
            try {
                reps = profileService.getProfileComment(request.params(":userId"));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            response.status(200);
            return reps;
        }, new JsonTransformer());

        // create a new profile comment
        post(API_CONTEXT + "/profile_comments/:userId", "application/json", (request, response) -> {
            JsonResponse reps = new JsonResponse(true);
            try {
                reps = profileService.newProfileComment(request.params(":userId"), request.body());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            response.status(500);
            return reps;
        }, new JsonTransformer());

        // get all sports
        get(API_CONTEXT + "/sports", "application/json", (request, response) ->{
            JsonResponse reps = new JsonResponse(true);
            //System.out.println(request.queryParams("id") + " " + request.queryParams("type"));
            try {
                reps = profileService.getAllSports();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            response.status(200);
            return reps;
        }, new JsonTransformer());


    }
}
