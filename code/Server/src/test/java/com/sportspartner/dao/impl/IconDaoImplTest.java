package com.sportspartner.dao.impl;
import com.sportspartner.controllers.ActivityController;
import com.sportspartner.dao.DeviceRegistrationDao;
import com.sportspartner.dao.impl.ActivityDaoImpl;
import com.sportspartner.dao.impl.ActivityMemberDaoImpl;
import com.sportspartner.dao.impl.AuthorizationDaoImpl;
import com.sportspartner.dao.impl.NotificationDaoImpl;
import com.sportspartner.main.Bootstrap;
import com.sportspartner.model.*;
import com.sportspartner.service.ActivityService;
import org.junit.*;
import spark.Spark;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.sportspartner.main.Bootstrap.PORT;
import static org.junit.Assert.assertEquals;
import static spark.Spark.ipAddress;
import static spark.Spark.port;
import static spark.Spark.staticFileLocation;

public class IconDaoImplTest {
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
    public void testIconDaoImpl() throws SQLException {
        Sport sport = new Sport("100","Foo","foo");
        SportDaoImpl sportDaoImpl = new SportDaoImpl();
        IconDaoImpl iconDaoImpl = new IconDaoImpl();
        sportDaoImpl.deleteSport("100");
        sportDaoImpl.newSport(sport);
        Icon icon = new Icon("100","uuid","smallpath","originpath","USER");
        iconDaoImpl.deleteIcon("100");
        assertEquals(true,iconDaoImpl.newIcon(icon));
        assertEquals(true,iconDaoImpl.updateIcon(icon));
        assertEquals("100",iconDaoImpl.getIcon("uuid").getSpId());
        assertEquals(true,iconDaoImpl.deleteIcon("100"));
        assertEquals(true,sportDaoImpl.deleteSport("100"));

    }

}
