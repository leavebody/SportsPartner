package com.sportspartner.dao.impl;

import com.sportspartner.model.DeviceRegistration;
import org.junit.*;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class DeviceRegistrationDaoImplTest {

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
    public void testDeviceRegistrationDaoImpl() throws SQLException {
        DeviceRegistration deviceRegistration = new DeviceRegistration("u1","device");
        DeviceRegistrationDaoImpl deviceRegistrationDaoImpl = new DeviceRegistrationDaoImpl();
        assertEquals(true,deviceRegistrationDaoImpl.newDeviceRegistration(deviceRegistration));
        assertEquals(true,deviceRegistrationDaoImpl.hasDeviceRegistration(deviceRegistration));
        assertEquals(true,deviceRegistrationDaoImpl.getAllDeviceRegistrations("u1").size()>0);
        assertEquals(true,deviceRegistrationDaoImpl.deleteDeviceRegistration(deviceRegistration));
    }
}
