package com.sportspartner.model;

import org.junit.*;

import static org.junit.Assert.assertEquals;

public class DeviceRegistrationTest {
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
     * Test when DeviceRegistration is called
     */
    @Test
    public void testDeviceRegistration(){
        DeviceRegistration deviceRegistration = new DeviceRegistration();
        deviceRegistration.setUserId("u1");
        deviceRegistration.setRegistrationId("001");

        assertEquals("u1 001", deviceRegistration.getUserId() +" "+deviceRegistration.getRegistrationId());
    }
}
