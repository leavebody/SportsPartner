package com.sportspartner.dao.impl;

import com.sportspartner.model.PendingFriendRequest;
import org.junit.*;
import static org.junit.Assert.assertEquals;

public class PendingRequestDaoImplTest {
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
    @Test
    public void testPendingFriendRequestDaoImpl(){
        PendingFriendRequestDaoImpl pendingFriendRequestDaoImpl = new PendingFriendRequestDaoImpl();
        PendingFriendRequest pendingFriendRequest = new PendingFriendRequest("u4","u2");
        assertEquals(true,pendingFriendRequestDaoImpl.newPendingRequest(pendingFriendRequest));
        assertEquals(true,pendingFriendRequestDaoImpl.hasPendingRequest(pendingFriendRequest));
        assertEquals(true,pendingFriendRequestDaoImpl.getAllPendingRequests("u4").size()>0);
        assertEquals(true,pendingFriendRequestDaoImpl.deletePendingRequest(pendingFriendRequest));
    }

}
