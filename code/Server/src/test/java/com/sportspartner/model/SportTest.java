package com.sportspartner.model;

import org.junit.*;

import static org.junit.Assert.assertEquals;

public class SportTest {
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
     * Test when Sport is called
     */
    @Test
    public void testSPort(){
        Sport sport = new Sport();
        sport.setSportId("001");
        sport.setSportName("tennis");
        sport.setSportIconUUID("path");

        assertEquals("001 tennis path", sport.getSportId()+" "+sport.getSportName()+" "+sport.getSportIconUUID());
    }
}
