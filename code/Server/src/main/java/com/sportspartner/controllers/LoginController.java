package com.sportspartner.controllers;

import com.sportspartner.util.JsonResponse;
import com.sportspartner.service.UserService;
import com.sportspartner.util.JsonTransformer;

import static spark.Spark.*;

public class LoginController {
    private static final String API_CONTEXT = "/api.sportspartner.com/v1";
    private UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
        setupEndpoints();
    }

    /**
     * Set all endpoints for login
     */
    private void setupEndpoints() {

        //login
        post(API_CONTEXT + "/login", "application/json", (request, response) -> {
            JsonResponse reps = new JsonResponse(false);
            try {
                reps = userService.login(request.body());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            response.status(200);
            return reps;
        }, new JsonTransformer());

    }

}
