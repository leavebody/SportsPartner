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
            c = DriverManager
                    .getConnection("jdbc:postgres://ahtajjtgbpbnic:108f9970bc1a9d49234867433c7b32f5494c553c3795b16d3585c1ba02c8f504@ec2-54-225-94-143.compute-1.amazonaws.com:5432/d90iv6g3rmligc");
            //c = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"));
        } catch ( Exception e ) {
            System.err.println( "nao ni mei"+e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        return c;
    }

}
