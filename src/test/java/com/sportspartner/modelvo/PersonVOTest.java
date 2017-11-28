package com.sportspartner.modelvo;

import org.junit.*;

import static org.junit.Assert.assertEquals;

public class PersonVOTest {
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
     * Test when PersonVO is called
     */
    @Test
    public void testPersonVO() {
        PersonVO personVO = new PersonVO();
        personVO.setUserId("u1");
        personVO.setUserName("Name");
        personVO.setAddress("JHU");
        personVO.setGender("Female");
        personVO.setAge(20);
        personVO.setPunctuality(5);
        personVO.setParticipation(5);
        personVO.setIconUUID("UUID");

        assertEquals("u1", personVO.getUserId());
        assertEquals("Name", personVO.getUserName());
        assertEquals("JHU", personVO.getAddress());
        assertEquals("Female", personVO.getGender());
        assertEquals("20", String.valueOf(personVO.getAge()));
        assertEquals("5.0", String.valueOf(personVO.getPunctuality()));
        assertEquals("5.0", String.valueOf(personVO.getParticipation()));
        assertEquals("UUID", personVO.getIconUUID());
    }
}
