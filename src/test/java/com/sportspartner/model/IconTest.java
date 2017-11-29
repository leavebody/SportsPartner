package com.sportspartner.model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.*;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by xuanzhang on 11/29/17.
 */
public class IconTest {
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
     * Test when Icon Model is called
     */
    @Test
    public void testIcon(){
        Icon icon = new Icon();
        icon.setSpId("1");
        icon.setIconUUID("2");
        icon.setSmallPath("3");
        icon.setOriginPath("4");
        icon.setObject("5");

        assertEquals("1", icon.getSpId());
        assertEquals("2", icon.getIconUUID());
        assertEquals("3", icon.getSmallPath());
        assertEquals("4", icon.getOriginPath());
        assertEquals("5", icon.getObject());
    }
}
