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
        Date date = new Date(1234);
        ActivityComment activityComment = new ActivityComment("a001", "001", "u1", new Date(1234), "good");
        String result = activityComment.getActivityId() + "," + activityComment.getCommentId() + "," + activityComment.getAuthorId()
                +","+activityComment.getTime().toString() + "," + activityComment.getContent();
        assertEquals("a001,001,u1," + date.toString() +",good", result);
    }
}
