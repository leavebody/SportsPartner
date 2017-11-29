package com.sportspartner.model;

import org.junit.*;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ActivityCommentTest {
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
     * Test when ActivityComment is called
     */
    @Test
    public void testActivityComment(){
        ActivityComment activityComment = new ActivityComment("a001", "001", "u1", new Date(1234), "good");
        assertEquals("a001", activityComment.getActivityId());
        assertEquals("001", activityComment.getCommentId());
        assertEquals("u1", activityComment.getAuthorId());
        assertEquals(new Date(1234), activityComment.getTime());
        assertEquals("good", activityComment.getContent());

    }
}
