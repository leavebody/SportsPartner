package com.sportspartner.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DaoUtil {

    public static void CloseDao (ResultSet rs, PreparedStatement stmt, Connection c)throws SQLException{
            rs.close();
            stmt.close();
            c.close();
    }
}
