package com.sportspartner.model;

import org.junit.*;

import static org.junit.Assert.assertEquals;

public class ActivityTest {
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
     * Test when Activity is called
     */
    @Test
    public void testActivity(){
        Facility facility = new Facility("001", "name", "UUID","001", 0.0, 1.1, "21210", "add", "001",5,6, "10", "good");
        Activity activity = new Activity();
        activity.setFacilityId("f01");
        activity.setStatus("OPEN");
        activity.setLongitude(0.0);
        activity.setZipcode("21210");
        activity.setLatitude(1.1);
        activity.setCapacity(4);
        activity.setSize(3);
        activity.setDescription("join");
        activity.setFromFacility(facility);

        assertEquals("f01", activity.getFacilityId());
    }
}
