package com.sportspartner.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.net.URI;
import java.net.URISyntaxException;
import org.sql2o.*;

public class ConnectionUtil {
    /**
     * Connect to our own the Postgresql Server.
     * @return the Connection object
     */

    public Connection connectDB() {
        URI dbUri;
        Connection c = null;
        try {
              Class.forName("org.postgresql.Driver");


            if (System.getenv("DATABASE_URL") == null) {
                c = DriverManager
                        .getConnection("jdbc:postgresql://elmer.db.elephantsql.com:5432/rdkxzlvf", "rdkxzlvf", "At7YAFMgJqq1aMAcqMTY9CixdC_toDeM");
                System.out.println("This is the original database!");
            } else {
                dbUri = new URI(System.getenv("DATABASE_URL"));
                //dbUri = new URI(System.getenv("JDBC_DATABASE_URL"));
                String dburiString = System.getenv("JDBC_DATABASE_URL");
                int port = dbUri.getPort();
                String host = dbUri.getHost();
                String path = dbUri.getPath();
                String username = (dbUri.getUserInfo() == null) ? null : dbUri.getUserInfo().split(":")[0];
                String password = (dbUri.getUserInfo() == null) ? null : dbUri.getUserInfo().split(":")[1];
                //c = DriverManager.getConnection("jdbc:postgresql://" + host + ":" + port + path, username, password);
                c = DriverManager.getConnection(dburiString);
            }
            //c =  DriverManager.getConnection(dbUrl);
            //c = DriverManager
            //        .getConnection("jdbc:postgresql://elmer.db.elephantsql.com:5432/rdkxzlvf", "rdkxzlvf", "At7YAFMgJqq1aMAcqMTY9CixdC_toDeM");
        } catch ( Exception e ) {
            System.err.println(e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        return c;
    }

}
