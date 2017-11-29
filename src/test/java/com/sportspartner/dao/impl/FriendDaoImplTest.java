package com.sportspartner.dao.impl;

import org.junit.*;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class FriendDaoImplTest {

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
    public void testFriendDaoImpl() throws SQLException {
        FriendDaoImpl friendDaoImpl = new FriendDaoImpl();
        friendDaoImpl.deleteFriend("u1","u24");
        assertEquals(true,friendDaoImpl.newFriend("u1","u24"));
        assertEquals(true,friendDaoImpl.getFriend("u1","u24"));
        assertEquals(true,friendDaoImpl.getAllFriends("u1").size()>0);
        assertEquals(true,friendDaoImpl.deleteFriend("u1","u24"));
    }
}
