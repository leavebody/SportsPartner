package com.sportspartner.modelvo;

import org.junit.*;

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

        assertEquals("John", signupVO.getUserId());
        assertEquals("hopkins", signupVO.getPassword());
        assertEquals("hopkins", signupVO.getConfirmPassword());
        assertEquals("PERSON", signupVO.getType());

    }
}
