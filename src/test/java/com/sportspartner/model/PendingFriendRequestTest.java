package com.sportspartner.model;

import org.junit.*;

import static org.junit.Assert.assertEquals;

public class PendingFriendRequestTest {
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
     * Test when PendingFriendRequest is called
     */
    @Test
    public void testPendingFriendRequest(){
        PendingFriendRequest pendingFriendRequest = new PendingFriendRequest();
        pendingFriendRequest.setSenderId("u1");
        pendingFriendRequest.setReceiverId("u2");

        assertEquals("u1u2", pendingFriendRequest.getSenderId()+pendingFriendRequest.getReceiverId());
    }
}
