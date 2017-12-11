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
        FacilityComment facilityComment = new FacilityComment("001", "NULL", "u1", "NULL", "good", new Date(1234));
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

        String expected = "001 001 UUID 0011 0.0 1.1 21210 address provider 5.0 6 time good";
        String actual = facility.getFacilityId() + " " + facility.getFacilityName() + " "
                + facility.getIconUUID() + " " + facility.getSportId() + " " + String.valueOf(facility.getLongitude()) + " "
                + String.valueOf(facility.getLatitude()) + " " + facility.getZipcode() + " " + facility.getAddress() + " "
                + facility.getProviderId() + " " + String.valueOf(facility.getScore()) + " " + String.valueOf(facility.getScoreCount()) + " "
                + facility.getOpenTime() + " " + facility.getDescription();

        assertEquals(expected, actual);
    }
}
