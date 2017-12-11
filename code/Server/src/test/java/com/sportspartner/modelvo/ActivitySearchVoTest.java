package com.sportspartner.modelvo;

import org.junit.*;

import static org.junit.Assert.assertEquals;

public class ActivitySearchVoTest {
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
        ActivitySearchVO activitySearchVO = new ActivitySearchVO();
        activitySearchVO.setSportId("001");
        activitySearchVO.setStarttime("NULL");
        activitySearchVO.setEndtime("NULL");
        activitySearchVO.setPlace("NULL");
        activitySearchVO.setLongitude(1000);
        activitySearchVO.setLatitude(1000);
        activitySearchVO.setCapacity(-1);

        activitySearchVO.getPlace();

        assertEquals("NULL", activitySearchVO.getPlace());
    }

}
