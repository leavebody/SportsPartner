package com.sportspartner.modelvo;

import org.junit.*;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class SignupVOTest {

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
     * Test when SignupVO is called
     */
    @Test
    public void testActivityOutLineVO(){
        SignupVO signupVO = new SignupVO();
        signupVO.setUserId("John");
        signupVO.setPassword("hopkins");
        signupVO.setConfirmPassword("hopkins");
        signupVO.setType("PERSON");

        String expected = "John hopkins hopkins PERSON";
        String actual = signupVO.getUserId() + " " +  signupVO.getPassword() + " "
                + signupVO.getConfirmPassword() + " " + signupVO.getType();

        assertEquals(expected, actual);
    }


}
