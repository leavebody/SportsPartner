package com.sportspartner.model;

import org.junit.*;

import static org.junit.Assert.assertEquals;

public class PendingJoinActivityRequestTest {
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

    @Test
    public void testPendingJoinActivityRequest(){
        PendingJoinActivityRequest pendingJoinActivityRequest1 = new PendingJoinActivityRequest();
        PendingJoinActivityRequest pendingJoinActivityRequest2 = new PendingJoinActivityRequest("001","u1","u2");
        pendingJoinActivityRequest1.setActivityId("002");
        pendingJoinActivityRequest1.setCreatorId("u1");
        pendingJoinActivityRequest1.setRequestorId("u2");
        assertEquals(true, pendingJoinActivityRequest1.getRequestorId()==pendingJoinActivityRequest2.getCreatorId()&&pendingJoinActivityRequest1.getActivityId()=="002");

    }
}
