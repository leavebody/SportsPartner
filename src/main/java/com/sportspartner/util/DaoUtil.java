package com.sportspartner.util;

import java.sql.*;

public class DaoUtil {

    public static void CloseDao (ResultSet rs, PreparedStatement stmt, Connection c)throws SQLException{
        if(rs!=null) {
            rs.close();
        }
        if(stmt!=null) {
            stmt.close();
        }
        if(c!=null) {
            c.close();
        }
    }
    public static void CloseDaoNoRs (PreparedStatement stmt, Connection c)throws SQLException{
        if(stmt!=null) {
            stmt.close();
        }
        if(c!=null) {
            c.close();
        }
    }
    public static void CloseDao (ResultSet rs, Statement stmt, Connection c)throws SQLException{
        if(rs!=null) {
            rs.close();
        }
        if(stmt!=null) {
            stmt.close();
        }
        if(c!=null) {
            c.close();
        }
    }
}
