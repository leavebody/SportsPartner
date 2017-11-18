package com.sportspartner.dao.impl;

import com.sportspartner.model.Notification;

import org.junit.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;


public class NotificationDaoImplTest {
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
    public void testActivityDaoImpl(){
        
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
