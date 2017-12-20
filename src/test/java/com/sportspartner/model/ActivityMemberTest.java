package com.sportspartner.model;

import org.junit.*;

import static org.junit.Assert.assertEquals;

public class ActivityMemberTest {
    @BeforeClass
    public static void setUpBeforeClass() throws Exception{
    }

    @AfterClass
    public static void tearDownAfterClass()throws Exception{
    }

    @Before
    public void setUp(){}

    @After
    public void teardown(){
    }

    /**
     * Test when ActivityMember is called
     */
    @Test
    public void testActivityMember(){
        ActivityMember activityMember = new ActivityMember();
        activityMember.setUserId("u1");
        activityMember.setActivityId("a001");

        assertEquals("u1 a001ActivityMember{activityId='a001', userId='u1'}", activityMember.getUserId()+" " + activityMember.getActivityId()+activityMember.toString());
    }
}
