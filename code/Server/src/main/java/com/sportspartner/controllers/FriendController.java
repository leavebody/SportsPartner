package com.sportspartner.controllers;

import com.sportspartner.service.FriendService;
import com.sportspartner.util.JsonResponse;
import com.sportspartner.util.JsonTransformer;

import static spark.Spark.get;
import static spark.Spark.post;

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
        post(API_CONTEXT + "/friendrequest/:receiverId/:senderId", "application/json", (request, response) -> {
            JsonResponse reps = new JsonResponse();
            try {
                reps = friendService.sendFriendRequest(request.params(":receiverId"),request.params(":senderId"));
                response.status(200);
                return reps;
            } catch ( FriendService.FriendServiceException ex) {
                response.status(200);
                return reps;
            }
        }, new JsonTransformer());
        post(API_CONTEXT + "/acceptrequest/:receiverId/:senderId", "application/json", (request, response) -> {
            JsonResponse reps = new JsonResponse();
            try {
                reps = friendService.acceptFriendRequest(request.params(":receiverId"),request.params(":senderId"));
                response.status(200);
                return reps;
            } catch ( FriendService.FriendServiceException ex) {
                response.status(200);
                return reps;
            }
        }, new JsonTransformer());
    }
}
