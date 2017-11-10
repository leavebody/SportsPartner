package com.sportspartner.controllers;

import com.sportspartner.service.ActivityService;
import com.sportspartner.service.ProfileService;
import com.sportspartner.util.JsonResponse;
import com.sportspartner.util.JsonTransformer;

import static spark.Spark.*;

public class ActivityController {

    private static final String API_CONTEXT = "/api.sportspartner.com/v1";
    private ActivityService activityService;
    public ActivityController(ActivityService activityService){
        this.activityService= activityService;
        setupEndpoints();
    }

    /**
     * Set all endpoints for activity
     */
    private void setupEndpoints() {
        // get activity details
        get(API_CONTEXT + "/activity/:id", "application/json", (request, response) -> {
            JsonResponse reps = new JsonResponse();
            try {
                if(request.queryParams("content").equals("full")){
                    reps = activityService.getActivityDetail(request.params(":id"), request.queryParams("requestorId"),request.queryParams("requestorKey"));
                }else if(request.queryParams("content").equals("outline")){
                    reps = activityService.getActivityOutline(request.params(":id"));
                }else{
                    reps.setResponse("false");
                    reps.setMessage("No such content");
                }
                response.status(200);
                return reps;
            } catch (Exception ex) {
                response.status(200);
                return reps;
            }

        }, new JsonTransformer());

        // get upcoming activity outlines
        get(API_CONTEXT + "/activity_upcoming", "application/json", (request, response) -> {
            JsonResponse reps = new JsonResponse();
            try {
                reps = activityService.getUpcomingActivity(request.queryParams("id"), Integer.parseInt(request.queryParams("offset")), Integer.parseInt(request.queryParams("limit")));
                response.status(200);
                return reps;
            } catch (Exception ex) {
                response.status(200);
                return reps;
            }

        }, new JsonTransformer());

        // get past activity outlines
        get(API_CONTEXT + "/activity_past", "application/json", (request, response) -> {
            JsonResponse reps = new JsonResponse();
            try {
                reps = activityService.getPastActivity(request.queryParams("id"), Integer.parseInt(request.queryParams("offset")), Integer.parseInt(request.queryParams("limit")));
                    response.status(200);
                return reps;
            } catch (Exception ex) {
                response.status(200);
                return reps;
            }

        }, new JsonTransformer());

        // get activity members
        get(API_CONTEXT + "/activity_members/:id", "application/json", (request, response) -> {
            JsonResponse reps = new JsonResponse();
            try {
                reps = activityService.getActivityMembers(request.params("id"));
                response.status(200);
                return reps;
            } catch (Exception ex) {
                response.status(200);
                return reps;
            }

        }, new JsonTransformer());

        // add an activity member
        post(API_CONTEXT + "/activity_members/:id", "application/json", (request, response) -> {
            JsonResponse reps = new JsonResponse();
            try {
                reps = activityService.addActivityMember(request.params("id"),request.body());
                response.status(200);
                return reps;
            } catch (Exception ex) {
                response.status(200);
                return reps;
            }

        }, new JsonTransformer());

        // remove an activity member
        delete(API_CONTEXT + "/activity_members/:id", "application/json", (request, response) -> {
            JsonResponse reps = new JsonResponse();
            try {
                reps = activityService.removeActivityMember(request.params("id"),request.body());
                response.status(200);
                return reps;
            } catch (Exception ex) {
                response.status(200);
                return reps;
            }

        }, new JsonTransformer());

        // create an activity
        post(API_CONTEXT + "/activity", "application/json", (request, response) -> {
            JsonResponse reps = new JsonResponse();
            try {
                reps = activityService.newActivity(request.body());
                response.status(200);
                return reps;
            } catch (Exception ex) {
                response.status(200);
                return reps;
            }

        }, new JsonTransformer());

    }

}
