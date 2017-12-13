package com.sportspartner.util;

import java.sql.*;

public class DaoUtil {

    public static void CloseDao (ResultSet rs, PreparedStatement stmt, Connection c)throws SQLException{
            rs.close();
            stmt.close();
            c.close();
    }
    public static void CloseDaoNoRs (PreparedStatement stmt, Connection c)throws SQLException{
        stmt.close();
        c.close();
    }
    public static void CloseDao (ResultSet rs, Statement stmt, Connection c)throws SQLException{
        rs.close();
        stmt.close();
        c.close();
    }
}
