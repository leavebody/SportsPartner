package com.sportspartner.controllers;

import com.sportspartner.service.UserService;
import com.sportspartner.util.JsonResponse;
import com.sportspartner.util.JsonTransformer;

import static spark.Spark.delete;

public class LogoutController {
    private static final String API_CONTEXT = "/api.sportspartner.com/v1";
    private UserService userService;

    public LogoutController(UserService userService) {
        this.userService = userService;
        setupEndpoints();
    }

    /**
     * Set all endpoints for logout
     */
    private void setupEndpoints() {

        //logout
        delete(API_CONTEXT + "/logout", "application/json", (request, response) -> {
            JsonResponse reps = new JsonResponse();
            try {
                reps = userService.logOut(request.queryParams("userId"), request.queryParams("key"),request.queryParams("registrationId"));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            response.status(200);
            return reps;
        }, new JsonTransformer());

    }

}
