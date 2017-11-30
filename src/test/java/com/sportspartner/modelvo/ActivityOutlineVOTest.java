package com.sportspartner.modelvo;

import org.junit.*;

import java.util.Date;

import static org.junit.Assert.assertEquals;


public class ActivityOutlineVOTest {

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
        ActivityOutlineVO activityOutlineVO = new ActivityOutlineVO();
        activityOutlineVO.setActivityId("01");
        activityOutlineVO.setCreatorId("u1");
        activityOutlineVO.setStatus("OPEN");
        activityOutlineVO.setSportIconUUID("NULL");
        activityOutlineVO.setSportName("Basketball");
        activityOutlineVO.setStartTime(new Date(1234));
        activityOutlineVO.setEndTime(new Date(1234));
        activityOutlineVO.setFacilityId("001");
        activityOutlineVO.setLatitude(0.0);
        activityOutlineVO.setLongitude(0.1);
        activityOutlineVO.setAddress("JHU");
        activityOutlineVO.setCapacity(4);
        activityOutlineVO.setSize(1);

//        assertEquals("01",activityOutlineVO.getActivityId());
//        assertEquals("u1",activityOutlineVO.getCreatorId());
//        assertEquals("OPEN",activityOutlineVO.getStatus());
//        assertEquals("NULL",activityOutlineVO.getSportIconUUID());
//        assertEquals("Basketball",activityOutlineVO.getSportName());
//        assertEquals(new Date(1234), activityOutlineVO.getStartTime());
//        assertEquals(new Date(1234),activityOutlineVO.getEndTime());
//        assertEquals("0.0", String.valueOf(activityOutlineVO.getLatitude()));
//        assertEquals("0.1", String.valueOf(activityOutlineVO.getLongitude()));
//        assertEquals("JHU",activityOutlineVO.getAddress());
//        assertEquals(4,activityOutlineVO.getCapacity());
//        assertEquals(1,activityOutlineVO.getSize());
//        assertEquals("001",activityOutlineVO.getFacilityId());
        assertEquals("01 u1 OPEN NULL Basketball Wed Dec 31 19:00:01 EST 1969 Wed Dec 31 19:00:01 EST 1969 0.0 0.1JHU 4 1 001",activityOutlineVO.getActivityId()+ " " + activityOutlineVO.getCreatorId()+ " " + activityOutlineVO.getStatus() +
                " " +activityOutlineVO.getSportIconUUID() + " " +activityOutlineVO.getSportName() + " " + activityOutlineVO.getStartTime()
                + " "+ activityOutlineVO.getEndTime()+ " "+ String.valueOf(activityOutlineVO.getLatitude())+" " + String.valueOf(activityOutlineVO.getLongitude()
                + activityOutlineVO.getAddress())+ " "+ activityOutlineVO.getCapacity()+ " " +activityOutlineVO.getSize()+ " " + activityOutlineVO.getFacilityId());
    }

}
