package com.sportspartner.model;

import org.junit.*;

import static org.junit.Assert.assertEquals;

public class AuthorizationTest {
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
     * Test when Authorization is called
     */
    @Test
    public void testAuthorization(){
        Authorization authorization = new Authorization();
        authorization.setUserId("u1");

        assertEquals("u1", authorization.getUserId());
    }
}
