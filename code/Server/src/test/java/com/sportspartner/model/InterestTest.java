package com.sportspartner.model;

import org.junit.*;

import static org.junit.Assert.assertEquals;

public class InterestTest {
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
     * Test when Interest is called
     */
    @Test
    public void testInterest(){
        Interest interest = new Interest();
        interest.setUserId("u1");

        assertEquals("u1", interest.getUserId());
    }
}
