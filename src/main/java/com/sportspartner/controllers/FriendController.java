package com.sportspartner.controllers;

import com.sportspartner.service.FriendService;
import com.sportspartner.util.JsonResponse;
import com.sportspartner.util.JsonTransformer;

import static spark.Spark.delete;
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

        // get all friends of the user
        get(API_CONTEXT + "/:userId/friends", "application/json", (request, response) -> {
            JsonResponse reps = new JsonResponse(false);
            try {
                reps = friendService.getFriendList(request.params(":userId"));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            response.status(200);
            return reps;
        }, new JsonTransformer());

        // delete a friend
        delete(API_CONTEXT + "/deletefriend/:userId1/:userId2", "application/json", (request, response) -> {
            JsonResponse reps = new JsonResponse(false);
            try {
                reps = friendService.deleteFriend(request.params(":userId1"),request.params(":userId2"));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            response.status(200);
            return reps;
        }, new JsonTransformer());
    }
}

