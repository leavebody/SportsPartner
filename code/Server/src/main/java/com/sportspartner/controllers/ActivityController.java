package com.sportspartner.controllers;

import com.sportspartner.service.ActivityService;
import com.sportspartner.util.JsonResponse;
import com.sportspartner.util.JsonTransformer;

import static spark.Spark.*;

public class ActivityController {

    private static final String API_CONTEXT = "/api.sportspartner.com/v1";
    private ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
        setupEndpoints();
    }

    /**
     * Set all endpoints for activity
     */
    private void setupEndpoints() {
        // get activity details
        get(API_CONTEXT + "/activity/:id", "application/json", (request, response) -> {
            JsonResponse reps = new JsonResponse(false);
            try {
                if (request.queryParams("content").equals("full")) {
                    reps = activityService.getActivityDetail(request.params(":id"), request.queryParams("requestorId"), request.queryParams("requestorKey"));
                } else if (request.queryParams("content").equals("outline")) {
                    reps = activityService.getActivityOutline(request.params(":id"));
                } else {
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
            JsonResponse reps = new JsonResponse(false);
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
            JsonResponse reps = new JsonResponse(false);
            try {
                reps = activityService.getPastActivity(request.queryParams("id"), Integer.parseInt(request.queryParams("offset")), Integer.parseInt(request.queryParams("limit")));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            response.status(200);
            return reps;
        }, new JsonTransformer());

        // get recommend activity outlines
        get(API_CONTEXT + "/activity_recommend", "application/json", (request, response) -> {
            // test GET /activity_recommend?id=u2&latitude=38.567&langitude=76.312&limit=10&offset=0
            JsonResponse reps = new JsonResponse(false);
            try {
                reps = activityService.getRecommendActivity(request.queryParams("id"),
                        Integer.parseInt(request.queryParams("offset")),
                        Integer.parseInt(request.queryParams("limit")),
                        Double.parseDouble(request.queryParams("latitude")),
                        Double.parseDouble(request.queryParams("longitude")));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            response.status(200);
            return reps;
        }, new JsonTransformer());

        // get activity members
        get(API_CONTEXT + "/activity_members/:id", "application/json", (request, response) -> {
            JsonResponse reps = new JsonResponse(false);
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
            JsonResponse reps = new JsonResponse(false);
            try {
                reps = activityService.addActivityMember(request.params("id"), request.body());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            response.status(200);
            return reps;
        }, new JsonTransformer());

        // remove an activity member
        delete(API_CONTEXT + "/activity_members/:id", "application/json", (request, response) -> {
            JsonResponse reps = new JsonResponse(false);
            try {
                reps = activityService.removeActivityMember(request.params("id"), request.body());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            response.status(200);
            return reps;
        }, new JsonTransformer());

        // create an activity
        post(API_CONTEXT + "/activity", "application/json", (request, response) -> {
            JsonResponse reps = new JsonResponse(false);
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
            JsonResponse reps = new JsonResponse(false);
            try {
                reps = activityService.updateActivity(request.params(":activityId"), request.body());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            response.status(200);
            return reps;
        }, new JsonTransformer());

        // Delete(cancel) an activity
        delete(API_CONTEXT + "/activity/:activityId/:userId/:key", "application/json", (request, response) -> {
            JsonResponse reps = new JsonResponse(false);
            try {
                reps = activityService.deleteActivity(request.params("activityId"), request.params("userId"), request.params("key"));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            response.status(200);
            return reps;
        }, new JsonTransformer());

        // When an activity has finished, the participants of the activity can review the activity by rating teammates and facilities.
        // {"userId":"test","key":"testKey","facilityreview":{"activityid":"aTestPast", "reviewer":"test", "reviewee":"001", "score":5, "comments":"test facility comment 057"}, "userreviews":[{"activityid":"aTestPast", "reviewer":"u24", "reviewee":"test1", "punctuality":5,"participation":5, "comments":"test comment 057"}]}
        post(API_CONTEXT + "/activityreview/:id", "application/json", (request, response) -> {
            JsonResponse reps = new JsonResponse(false);
            try {
                reps = activityService.reviewActivity(request.params("id"), request.body());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            response.status(200);
            return reps;
        }, new JsonTransformer());

        //search an activity
        //GET https://api.sportspartner.com/v1/search?type=activity
        post(API_CONTEXT + "/search", "application/json", (request, response) -> {
            JsonResponse reps = new JsonResponse(false);
            try {
                if (request.queryParams("type").equals("activity")) {
                    reps = activityService.searchActivity(Integer.parseInt(request.queryParams("limit")), Integer.parseInt(request.queryParams("offset")), request.body());
                } else {
                    reps.setResponse("false");
                    reps.setMessage("No such content");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                reps.setResponse("false");
                reps.setMessage(ex + " " + ex.getMessage());
            } finally {
                response.status(200);
                return reps;
            }
        }, new JsonTransformer());

        //DELETE https://api.sportspartner.com/v1/activity_leave/:activityId?userId=userId&key=key
        delete(API_CONTEXT + "/activity_leave/:activityId", "application/json", (request, response) -> {
            JsonResponse reps = new JsonResponse(false);
            try {
                reps = activityService.leaveActivity(request.params(":activityId"), request.queryParams("userId"), request.queryParams("key"));

            } catch (Exception ex) {
                ex.printStackTrace();
                reps.setResponse("false");
                reps.setMessage(ex + " " + ex.getMessage());
            } finally {
                response.status(200);
                return reps;
            }
        }, new JsonTransformer());
    }
}
