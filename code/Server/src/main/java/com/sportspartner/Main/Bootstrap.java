package com.sportspartner.Main;

import com.sportspartner.Controllers.LoginController;
import com.sportspartner.Controllers.SignUpController;
import com.sportspartner.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataSource;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static spark.Spark.ipAddress;
import static spark.Spark.port;
import static spark.Spark.staticFileLocation;

public class Bootstrap {
    public static final String IP_ADDRESS = "localhost";
    public static final int PORT = 8080;

    private static final Logger logger = LoggerFactory.getLogger(Bootstrap.class);

    public static void main(String[] args) throws Exception {

        //Specify the IP address and Port at which the server should be run
        ipAddress(IP_ADDRESS);
        port(PORT);
        //Specify the sub-directory from which to serve static resources (like html and css)
        staticFileLocation("/public");

        //Create the model instance and then configure and start the web service
        try {
            UserService model = new UserService();
            new LoginController(model);
            new SignUpController(model);
        } catch (Exception ex) {
            logger.error("Failed to create a GameService instance. Aborting");
        }
    }

}
