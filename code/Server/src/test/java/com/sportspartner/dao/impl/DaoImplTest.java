package com.sportspartner.dao.impl;
import com.sportspartner.controllers.ActivityController;
import com.sportspartner.dao.DeviceRegistrationDao;
import com.sportspartner.dao.impl.ActivityDaoImpl;
import com.sportspartner.dao.impl.ActivityMemberDaoImpl;
import com.sportspartner.dao.impl.AuthorizationDaoImpl;
import com.sportspartner.dao.impl.NotificationDaoImpl;
import com.sportspartner.main.Bootstrap;
import com.sportspartner.model.*;
import com.sportspartner.service.ActivityService;
import org.junit.*;
import spark.Spark;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.sportspartner.main.Bootstrap.PORT;
import static org.junit.Assert.assertEquals;
import static spark.Spark.ipAddress;
import static spark.Spark.port;
import static spark.Spark.staticFileLocation;

public class DaoImplTest {
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
    public void testAuthorizationDaoImpl() {
        Authorization authorization = new Authorization("u1","keytest");
        AuthorizationDaoImpl authorizationDaoImpl = new AuthorizationDaoImpl();
        assertEquals(true,authorizationDaoImpl.newAuthorization(authorization));
        assertEquals(true,authorizationDaoImpl.hasAuthorization(authorization));
        List<Authorization> authorizations = authorizationDaoImpl.getAllAuthorizations();
        assertEquals(true,authorizations.size()>0);
        assertEquals(true,authorizationDaoImpl.updateAuthorization(authorization,"newkey"));
        authorization.setKey("newkey");
        assertEquals(true,authorizationDaoImpl.deleteAuthorization(authorization));
    }

    @Test
    public void testActivityMemberDaoImpl(){
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
    @Test
    public void testFriendDaoImpl(){
       FriendDaoImpl friendDaoImpl = new FriendDaoImpl();
        friendDaoImpl.deleteFriend("u1","u24");
        assertEquals(true,friendDaoImpl.newFriend("u1","u24"));
        assertEquals(true,friendDaoImpl.getFriend("u1","u24"));
        assertEquals(true,friendDaoImpl.getAllFriends("u1").size()>0);
        assertEquals(true,friendDaoImpl.deleteFriend("u1","u24"));
    }

    @Test
    public void testActivityDaoImpl(){

        ActivityDaoImpl activityDaoImpl = new ActivityDaoImpl();
        ActivityMemberDaoImpl activityMemberDaoImpl = new ActivityMemberDaoImpl();
        Date startTime = new Date(System.currentTimeMillis());
        Date endTime = new Date(System.currentTimeMillis()+10);
        activityMemberDaoImpl.deleteAllActivityMembers("ac001");
        activityDaoImpl.deleteActivity("ac001");
        //(String activityId, String creatorId, String facilityId, String status, String sportId, double longitude, double latitude, String zipcode, String address, Date startTime, Date endTime, int capacity, int size, String description)
        Activity newActivity = new Activity("ac001", "u1", "001", "OPEN", "003",0.0,0.0,"21210","somewhere",startTime, endTime, 4, 3 , "Do nothing!");
        assertEquals(true,activityDaoImpl.newActivity(newActivity));
        assertEquals("OPEN",activityDaoImpl.getActivity("ac001").getStatus());
        assertEquals(true,activityDaoImpl.getAllActivities().size()>0);
        assertEquals(true,activityDaoImpl.getUpcomingActivities("u1").size()>0);
        assertEquals(true,activityDaoImpl.getPastActivities("u1").size()>0);
        assertEquals(true,activityDaoImpl.getRecommendActivities("u1").size()>0);
        newActivity.setAddress("other place");
        assertEquals(true,activityDaoImpl.updateActivity(newActivity));
        assertEquals(true, activityDaoImpl.deleteActivity("ac001"));

    }

    @Test
    public void testDeviceRegistrationDaoImpl(){
        DeviceRegistration deviceRegistration = new DeviceRegistration("u1","device");
        DeviceRegistrationDaoImpl deviceRegistrationDaoImpl = new DeviceRegistrationDaoImpl();
        assertEquals(true,deviceRegistrationDaoImpl.newDeviceRegistration(deviceRegistration));
        assertEquals(true,deviceRegistrationDaoImpl.hasDeviceRegistration(deviceRegistration));
        assertEquals(true,deviceRegistrationDaoImpl.getAllDeviceRegistrations("u1").size()>0);
        assertEquals(true,deviceRegistrationDaoImpl.deleteDeviceRegistration(deviceRegistration));
    }

    @Test
    public void testGetFacilityDaoImpl(){
        //(String facilityId, String facilityName, String iconUUID, String sportId, double longitude, double latitude, String zipcode, String address, String providerId, double score, int scoreCount, String openTime, String description)
        //Date startTime = new Date(System.currentTimeMillis());
        //Date endTime = new Date(System.currentTimeMillis()+10);
        FacilityDaoImpl facilityDaoImpl =  new FacilityDaoImpl();
        facilityDaoImpl.deleteFacility("ff001");
        Facility facility = new Facility("ff001","some","uuid","001",0.0,0.0,"00000","somewhere","f1",0.0,0,"all day","what");

        assertEquals(true,facilityDaoImpl.newFacility(facility));
        assertEquals("ff001",facilityDaoImpl.getFacility("ff001").getFacilityId());
        assertEquals(false,facilityDaoImpl.getNearbyFacilities(0,0,0,0).size()>0);
        assertEquals(true,facilityDaoImpl.deleteFacility("ff001"));
    }
    @Test
    public void testIconDaoImpl(){
        Sport sport = new Sport("100","Foo","foo");
        SportDaoImpl sportDaoImpl = new SportDaoImpl();
        IconDaoImpl iconDaoImpl = new IconDaoImpl();
        sportDaoImpl.deleteSport("100");
        sportDaoImpl.newSport(sport);
        Icon icon = new Icon("100","uuid","smallpath","originpath","USER");
        iconDaoImpl.deleteIcon("100");
        assertEquals(true,iconDaoImpl.newIcon(icon));
        assertEquals(true,iconDaoImpl.updateIcon(icon));
        assertEquals("100",iconDaoImpl.getIcon("uuid").getSpId());
        assertEquals(true,iconDaoImpl.deleteIcon("100"));
        assertEquals(true,sportDaoImpl.deleteSport("100"));

    }
    @Test
    public void testNotificationDaoImpl() {
        String uuid = UUID.randomUUID().toString();
        Date time = new Date(System.currentTimeMillis());
        Notification notification = new Notification("u1",uuid,"title","detail","NORESPONSE","u24",time,0,0);
        NotificationDaoImpl f1 = new NotificationDaoImpl();
        assertEquals(true,f1.newNotification(notification));
        List<Notification> notifications = f1.getUnsentNotification("u1");
        assertEquals("title",notifications.get(0).getNotificationTitle());
        assertEquals(true,f1.setNotificationSent("u1",uuid));
        assertEquals(false,f1.deleteNotification(notification));
        notification.setNotificationState(1);
        assertEquals(true,f1.deleteNotification(notification));
    }

}
