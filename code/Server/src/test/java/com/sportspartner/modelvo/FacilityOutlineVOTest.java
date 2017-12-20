package com.sportspartner.modelvo;

import org.junit.*;

import static org.junit.Assert.assertEquals;

public class FacilityOutlineVOTest {
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
    public void testActivityOutLineVO(){
        FacilityOutlineVO facilityOutlineVO = new FacilityOutlineVO("001", "name", "001", "Tennis", "UUID", 0.0, 1.0, "add", 5);
        facilityOutlineVO.setFacilityId("002");
        facilityOutlineVO.setSportUUID("001");
        facilityOutlineVO.setFacilityName("N");
        facilityOutlineVO.setSportName("SPORT");
        facilityOutlineVO.setSportUUID("UUID");
        facilityOutlineVO.setScore(4.0);
        facilityOutlineVO.setLongitude(facilityOutlineVO.getLongitude());
        facilityOutlineVO.setLatitude(facilityOutlineVO.getLatitude());
        facilityOutlineVO.setAddress("add");
        facilityOutlineVO.setSportId("111");


        assertEquals("002NUUIDSPORT4.01.01.0add111", facilityOutlineVO.getFacilityId()+facilityOutlineVO.getFacilityName()+facilityOutlineVO.getSportUUID()+facilityOutlineVO.getSportName()+String.valueOf(facilityOutlineVO.getScore())+String.valueOf(facilityOutlineVO.getLatitude())+String.valueOf(facilityOutlineVO.getLatitude())+facilityOutlineVO.getAddress()+facilityOutlineVO.getSportId());
    }
}
