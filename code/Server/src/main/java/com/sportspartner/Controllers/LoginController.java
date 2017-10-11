package com.sportspartner.Controllers;

import com.sportspartner.Models.User;
import com.sportspartner.Service.UserService;
import com.sportspartner.Util.JsonTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

public class LoginController {
    private static final String API_CONTEXT = "/api.sportspartner.com/v1";
    private UserService userService;
    private final Logger logger = LoggerFactory.getLogger(LoginController.class);

    public LoginController(UserService userService) {
        this.userService = userService;
        setupEndpoints();
    }

    private void setupEndpoints() {
        //login
        put(API_CONTEXT + "/login", "application/json", (request, response) -> {
            try {
                User user = userService.login(request.body());
                response.status(200);
                return user;
            } catch (Exception ex) {
                logger.error("Failed to Login");
                response.status(500);
                return false;
            }
        }, new JsonTransformer());

    }

}
