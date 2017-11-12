package com.sportspartner.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionUtil {
    /**
     * Connect to our own the Postgresql Server.
     * @return the Connection object
     */
    public Connection connectDB() {
        Connection c = null;
        try {
            Class.forName("org.postgresql.Driver");
            //c = DriverManager
             //       .getConnection("jdbc:postgresql://elmer.db.elephantsql.com:5432/rdkxzlvf", "rdkxzlvf", "At7YAFMgJqq1aMAcqMTY9CixdC_toDeM");
            c = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"));
        } catch ( Exception e ) {
            System.err.println( "nao ni mei"+e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        return c;
    }

}