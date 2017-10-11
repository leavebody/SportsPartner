package com.sportspartner.Controllers;

import com.sportspartner.Models.Person;
import com.sportspartner.Models.User;
import com.sportspartner.Service.UserService;
import com.sportspartner.Util.JsonTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.post;
import static spark.Spark.put;

public class SignUpController {
    private static final String API_CONTEXT = "/api.sportspartner.com/v1";
    private UserService userService;
    private final Logger logger = LoggerFactory.getLogger(LoginController.class);

    public SignUpController(UserService userService) {
        this.userService = userService;
        setupEndpoints();
    }

    private void setupEndpoints() {
        //signUp as person
        post(API_CONTEXT + "/signup/type=person", "application/json", (request, response) -> {
            try {
                userService.signUpPerson(request.body());
                response.status(200);
                return true;
            } catch (Exception ex) {
                logger.error("Failed to SignUp");
                response.status(500);
                return false;
            }
        }, new JsonTransformer());

        //signUp as ActivityProvider
        post(API_CONTEXT + "/signup/type=activityprovider", "application/json", (request, response) -> {
            try {
                userService.signUpActivityProvider(request.body());
                response.status(200);
                return true;
            } catch (Exception ex) {
                logger.error("Failed to SignUp");
                response.status(500);
                return false;
            }
        }, new JsonTransformer());

    }
}
