package com.sportspartner.controllers;
import static spark.Spark.*;

import com.sportspartner.model.Notification;
import com.sportspartner.service.*;
import com.sportspartner.util.JsonResponse;
import com.sportspartner.util.JsonTransformer;

public class NotificationController {
    private static final String API_CONTEXT = "/api.sportspartner.com/v1";

    private NotificationService notificationService;
    public NotificationController(NotificationService notificationService){
        this.notificationService = notificationService;
        setupEndpoints();
    }

    // send a friend request
    private void setupEndpoints() {
        post(API_CONTEXT + "/friendrequest/:receiverId/:senderId", "application/json", (request, response) -> {
            JsonResponse reps = new JsonResponse(false);
            try {
                reps = notificationService.sendFriendRequest(request.params(":receiverId"), request.params(":senderId"));
            } catch(Exception ex) {
                ex.printStackTrace();
            }
            response.status(200);
            return reps;
        }, new JsonTransformer());

        // accept a freind request
        post(API_CONTEXT + "/acceptfriendrequest/:receiverId/:senderId", "application/json", (request, response) -> {
            JsonResponse reps = new JsonResponse(false);
            try {
                reps = notificationService.acceptFriendRequest(request.params(":receiverId"), request.params(":senderId"));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            response.status(200);
            return reps;
        }, new JsonTransformer());

        // decline a friend request
        post(API_CONTEXT + "/declinefriendrequest/:receiverId/:senderId", "application/json", (request, response) -> {
            JsonResponse reps = new JsonResponse(false);
            try {
                reps = notificationService.declineFriendRequest(request.params(":receiverId"), request.params(":senderId"));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            response.status(200);
            return reps;
        }, new JsonTransformer());
/*
        // send a join activity application
        post(API_CONTEXT + "/joinactivityapplication/:activityId", "application/json", (request, response) -> {
            JsonResponse reps = new JsonResponse();
            try {
                reps = notificationService.sendJoinActivityRequest(request.params("activityId"), request.body());
                response.status(200);
                return reps;
            } catch (Exception ex) {
                response.status(200);
                return reps;
            }

        }, new JsonTransformer());

        // accept a join activity application
        post(API_CONTEXT + "/acceptjoinactivityapplication/:id", "application/json", (request, response) -> {
            JsonResponse reps = new JsonResponse();
            try {
                reps = notificationService.acceptJoinActivityRequest(request.params("id"), request.body());
                response.status(200);
                return reps;
            } catch (Exception ex) {
                response.status(200);
                return reps;
            }

        }, new JsonTransformer());

        // decline a join activity application
        post(API_CONTEXT + "/declinejoinactivityapplication/:id", "application/json", (request, response) -> {
            JsonResponse reps = new JsonResponse();
            try {
                reps = notificationService.declineJoinActivityRequest(request.params("id"), request.body());
                response.status(200);
                return reps;
            } catch (Exception ex) {
                response.status(200);
                return reps;
            }

        }, new JsonTransformer());
    */}

}
