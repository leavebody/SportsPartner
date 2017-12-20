package com.sportspartner.dao.impl;

import com.sportspartner.dao.PendingJoinActivityRequestDao;
import com.sportspartner.model.PendingJoinActivityRequest;
import org.junit.*;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class PendingJoinActivityRequestDaoImplTest {
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
    public void testPendingJoinActivityRequestDaoImpl() throws SQLException {
        PendingJoinActivityRequestDao dao = new PendingJoinActivityRequestDaoImpl();
        dao.getAllPendingRequests("u1");
        dao.hasPendingRequest(new PendingJoinActivityRequest());
        PendingJoinActivityRequest re = new PendingJoinActivityRequest("a001","u1", "u2");
        dao.newPendingRequest(re);
        assertEquals(true, dao.deletePendingRequest(re));

    }
}
