package com.sportspartner.modelvo;

import org.junit.*;

import static org.junit.Assert.assertEquals;

public class LoginVOTest {

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
        LoginVO loginVO = new LoginVO();
        loginVO.setUserId("u1");
        loginVO.setPassword("p1");
        loginVO.setRegistrationId("u1u1");

        assertEquals("u1 p1 u1u1", loginVO.getUserId() + " " + loginVO.getPassword() + " " + loginVO.getRegistrationId());
    }

    /**
     * Test when ActivityOutLineVO is called
     */
    @Test
    public void testLoginVO() {
        LoginVO loginVO1 = new LoginVO("u2", "p2", "u2u2");

        assertEquals("u2 p2 u2u2", loginVO1.getUserId() + " " + loginVO1.getPassword() + " " + loginVO1.getRegistrationId());
    }
}
