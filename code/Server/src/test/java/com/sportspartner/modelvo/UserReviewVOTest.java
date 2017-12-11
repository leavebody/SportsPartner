package com.sportspartner.modelvo;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserReviewVOTest {
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
     * Test when UserReviewVO is called
     */
    @Test
    public void testUserReviewVO(){
        UserReviewVO userReviewVO = new UserReviewVO();

        userReviewVO.setActivityid("NULL");
        userReviewVO.setReviewer("u1");
        userReviewVO.setReviewee("u2");
        userReviewVO.setPunctuality(5);
        userReviewVO.setParticipation(5);
        userReviewVO.setComments("comment");
        userReviewVO.getActivityid();

        assertEquals("UserReviewVO{activityid='NULL', reviewer='u1', reviewee='u2', punctuality=5.0, participation=5.0, comments='comment'}", userReviewVO.toString());
    }
}
