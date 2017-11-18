package com.sportspartner.dao.impl;

import com.sportspartner.model.Facility;
import org.junit.*;

import static org.junit.Assert.assertEquals;

public class FacilityDaoImplTest {
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
    public void testFacilityDaoImpl(){
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
}
