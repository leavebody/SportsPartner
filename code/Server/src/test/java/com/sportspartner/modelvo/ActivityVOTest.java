package com.sportspartner.modelvo;

import com.sportspartner.model.ActivityComment;
import org.junit.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ActivityVOTest {

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
    public void testActivityVO(){
        List<UserOutlineVO> members = new ArrayList<>();
        members.add(new UserOutlineVO("01", "u1", "p1"));

        List<ActivityComment> disscussion = new ArrayList<>();
        disscussion.add(new ActivityComment("01", "001", "u1", new Date(1234), "Good"));

        ActivityVO activityVO = new ActivityVO();
        activityVO.setActivityId("01");
        activityVO.setCreatorId("u1");
        activityVO.setStatus("OPEN");
        activityVO.setSportIconUUID("NULL");
        activityVO.setSportIconPath("Path");
        activityVO.setSportName("Basketball");
        activityVO.setStartTime(new Date(1234));
        activityVO.setEndTime(new Date(1234));
        activityVO.setFacilityId("001");
        activityVO.setFacilityName("facility");
        activityVO.setLatitude(0.0);
        activityVO.setLongitude(0.1);
        activityVO.setAddress("JHU");
        activityVO.setCapacity(4);
        activityVO.setSize(1);
        activityVO.setMembers(members);
        activityVO.setDetail("detail");
        activityVO.setDiscussion(disscussion);


//        assertEquals("01",activityVO.getActivityId());
//        assertEquals("u1",activityVO.getCreatorId());
//        assertEquals("OPEN",activityVO.getStatus());
//        assertEquals("NULL",activityVO.getSportIconUUID());
//        assertEquals("Path",activityVO.getSportIconPath());
//        assertEquals("Basketball",activityVO.getSportName());
//        assertEquals(new Date(1234), activityVO.getStartTime());
//        assertEquals(new Date(1234), activityVO.getEndTime());
//        assertEquals("0.0", String.valueOf(activityVO.getLatitude()));
//        assertEquals("0.1", String.valueOf(activityVO.getLongitude()));
//        assertEquals("JHU", activityVO.getAddress());
//        assertEquals(4, activityVO.getCapacity());
//        assertEquals(1, activityVO.getSize());
//        assertEquals("001", activityVO.getFacilityId());
//        assertEquals("facility", activityVO.getFacilityName());
//        assertEquals(members, activityVO.getMembers());
//        assertEquals("detail", activityVO.getDetail());
//        assertEquals(disscussion, activityVO.getDiscussion());

        assertEquals("01 u1 OPEN NULL Path Basketball Wed Dec 31 19:00:01 EST 1969 Wed Dec 31 19:00:01 EST 1969 0.0 0.1 JHU 4 1 001 facility 1 detail 1 1",activityVO.getActivityId()+ " " +activityVO.getCreatorId() + " " + activityVO.getStatus()
                + " " + activityVO.getSportIconUUID()+ " "+ activityVO.getSportIconPath() + " " + activityVO.getSportName() + " "
                + activityVO.getStartTime() + " " + activityVO.getEndTime() + " " +String.valueOf(activityVO.getLatitude()  + " "
                + String.valueOf(activityVO.getLongitude()) + " " + activityVO.getAddress()  + " "+ activityVO.getCapacity() + " "
                +activityVO.getSize() + " "+activityVO.getFacilityId() +" "+ activityVO.getFacilityName() + " "+ activityVO.getMembers().size()
                +" "+activityVO.getDetail() + " " + activityVO.getDiscussion().size() +" "+ activityVO.getDiscussion().size()));
    }
}
