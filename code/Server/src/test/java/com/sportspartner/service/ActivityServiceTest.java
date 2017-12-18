package com.sportspartner.service;


import com.google.gson.Gson;
import com.sportspartner.dao.impl.ActivityMemberDaoImpl;
import com.sportspartner.model.ActivityMember;
import com.sportspartner.util.JsonResponse;
import org.junit.*;
import spark.Spark;
import static org.junit.Assert.assertEquals;

public class ActivityServiceTest {
    private ActivityMemberDaoImpl activityMemberDaoImpl = new ActivityMemberDaoImpl();
    private ActivityService activityService = new ActivityService();
    private static ActivityMemberDaoImpl activityMemberDao = new ActivityMemberDaoImpl();
    private JsonResponse resp = new JsonResponse();

    @BeforeClass
    public static void setUpBeforeClass() throws Exception{
        ActivityMember addMember = new ActivityMember("aTest", "test2");
        activityMemberDao.newActivityMember(addMember);
    }

    @AfterClass
    public static void tearDownAfterClass()throws Exception{
        Spark.stop();
        Thread.sleep(2000);
    }

    @Before
    public void setUp(){}

    @After
    public void teardown(){
        try {
            String activityId = "a007";
            String userId = "u24";
            ActivityMember activityMember = new ActivityMember(activityId, userId);
            if(activityMemberDaoImpl.hasActivityMember(activityMember)) {
                String body = new Gson().toJson(activityMember);
                activityService.removeActivityMember(activityId, body);
            }
            String activityId1 = "a007";
            String userId1 = "u2";
            ActivityMember activityMember1 = new ActivityMember(activityId1, userId1);
            if(!activityMemberDaoImpl.hasActivityMember(activityMember)) {
                String body = new Gson().toJson(activityMember1);
                activityService.addActivityMember(activityId, body);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    /**
     * Test when getActivityDetail when the requestor is the creator of the activity.
     */
    @Test
    public void testGetActivityDetailAsCreator(){
        String response = null;
        try{
            String activityId = "aTest";
            String requestorId = "test";
            String requestorKey = "testKey";
            resp = activityService.getActivityDetail(activityId, requestorId, requestorKey);
            response = new Gson().toJson(resp);
        }catch(Exception e){
            e.printStackTrace();
        }
        assertEquals(true, !response.isEmpty());
    }



    /**
     * Test getActivityDetail when the requestor is a member of the activity(not creator)
     */
    @Test
    public void testGetActivityDetailAsMember(){
        String response = null;
        try{
            String activityId = "aTest";
            String requestorId = "test1";
            String requestorKey = "testKey1";
            resp = activityService.getActivityDetail(activityId, requestorId, requestorKey);
        }catch(Exception e){
            e.printStackTrace();
        }
        response = new Gson().toJson(resp);
        assertEquals(true, !response.isEmpty());
    }

    /**
     * Test when getActivityDetail when the requestorKey is not correct,
     * although the requestor is a creator or a member of the activity,
     * he will still be recognized as a stranger
     */
    @Test
    public void testGetActivityDetailWrongKey(){
        String response = null;
        try{
            String activityId = "a001";
            String requestorId = "u1";
            String requestorKey = "wrongkey";
            resp = activityService.getActivityDetail(activityId, requestorId, requestorKey);
        }catch(Exception e){
            e.printStackTrace();
        }
        response = new Gson().toJson(resp);
        assertEquals(true, !response.isEmpty());
    }

    /**
     * Test getActivityDetail when the requestor is a stranger.
     */
    @Test
    public void testGetActivityDetailAsStranger(){
        String response = null;
        try{
            String activityId = "aTest";
            String requestorId = "test2";
            String requestorKey = "testKey2";
            resp = activityService.getActivityDetail(activityId, requestorId, requestorKey);
        }catch(Exception e){
            e.printStackTrace();
        }
        response = new Gson().toJson(resp);
        assertEquals(true, !response.isEmpty());
    }

    /**
     * Test getActivityDetail fails when there's no activity of given Id
     */
    @Test
    public void testGetActivityDetailNoId(){
        String response = null;
        try{
            String activityId = "fake";
            String requestorId = "u1";
            String requestorKey = "nonono";
            resp = activityService.getActivityDetail(activityId, requestorId, requestorKey);
        }catch(Exception e){
            e.printStackTrace();
        }
        response = new Gson().toJson(resp);
        assertEquals("{\"response\":\"false\",\"message\":\"No such activity\"}", response);
    }

    /**
     * Test getActivityOutline succeed.
     */
    @Test
    public void testGetActivityOutline(){
        String response = null;
        try{
            String activityId = "aTest";
            resp = activityService.getActivityOutline(activityId);
            response = new Gson().toJson(resp);
        }catch(Exception e){
            e.printStackTrace();
        }
        assertEquals("{\"response\":\"true\",\"activityOutline\":{\"activityId\":\"aTest\",\"creatorId\":\"test\",\"status\":\"OPEN\",\"sportIconUUID\":\"27858272-c73a-11e7-abc4-cec278b6b50a\",\"sportName\":\"Swimming\",\"startTime\":\"Nov 18, 2018 2:11:00 AM\",\"endTime\":\"Nov 18, 2018 3:11:00 AM\",\"facilityId\":\"NULL\",\"longitude\":151.211302131414,\"latitude\":-33.8507705324009,\"address\":\"JHU\",\"capacity\":4,\"size\":2}}",response);
    }

    /**
     * Test getActivityOutline when there's no such activityId.
     */
    @Test
    public void testGetActivityOutlineNoId(){
        String response = null;
        try{
            String activityId = "fake";
            resp = activityService.getActivityOutline(activityId);
            response = new Gson().toJson(resp);
        }catch(Exception e){
            e.printStackTrace();
        }
        assertEquals("{\"response\":\"false\",\"message\":\"No such activity\"}",response);
    }

    /**
     * Test getUpcomingActivity succeed.
     */
    @Test
    public void testGetUpcomingActivity(){
        String response = null;
        try{
            String userId = "test";
            int offset = 0;
            int limit = 3;
            resp = activityService.getUpcomingActivity(userId, offset, limit);
            response = new Gson().toJson(resp);
        }catch(Exception e){
            e.printStackTrace();
        }
        assertEquals(true, !response.isEmpty());
    }

    /**
     * Test getUpcomingActivity when there's no more upcoming activities for a user.
     */
    @Test
    public void testGetUpcomingActivityNoMore(){
        String response = null;
        try{
            String userId = "test3";
            int offset = 0;
            int limit = 3;
            resp = activityService.getUpcomingActivity(userId, offset, limit);
            response = new Gson().toJson(resp);
        }catch(Exception e){
            e.printStackTrace();
        }
        assertEquals("{\"response\":\"true\",\"message\":\"No more upcoming activities\",\"activityOutlines\":[]}", response);
    }


    /**
     * Test getUpcomingActivity when the userId does not exist.
     */
    @Test
    public void testGetUpcomingActivityNoId(){
        String response = null;
        try{
            String userId = "nouser";
            int offset = 0;
            int limit = 3;
            resp = activityService.getUpcomingActivity(userId, offset, limit);
            response = new Gson().toJson(resp);
        }catch(Exception e){
            e.printStackTrace();
         }
         assertEquals("{\"response\":\"false\",\"message\":\"No such user\"}", response);
    }

    /**
     * Test getPastActivity succeed.
     */
    @Test
    public void testGetPastActivity(){
        String response = null;
        try{
            String userId = "test";
            int offset = 0;
            int limit = 3;
            resp = activityService.getPastActivity(userId, offset, limit);
            response = new Gson().toJson(resp);
        }catch(Exception e){
            e.printStackTrace();
        }
        assertEquals("{\"response\":\"true\",\"activityOutlines\":[{\"activityId\":\"aTestPast\",\"creatorId\":\"test\",\"status\":\"OPEN\",\"sportIconUUID\":\"27858272-c73a-11e7-abc4-cec278b6b50a\",\"sportName\":\"Swimming\",\"startTime\":\"Nov 18, 2017 2:11:00 AM\",\"endTime\":\"Nov 18, 2017 3:11:00 AM\",\"facilityId\":\"001\",\"longitude\":-79.55,\"latitude\":36.0,\"address\":\"JHU\",\"capacity\":4,\"size\":2}]}", response);
    }

    /**
     * Test getPastActivity when there's no more past activities for a user.
     */
    @Test
    public void testGetPastActivityNoMore(){
        String response = null;
        try{
            String userId = "test3";
            int offset = 0;
            int limit = 3;
            resp = activityService.getPastActivity(userId, offset, limit);
            response = new Gson().toJson(resp);
        }catch(Exception e){
            e.printStackTrace();
        }
        assertEquals("{\"response\":\"true\",\"message\":\"No more past activities\",\"activityOutlines\":[]}", response);
    }


    /**
     * Test getPastActivity when the userId does not exist.
     */
    @Test
    public void testGetPastActivityNoId(){
        String response = null;
        try{
            String userId = "nouser";
            int offset = 0;
            int limit = 3;
            resp = activityService.getPastActivity(userId, offset, limit);
            response = new Gson().toJson(resp);
        }catch(Exception e){
            e.printStackTrace();
        }
        assertEquals("{\"response\":\"false\",\"message\":\"No such user\"}", response);
    }

    /**
     * Test getActivityMembers succeed.
     */
    @Test
    public void testGetActivityMembers(){
        String response = null;
        try{
            String activityId = "aTest";
            resp = activityService.getActivityMembers(activityId);
            response = new Gson().toJson(resp);
        }catch(Exception e){
            e.printStackTrace();
        }
        assertEquals(true, !response.isEmpty());
    }

    /**
     * Test addActivityMember succeed.
     */
    @Test
    public void testAddActivityMember(){
        String response = null;
        try{
            String activityId = "a007";
            String userId = "u24";
            String body = new Gson().toJson(new ActivityMember(activityId, userId));
            resp = activityService.addActivityMember(activityId, body);
            response = new Gson().toJson(resp);
        }catch(Exception e){
            e.printStackTrace();
        }
        assertEquals("{\"response\":\"true\"}", response);
    }

    /**
     * Test removeActivityMember succeed.
     */
    @Test
    public void testRemoveActivityMember(){
        String response = null;
        try{
            String activityId = "a007";
            String userId = "u2";
            String body = new Gson().toJson(new ActivityMember(activityId, userId));
            resp = activityService.removeActivityMember(activityId, body);
            response = new Gson().toJson(resp);
        }catch(Exception e){
            e.printStackTrace();
        }
        assertEquals("{\"response\":\"true\"}", response);
    }

    /**
     * Test LeaveActivity succeed.
     */
    @Test
    public void testLeaveActivity(){
        String response = null;
        try{
            resp = activityService.leaveActivity("aTest", "test2", "testKey2");
            response = new Gson().toJson(resp);
        }catch(Exception e){
            e.printStackTrace();
        }
        assertEquals("{\"response\":\"true\"}", response);
    }


}
