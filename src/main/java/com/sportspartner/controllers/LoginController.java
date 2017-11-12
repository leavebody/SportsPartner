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
            JsonResponse reps = new JsonResponse();
            try {
                reps = userService.login(request.body());
                response.status(200);
                return reps;
            } catch (UserService.UserServiceException ex) {
                response.status(200);
                return reps;
            }
        }, new JsonTransformer());

        //for test connection
        get("/hello", "application/json", (request, response) -> {
            JsonResponse reps = new JsonResponse();
            reps.setMessage("hello");
            response.status(200);
            return reps;
        }, new JsonTransformer());

    }

}
