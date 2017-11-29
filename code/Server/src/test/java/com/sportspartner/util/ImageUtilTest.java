package com.sportspartner.util;

import org.junit.*;

import java.awt.image.BufferedImage;

import static org.junit.Assert.assertEquals;

public class ImageUtilTest {
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
     * Test when ImageUtil is called
     */
    @Test
    public void testImageUtil(){
        ImageUtil imageUtil = new ImageUtil();
        String imagePath = imageUtil.getImagePath("001", "SPORT", "origin");
        BufferedImage userImage = imageUtil.getUserImage("u1", "small");
        BufferedImage sportImage = imageUtil.getSportImage("001", "small");
        Boolean isSavedUserImage = imageUtil.saveUserImage(userImage, "u1", "small");
        Boolean isSavedSportImage = imageUtil.saveSportImage(sportImage, "001", "small");

        assertEquals("./res/sporticon/001_origin.png", imagePath);
        assertEquals(true, isSavedUserImage);
        assertEquals(true, isSavedSportImage);

    }
}
