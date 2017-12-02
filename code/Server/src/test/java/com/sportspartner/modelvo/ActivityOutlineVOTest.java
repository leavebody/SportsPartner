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
        
        Date date = new Date(1234);
        String expected = "01 u1 OPEN NULL Basketball " + date.toString() + " " + date.toString()
                + " 0.0 0.1 JHU 4 1 001";
        String actual = activityOutlineVO.getActivityId() + " " +  activityOutlineVO.getCreatorId() + " "
                + activityOutlineVO.getStatus() + " " + activityOutlineVO.getSportIconUUID() + " " + activityOutlineVO.getSportName() + " "
                + activityOutlineVO.getStartTime().toString() + " " + activityOutlineVO.getEndTime().toString() + " "
                + String.valueOf(activityOutlineVO.getLatitude()) + " " + String.valueOf(activityOutlineVO.getLongitude()) + " "
                + activityOutlineVO.getAddress() + " " + String.valueOf(activityOutlineVO.getCapacity()) + " "
                + String.valueOf(activityOutlineVO.getSize()) + " " + activityOutlineVO.getFacilityId();

        assertEquals(expected, actual);
    }

}
