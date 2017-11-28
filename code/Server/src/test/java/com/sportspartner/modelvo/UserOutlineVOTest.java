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
        Person person = new Person("02", "u2", "Baltimore", "Female", 23, 5, 10, 5, 10, "Path");

        UserOutlineVO userOutlineVO = new UserOutlineVO();
        userOutlineVO.setUserId("01");
        userOutlineVO.setUserName("u1");
        userOutlineVO.setIconUUID("UUID");

        assertEquals("01", userOutlineVO.getUserId());
        assertEquals("u1", userOutlineVO.getUserName());
        assertEquals("UUID", userOutlineVO.getIconUUID());

        userOutlineVO.setFromPerson(person);
        assertEquals("02", userOutlineVO.getUserId());
        assertEquals("u2", userOutlineVO.getUserName());
        assertEquals("Path", userOutlineVO.getIconUUID());
    }
}
