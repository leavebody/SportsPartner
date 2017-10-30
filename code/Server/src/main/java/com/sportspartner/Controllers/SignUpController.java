package com.sportspartner.controllers;

import com.sportspartner.service.UserService;
import com.sportspartner.util.JsonResponse;
import com.sportspartner.util.JsonTransformer;

import static spark.Spark.post;

public class SignUpController {
    private static final String API_CONTEXT = "/api.sportspartner.com/v1";
    private UserService userService;

    public SignUpController(UserService userService) {
        this.userService = userService;
        setupEndpoints();
    }

    private void setupEndpoints() {
        //sign up as person
        post(API_CONTEXT + "/signup/:type", "application/json", (request, response) -> {
            JsonResponse reps = new JsonResponse();
            try {
                if (request.params("type").equals("person")) {
                    reps = userService.signup(request.body(), "PERSON");
                } else if (request.params("type").equals("facilityprovider")) {
                    reps = userService.signup(request.body(), "PROVIDER");
                }
                else{
                    reps.setResponse("false");
                    reps.setMessage("User type not exist");
                    return reps;
                }
                response.status(200);
                return reps;
            } catch (Exception ex) {
                response.status(500);
                return reps;
            }
        }, new JsonTransformer());

    }
}