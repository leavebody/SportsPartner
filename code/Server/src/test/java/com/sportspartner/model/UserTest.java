package com.sportspartner.model;

import org.junit.*;

import static org.junit.Assert.assertEquals;

public class UserTest {
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
     * Test when User is called
     */
    @Test
    public void testUser(){
        User user = new User();
        user.setUserId("u1");
        user.setPassword("p1");
        user.setType("PERSON");

        assertEquals(true, user.hasUser("u1","p1"));
    }
}
