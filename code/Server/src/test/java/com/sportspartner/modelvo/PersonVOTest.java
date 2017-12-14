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

        String expected = "u1 Name JHU Female 20 5.0 5.0 UUID";
        String actual = personVO.getUserId() + " " +  personVO.getUserName() + " "
                + personVO.getAddress() + " " + personVO.getGender() + " "
                + String.valueOf(personVO.getAge()) + " " + String.valueOf(personVO.getPunctuality()) + " "
                + String.valueOf(personVO.getParticipation()) + " " +personVO.getIconUUID();

        assertEquals(expected, actual);
    }
}
