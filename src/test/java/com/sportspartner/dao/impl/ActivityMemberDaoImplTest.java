package com.sportspartner.dao.impl;

import com.sportspartner.model.Activity;
import com.sportspartner.model.ActivityMember;
import com.sportspartner.model.Authorization;
import org.junit.*;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ActivityMemberDaoImplTest {
    @BeforeClass
    public static void setUpBeforeClass(){
    }

    @AfterClass
    public static void tearDownAfterClass(){
    }

    @Before
    public void setUp(){}

    @After
    public void teardown(){
    }

    @Test
    public void testActivityMemberDaoImpl() throws SQLException {
        ActivityDaoImpl activityDaoImpl = new ActivityDaoImpl();
        ActivityMemberDaoImpl activityMemberDaoImpl = new ActivityMemberDaoImpl();
        Date startTime = new Date(System.currentTimeMillis());
        Date endTime = new Date(System.currentTimeMillis()+10);
        activityMemberDaoImpl.deleteAllActivityMembers("ac001");
        activityDaoImpl.deleteActivity("ac001");
        Activity newActivity = new Activity("ac001", "u1", "001", "OPEN", "003",0.0,0.0,"21210","somewhere",startTime, endTime, 4, 3 , "Do nothing!");
        assertEquals(true,activityDaoImpl.newActivity(newActivity));
        ActivityMember activityMember1 = new ActivityMember("ac001","u2");
        ActivityMember activityMember2 = new ActivityMember("ac001","u3");
        assertEquals(true,activityMemberDaoImpl.newActivityMember(activityMember1));
        assertEquals(true,activityMemberDaoImpl.newActivityMember(activityMember2));
        assertEquals(true,activityMemberDaoImpl.hasActivityMember(activityMember1));
        assertEquals(true,activityMemberDaoImpl.getAllActivitymembers("ac001").size()==2);
        assertEquals(true,activityMemberDaoImpl.deleteActivityMember(activityMember1));
        assertEquals(true,activityMemberDaoImpl.deleteAllActivityMembers("ac001"));
        assertEquals(true,activityDaoImpl.deleteActivity("ac001"));

    }
}
