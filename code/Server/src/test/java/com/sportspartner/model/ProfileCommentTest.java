package com.sportspartner.model;

import org.junit.*;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ProfileCommentTest {
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
     * Test when ProfileComment is called
     */
    @Test
    public void testProfileComment(){
        ProfileComment profileComment = new ProfileComment();
        profileComment.setUserId("u1");
        profileComment.setCommentId("001");
        profileComment.setAuthorId("u2");
        profileComment.setAuthorName("dog");
        profileComment.setTime(new Date(1234));
        profileComment.setContent("Nice");

        assertEquals("u1", profileComment.getUserId());
        assertEquals("001", profileComment.getCommentId());
        assertEquals("u2", profileComment.getAuthorId());
        assertEquals("dog", profileComment.getAuthorName());
        assertEquals(new Date(1234), profileComment.getTime());
        assertEquals("Nice", profileComment.getContent());
    }
}
