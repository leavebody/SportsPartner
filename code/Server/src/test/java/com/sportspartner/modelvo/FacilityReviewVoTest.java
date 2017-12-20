package com.sportspartner.modelvo;

import org.junit.*;

import static org.junit.Assert.assertEquals;

public class FacilityReviewVoTest {
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
     * Test when ActivityOutLineVO is called
     */
    @Test
    public void testActivityOutLineVO(){
        FacilityReviewVO facilityReviewVO = new FacilityReviewVO();

        facilityReviewVO.setActivityid("NULL");
        facilityReviewVO.setReviewer("u1");
        facilityReviewVO.setReviewee("u2");
        facilityReviewVO.setScore(5);
        facilityReviewVO.setComments("null");

        facilityReviewVO.getActivityid();

        assertEquals("FacilityReviewVO{activityid='NULL', reviewer='u1', reviewee='u2', score=5.0, comments='null'}", facilityReviewVO.toString());
    }
}
