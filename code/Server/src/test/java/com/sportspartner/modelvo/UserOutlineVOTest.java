package com.sportspartner.modelvo;

import com.sportspartner.model.Person;
import org.junit.*;

import static org.junit.Assert.assertEquals;

public class UserOutlineVOTest {

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
     * Test when UserOutlineVO is called
     */
    @Test
    public void testUserOutlineVO() {
        UserOutlineVO userOutlineVO = new UserOutlineVO();
        userOutlineVO.setUserId("01");
        userOutlineVO.setUserName("u1");
        userOutlineVO.setIconUUID("UUID");

        String expected = "01 u1 UUID";
        String actual = userOutlineVO.getUserId() + " " +  userOutlineVO.getUserName() + " "
                + userOutlineVO.getIconUUID();

        assertEquals(expected, actual);
    }

    /**
     * Test when SetFromPerson is called
     */
    @Test
    public void testSetFromPerson() {
        Person person = new Person("02", "u2", "Baltimore", "Female", 23, 5, 10, 5, 10, "Path");
        UserOutlineVO userOutlineVO = new UserOutlineVO();
        userOutlineVO.setFromPerson(person);

        String expected = "02 u2 Path";
        String actual = userOutlineVO.getUserId() + " " +  userOutlineVO.getUserName() + " "
                + userOutlineVO.getIconUUID();

        assertEquals(expected, actual);
    }
}
