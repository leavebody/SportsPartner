package com.sportspartner.controllers;

import com.sportspartner.service.UserService;
import com.sportspartner.util.JsonResponse;
import com.sportspartner.util.JsonTransformer;

import static spark.Spark.delete;

public class LogoutController {
    private static final String API_CONTEXT = "";
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
                response.status(200);
                return reps;
            } catch (UserService.UserServiceException ex) {
                response.status(200);
                return reps;
            }
        }, new JsonTransformer());

    }

}
