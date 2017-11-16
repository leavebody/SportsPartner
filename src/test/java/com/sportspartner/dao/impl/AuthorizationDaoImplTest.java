package com.sportspartner.dao.impl;

import com.sportspartner.model.Authorization;
import org.junit.*;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class AuthorizationDaoImplTest {

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
    public void testAuthorizationDaoImpl() {
        Authorization authorization = new Authorization("u1","keytest");
        AuthorizationDaoImpl authorizationDaoImpl = new AuthorizationDaoImpl();
        assertEquals(true,authorizationDaoImpl.newAuthorization(authorization));
        assertEquals(true,authorizationDaoImpl.hasAuthorization(authorization));
        List<Authorization> authorizations = authorizationDaoImpl.getAllAuthorizations();
        assertEquals(true,authorizations.size()>0);
        assertEquals(true,authorizationDaoImpl.updateAuthorization(authorization,"newkey"));
        authorization.setKey("newkey");
        assertEquals(true,authorizationDaoImpl.deleteAuthorization(authorization));
    }

}
