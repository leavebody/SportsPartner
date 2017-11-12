package com.sportspartner.controllers;

import com.sportspartner.service.FriendService;
import com.sportspartner.util.JsonResponse;
import com.sportspartner.util.JsonTransformer;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;

public class FriendController {

    private static final String API_CONTEXT = "";
    private FriendService friendService;
    public  FriendController(FriendService friendService){
        this.friendService = friendService;
        setupEndpoints();
    }

    private void setupEndpoints(){

        // get all friends of the user
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

        // send a friend request
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

        // accept a freind request
        post(API_CONTEXT + "/acceptfriendrequest/:receiverId/:senderId", "application/json", (request, response) -> {
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

        // decline a friend request
        post(API_CONTEXT + "/declinefriendrequest/:receiverId/:senderId", "application/json", (request, response) -> {
            JsonResponse reps = new JsonResponse();
            try {
                reps = friendService.declineFriendRequest(request.params(":receiverId"),request.params(":senderId"));
                response.status(200);
                return reps;
            } catch ( FriendService.FriendServiceException ex) {
                response.status(200);
                return reps;
            }
        }, new JsonTransformer());

        // delete a friend
        delete(API_CONTEXT + "/deletefriend/:userId1/:userId2", "application/json", (request, response) -> {
            JsonResponse reps = new JsonResponse();
            try {
                reps = friendService.deleteFriend(request.params(":userId1"),request.params(":userId2"));
                response.status(200);
                return reps;
            } catch ( FriendService.FriendServiceException ex) {
                response.status(200);
                return reps;
            }
        }, new JsonTransformer());
    }
}
