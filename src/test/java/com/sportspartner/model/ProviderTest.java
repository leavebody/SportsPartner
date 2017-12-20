package com.sportspartner.model;

import org.junit.*;

import static org.junit.Assert.assertEquals;

public class ProviderTest {
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

    @Test
    public void testProvider(){
        Provider provider = new Provider();
        provider.setProviderId("001");
        provider.setProviderName("Someone");
        assertEquals("001 Someone", provider.getProviderId()+" "+provider.getProviderName());

    }
}
