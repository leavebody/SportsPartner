package com.sportspartner.model;

import org.junit.*;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class FacilityTest {
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
     * Test when facility is called
     */
    @Test
    public void testFacility() {
        Facility faclity1 = new Facility("001","002","UUID", "0011", 0.0, 1.1, "21210", "address",
                "provider", 5.0, 6, "time", "good");
        Facility facility = new Facility();
        facility.setFacilityId("001");
        facility.setFacilityName("001");
        facility.setIconUUID("UUID");
        facility.setSportId("0011");
        facility.setLongitude(0.0);
        facility.setLatitude(1.1);
        facility.setZipcode("21210");
        facility.setAddress("address");
        facility.setProviderId("provider");
        facility.setScore(5);
        facility.setScoreCount(6);
        facility.setOpenTime("time");
        facility.setDescription("good");

        assertEquals("001", facility.getFacilityId());
        assertEquals("001", facility.getFacilityName());
        assertEquals("UUID", facility.getIconUUID());
        assertEquals("0011", facility.getSportId());
        assertEquals("0.0", String.valueOf(facility.getLongitude()));
        assertEquals("1.1", String.valueOf(facility.getLatitude()));
        assertEquals("21210", facility.getZipcode());
        assertEquals("address", facility.getAddress());
        assertEquals("provider", facility.getProviderId());
        assertEquals("5.0", String.valueOf(facility.getScore()));
        assertEquals("6", String.valueOf(facility.getScoreCount()));
        assertEquals("time", facility.getOpenTime());
        assertEquals("good", facility.getDescription());
    }
}
