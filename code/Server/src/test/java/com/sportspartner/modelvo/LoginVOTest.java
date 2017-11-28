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
     * Test when ActivityOutLineVO is called
     */
    @Test
    public void testLoginVO() {
        LoginVO loginVO = new LoginVO();
        loginVO.setUserId("u1");
        loginVO.setPassword("p1");
        loginVO.setRegistrationId("u1u1");

        assertEquals("u1", loginVO.getUserId());
        assertEquals("p1", loginVO.getPassword());
        assertEquals("u1u1", loginVO.getRegistrationId());

        LoginVO loginVO1 = new LoginVO("u2", "p2", "u2u2");

        assertEquals("u2", loginVO1.getUserId());
        assertEquals("p2", loginVO1.getPassword());
        assertEquals("u2u2", loginVO1.getRegistrationId());

    }
}
