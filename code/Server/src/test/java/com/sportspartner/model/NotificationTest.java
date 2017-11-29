package com.sportspartner.model;

import org.junit.*;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by xuanzhang on 11/29/17.
 */
public class NotificationTest {
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
     * Test when Notification Model is called
     */
    @Test
    public void testIcon(){
        Notification notification = new Notification();
        notification.setReceiverId("1");
        notification.setNotificationId("2");
        notification.setNotificationTitle("3");
        notification.setNotificationDetail("4");
        notification.setNotificationState(5);
        notification.setNotificationPriority(6);
        notification.setNotificationType("7");
        notification.setNotificationSender("8");
        Date time = new Date();
        notification.setTime(time);

        assertEquals("1", notification.getReceiverId());
        assertEquals("2", notification.getNotificationId());
        assertEquals("3", notification.getNotificationTitle());
        assertEquals("4", notification.getNotificationDetail());
        assertEquals(5, notification.getNotificationState());
        assertEquals(6, notification.getNotificationPriority());
        assertEquals("7", notification.getNotificationType());
        assertEquals("8", notification.getNotificationSender());
        assertEquals(new Date(), notification.getTime());
    }

}
