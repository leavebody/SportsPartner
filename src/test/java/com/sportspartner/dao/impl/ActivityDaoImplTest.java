package com.sportspartner.dao.impl;

import com.sportspartner.model.Activity;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ActivityDaoImplTest {
    @BeforeClass
    public static void setUpBeforeClass(){
    }

    @AfterClass
    public static void tearDownAfterClass(){
    }

    @Before
    public void setUp(){}

    @After
    public void teardown(){
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();


    @Test
    public void testActivityDaoImpl(){

        ActivityDaoImpl activityDaoImpl = new ActivityDaoImpl();
        ActivityMemberDaoImpl activityMemberDaoImpl = new ActivityMemberDaoImpl();
        Date startTime = new Date(System.currentTimeMillis());
        Date endTime = new Date(System.currentTimeMillis()+10);
        activityMemberDaoImpl.deleteAllActivityMembers("ac001");
        activityDaoImpl.deleteActivity("ac001");
        //(String activityId, String creatorId, String facilityId, String status, String sportId, double longitude, double latitude, String zipcode, String address, Date startTime, Date endTime, int capacity, int size, String description)
        Activity newActivity = new Activity("ac001", "u1", "001", "OPEN", "003",0.0,0.0,"21210","somewhere",startTime, endTime, 4, 3 , "Do nothing!");
        assertEquals(true,activityDaoImpl.newActivity(newActivity));
        assertEquals("OPEN",activityDaoImpl.getActivity("ac001").getStatus());
        assertEquals(true,activityDaoImpl.getAllActivities().size()>0);
        assertEquals(true,activityDaoImpl.getUpcomingActivities("u1").size()>0);
        assertEquals(true,activityDaoImpl.getPastActivities("u1").size()>0);
        assertEquals(true,activityDaoImpl.getRecommendActivities("u1").size()>0);
        newActivity.setAddress("other place");
        assertEquals(true,activityDaoImpl.updateActivity(newActivity));
        assertEquals(true, activityDaoImpl.deleteActivity("ac001"));

    }

}
