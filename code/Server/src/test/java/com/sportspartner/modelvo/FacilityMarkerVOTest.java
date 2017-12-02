package com.sportspartner.modelvo;

import org.junit.*;

import static org.junit.Assert.assertEquals;

public class FacilityMarkerVOTest {

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
     * Test when ActivityOutLineVO is called
     */
    @Test
    public void testFacilityMarkerVO() {
        FacilityMarkerVO facilityMarkerVO = new FacilityMarkerVO();
        facilityMarkerVO.setFacilityId("001");
        facilityMarkerVO.setFacilityName("facility");
        facilityMarkerVO.setLatitude(1.0);
        facilityMarkerVO.setLongitude(0.1);

        assertEquals("001 facility 1.0 0.1", facilityMarkerVO.getFacilityId() +" "+ facilityMarkerVO.getFacilityName()+ " "+ String.valueOf(facilityMarkerVO.getLatitude())+ " "+ String.valueOf(facilityMarkerVO.getLongitude()));
//        assertEquals("facility", facilityMarkerVO.getFacilityName());
//        assertEquals("1.0", String.valueOf(facilityMarkerVO.getLatitude()));
//        assertEquals("0.1", String.valueOf(facilityMarkerVO.getLongitude()));
    }
}
