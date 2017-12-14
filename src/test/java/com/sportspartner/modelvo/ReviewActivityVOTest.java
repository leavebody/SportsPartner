package com.sportspartner.modelvo;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ReviewActivityVOTest {
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
     * Test when ReviewActivityVO is called
     */
    @Test
    public void testReviewActivityVO(){
        ReviewActivityVO reviewActivityVO = new ReviewActivityVO();
        FacilityReviewVO facilityReviewVO = new FacilityReviewVO();
        ArrayList<UserReviewVO> arrayList = new ArrayList<>();
        UserReviewVO userReviewVO = new UserReviewVO();
        arrayList.add(userReviewVO);

        reviewActivityVO.setKey("key");
        reviewActivityVO.setUserId("u1");
        reviewActivityVO.setUserReviewVOs(arrayList);
        reviewActivityVO.setFacilityReviewVO(facilityReviewVO);

        facilityReviewVO.getActivityid();

        assertEquals("ReviewActivityVO{userId='u1', userReviewVOs=[UserReviewVO{activityid='null', reviewer='null', reviewee='null', punctuality=0.0, participation=0.0, comments='null'}], facilityReviewVO=FacilityReviewVO{activityid='null', reviewer='null', reviewee='null', score=0.0, comments='null'}, key='key'}", reviewActivityVO.toString());
    }
}
