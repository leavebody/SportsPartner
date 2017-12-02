package com.sportspartner.controllers;

import com.sportspartner.service.ActivityService;
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
            JsonResponse reps = new JsonResponse(true);
            try {
                if(request.queryParams("content").equals("full")){
                    reps = activityService.getActivityDetail(request.params(":id"), request.queryParams("requestorId"),request.queryParams("requestorKey"));
                }else if(request.queryParams("content").equals("outline")){
                    reps = activityService.getActivityOutline(request.params(":id"));
                }else{
                    reps.setResponse("false");
                    reps.setMessage("No such content");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            response.status(200);
            return reps;
        }, new JsonTransformer());

        // get upcoming activity outlines
        get(API_CONTEXT + "/activity_upcoming", "application/json", (request, response) -> {
            JsonResponse reps = new JsonResponse(true);
            try {
                reps = activityService.getUpcomingActivity(request.queryParams("id"), Integer.parseInt(request.queryParams("offset")), Integer.parseInt(request.queryParams("limit")));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            response.status(200);
            return reps;
        }, new JsonTransformer());

        // get past activity outlines
        get(API_CONTEXT + "/activity_past", "application/json", (request, response) -> {
            JsonResponse reps = new JsonResponse(true);
            try {
                reps = activityService.getPastActivity(request.queryParams("id"), Integer.parseInt(request.queryParams("offset")), Integer.parseInt(request.queryParams("limit")));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            response.status(200);
            return reps;
        }, new JsonTransformer());

        // get activity members
        get(API_CONTEXT + "/activity_members/:id", "application/json", (request, response) -> {
            JsonResponse reps = new JsonResponse(true);
            try {
                reps = activityService.getActivityMembers(request.params("id"));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            response.status(200);
            return reps;
        }, new JsonTransformer());

        // add an activity member
        post(API_CONTEXT + "/activity_members/:id", "application/json", (request, response) -> {
            JsonResponse reps = new JsonResponse(true);
            try {
                reps = activityService.addActivityMember(request.params("id"),request.body());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            response.status(200);
            return reps;
        }, new JsonTransformer());

        // remove an activity member
        delete(API_CONTEXT + "/activity_members/:id", "application/json", (request, response) -> {
            JsonResponse reps = new JsonResponse(true);
            try {
                reps = activityService.removeActivityMember(request.params("id"),request.body());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            response.status(200);
            return reps;
        }, new JsonTransformer());

        // create an activity
        post(API_CONTEXT + "/activity", "application/json", (request, response) -> {
            JsonResponse reps = new JsonResponse(true);
            try {
                reps = activityService.newActivity(request.body());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            response.status(200);
            return reps;
        }, new JsonTransformer());

        //update an activity info
        put(API_CONTEXT + "/activity/:activityId", "application/json", (request, response) -> {
            JsonResponse reps = new JsonResponse(true);
            try {
                reps = activityService.updateActivity(request.params(":activityId"),request.body());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            response.status(200);
            return reps;
        }, new JsonTransformer());

        // Delete(cancel) an activity
        delete(API_CONTEXT + "/activity/:activityId/:userId/:key", "application/json", (request, response) -> {
            JsonResponse reps = new JsonResponse(true);
            try{
                reps = activityService.deleteActivity(request.params("activityId"), request.params("userId"), request.params("key"));
            } catch(Exception ex){
                ex.printStackTrace();
            }
            response.status(200);
            return reps;
        }, new JsonTransformer());

        //Todo
        //GET https://api.sportspartner.com/v1/search?type=activity
        get(API_CONTEXT + "search", "application/json", (request, response) -> {
            JsonResponse reps = new JsonResponse(true);
            try {
                reps = activityService.searchActivity(request.queryParams("type"), Integer.parseInt(request.queryParams("limit")), Integer.parseInt(request.queryParams("offset")), request.body());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            response.status(200);
            return reps;
        }, new JsonTransformer());

    }

}
