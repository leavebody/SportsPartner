package com.sportspartner.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionUtil {

        public Connection connectDB() {
            Connection c = null;
            try {
                Class.forName("org.postgresql.Driver");
                c = DriverManager
                        .getConnection("jdbc:postgresql://elmer.db.elephantsql.com:5432/rdkxzlvf", "rdkxzlvf", "At7YAFMgJqq1aMAcqMTY9CixdC_toDeM");
                
            } catch ( Exception e ) {
                System.err.println( e.getClass().getName()+": "+ e.getMessage() );
                System.exit(0);
            }
            return c;
        }

}