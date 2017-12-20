package com.sportspartner.modelvo;

import com.sportspartner.model.Facility;
import com.sportspartner.model.Provider;
import com.sportspartner.model.Sport;
import org.junit.*;

import static org.junit.Assert.assertEquals;

public class FacilityVOTest {

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
     * Test when ActivityOutLineVO set functions are called
     */
    @Test
    public void testLoginVOSet() {
        FacilityVO facilityVO1 = new FacilityVO();
        FacilityVO facilityVO2 = new FacilityVO("f01","Playground","p01","Someone","iconId","001","swimming","sportIconId",100.0,100.0,"Somewhere",5.0,"9:00-12:00","have fun!");
        FacilityVO facilityVO3 = new FacilityVO();
        facilityVO1.setFacilityId(facilityVO2.getFacilityId());
        facilityVO1.setFacilityName(facilityVO2.getFacilityName());
        facilityVO1.setProviderId(facilityVO2.getProviderId());
        facilityVO1.setProviderName(facilityVO2.getProviderName());
        facilityVO1.setIconUUID(facilityVO2.getIconUUID());
        facilityVO1.setSportId(facilityVO2.getSportId());
        facilityVO1.setSportName(facilityVO2.getSportName());
        facilityVO1.setSportIconUUID(facilityVO2.getSportIconUUID());
        facilityVO1.setLongitude(facilityVO2.getLongitude());
        facilityVO1.setLatitude(facilityVO2.getLatitude());
        facilityVO1.setOpenTime(facilityVO2.getOpenTime());
        facilityVO1.setDescription(facilityVO2.getDescription());
        facilityVO3.setFromFacility(new Facility());
        facilityVO3.setFromSport(new Sport());
        facilityVO3.setFromProvider(new Provider());
        assertEquals(true, facilityVO1.getFacilityId()==facilityVO2.getFacilityId());
    }
}
