package com.sportspartner.dao.impl;

import org.junit.*;
import com.sportspartner.model.Interest;

import static org.junit.Assert.assertEquals;

public class InterestDaoImplTest {
    @BeforeClass
    public static void setUpBeforeClass(){
    }

    @AfterClass
    public static void tearDownAfterClass(){
    }

    @Before
    public void setUp(){}

    @After
    public void teardown(){
    }

    @Test
    public void testInterestDaoImpl(){
        InterestDaoImpl interestDaoImpl = new InterestDaoImpl();
        Interest interest = new Interest("u3","001");
        Interest newInterest = new Interest("u3","002");
        assertEquals (true, interestDaoImpl.newInterest(interest));
        assertEquals(true,interestDaoImpl.updateInterest(interest,"002"));
        interest.setSportId("002");
        assertEquals(true,interestDaoImpl.getInterest("u3").size()>0);
        assertEquals(true,interestDaoImpl.deleteInterest(newInterest));

    }
}
