package com.sportspartner.model;

import org.junit.*;

import static org.junit.Assert.assertEquals;

public class PersonTest {
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
     * Test when Person is called
     */
    @Test
    public void testPerson(){
        Person person = new Person("u1");
        person.setUserId("u1");
        person.setPunctualityCount(5);
        person.setParticipationCount(6);

        String actual = person.getUserId() + " " + person.getPunctualityCount() + " " + String.valueOf(person.getParticipationCount());
        assertEquals("u1 5 6", actual);
    }
}
