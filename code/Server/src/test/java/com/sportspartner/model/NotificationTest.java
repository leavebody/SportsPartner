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
        Date time = new Date(1234);
        notification.setTime(time);
        Date date = new Date(1234);

        String expected = "1 2 3 4 5 6 7 8 " + date.toString();
        String actual = notification.getReceiverId() + " " +  notification.getNotificationId() + " "
                + notification.getNotificationTitle() + " " + notification.getNotificationDetail() + " " + String.valueOf(notification.getNotificationState()) + " "
                + String.valueOf(notification.getNotificationPriority()) + " " + notification.getNotificationType() + " " + notification.getNotificationSender() + " "
                + notification.getTime().toString();

        assertEquals(expected, actual);
    }

}
