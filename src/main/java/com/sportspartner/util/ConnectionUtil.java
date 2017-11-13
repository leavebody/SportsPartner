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
//            c = DriverManager
//                    .getConnection("jdbc:postgresql://elmer.db.elephantsql.com:5432/rdkxzlvf", "rdkxzlvf", "At7YAFMgJqq1aMAcqMTY9CixdC_toDeM");
            //c = DriverManager.getConnection("postgres://npvilmribuqyfn:4de73b009926d90511554c0cd40ca9fa0a133b604d768fbc4abffe1d00cff4fb@ec2-50-17-217-166.compute-1.amazonaws.com:5432/ddv402olp7iu27", "npvilmribuqyfn","4de73b009926d90511554c0cd40ca9fa0a133b604d768fbc4abffe1d00cff4fb");
            c = DriverManager.getConnection(System.getenv("DATABASE_URL"));
        } catch ( Exception e ) {
            System.err.println( "nao ni mei"+e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        return c;
    }

}
