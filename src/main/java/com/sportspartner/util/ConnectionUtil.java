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
            if (System.getenv("DATABASE_URL") == null) {
                dbUri = new URI("postgres://localhost:5432/to_do");
            } else {
                dbUri = new URI(System.getenv("DATABASE_URL"));
            }
            int port = dbUri.getPort();
            String host = dbUri.getHost();
            String path = dbUri.getPath();
            String username = (dbUri.getUserInfo() == null) ? null : dbUri.getUserInfo().split(":")[0];
            String password = (dbUri.getUserInfo() == null) ? null : dbUri.getUserInfo().split(":")[1];
//            c = DriverManager
//                    .getConnection("jdbc:postgresql://elmer.db.elephantsql.com:5432/rdkxzlvf", "rdkxzlvf", "At7YAFMgJqq1aMAcqMTY9CixdC_toDeM");
            //c = DriverManager.getConnection("postgres://npvilmribuqyfn:4de73b009926d90511554c0cd40ca9fa0a133b604d768fbc4abffe1d00cff4fb@ec2-50-17-217-166.compute-1.amazonaws.com:5432/ddv402olp7iu27", "npvilmribuqyfn","4de73b009926d90511554c0cd40ca9fa0a133b604d768fbc4abffe1d00cff4fb");
            System.out.println(host);
            System.out.println(port);
            System.out.println(path);
            System.out.println(username);
            System.out.println(password);

            c = DriverManager.getConnection("postgres://" + host + ":" + port + path, "ddv402olp7iu27", password);
        } catch ( Exception e ) {
            System.err.println( "nao ni mei"+e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        return c;
    }

}
