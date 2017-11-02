package com.sportspartner.controllers;

import com.sportspartner.service.FriendService;
import com.sportspartner.util.JsonResponse;
import com.sportspartner.util.JsonTransformer;

import static spark.Spark.get;

public class FriendController {

    private static final String API_CONTEXT = "/api.sportspartner.com/v1";
    private FriendService friendService;
    public  FriendController(FriendService friendService){
        this.friendService = friendService;
        setupEndpoints();
    }

    private void setupEndpoints(){

        get(API_CONTEXT + "/:userId/friends", "application/json", (request, response) -> {
            JsonResponse reps = new JsonResponse();
            try {
                reps = friendService.getFriendList(request.params(":userId"));
                response.status(200);
                return reps;
            } catch ( FriendService.FriendServiceException ex) {
                response.status(200);
                return reps;
            }
        }, new JsonTransformer());
    }
}
