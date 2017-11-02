package com.sportspartner.dao.impl;

import com.sportspartner.dao.AuthorizationDao;
import com.sportspartner.model.Authorization;
import com.sportspartner.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorizationDaoImpl implements AuthorizationDao {
    @Override
    public List<Authorization> getAllAuthorizations() {
        Connection c = new ConnectionUtil().connectDB();

        List<Authorization> authorizations = new ArrayList<Authorization>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = c.createStatement();
            rs = stmt.executeQuery("SELECT * FROM \"Authorization\";");
            while (rs.next()) {
                String userId = rs.getString("userId");
                String key = rs.getString("key");

                authorizations.add(new Authorization(userId, key));
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        } finally {
            try {
                rs.close();
                stmt.close();
                c.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return authorizations;
    }

    @Override
    public boolean hasAuthorization(Authorization authorization) {
        Connection c = new ConnectionUtil().connectDB();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        boolean hasAuthorization = false;
        String userId = authorization.getUserId();
        String key = authorization.getKey();
        try {
            stmt = c.prepareStatement("SELECT * FROM \"Authorization\" WHERE \"userId\" = ? AND \"key\" = ? ;");
            stmt.setString(1, userId);
            stmt.setString(2, key);
            rs = stmt.executeQuery();
            if (rs.next()) {
                hasAuthorization = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            //System.exit(0);
        } finally {
            try {
                rs.close();
                stmt.close();
                c.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return hasAuthorization;
    }

    @Override
    public boolean newAuthorization(Authorization authorization) {
        Connection c = new ConnectionUtil().connectDB();
        PreparedStatement statement = null;
        boolean indicator = false;
        String userId = authorization.getUserId();
        String key = authorization.getKey();
        try {
            statement =c.prepareStatement("INSERT INTO \"Authorization\" (\"userId\", \"key\") VALUES (?, ?)");
            statement.setString(1, userId);
            statement.setString(2, key);
            int rs = statement.executeUpdate();

            if (rs != 0) {
                indicator = true;
            }
        }catch( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);

        }finally{
            try {
                statement.close();
                c.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return indicator;
    }

    @Override
    public boolean updateAuthorization(Authorization authorization, String newKey) {
        Connection c = new ConnectionUtil().connectDB();

        PreparedStatement stmt = null;
        int rs;
        String userId = authorization.getUserId();
        String key = authorization.getKey();

        boolean indicator = false;
        try {
            stmt = c.prepareStatement("UPDATE \"Authorization\" SET \"key\" = ?  WHERE \"userId\"=? AND \"key\" = ?;");

            stmt.setString(1, newKey);
            stmt.setString(2, userId);
            stmt.setString(3, key);
            rs = stmt.executeUpdate();
            if(rs>0)
                indicator = true;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        } finally {
            try {
                stmt.close();
                c.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return indicator;
    }

    @Override
    public boolean deleteAuthorization(Authorization authorization) {
        Connection c = new ConnectionUtil().connectDB();

        PreparedStatement stmt = null;
        int rs;
        String userId = authorization.getUserId();
        String key = authorization.getKey();

        boolean result = false;
        try {
            stmt = c.prepareStatement("DELETE FROM \"Authorization\" WHERE \"userId\"=? " + "AND \"key\" = ?;");
            stmt.setString(1, userId);
            stmt.setString(2, key);
            rs = stmt.executeUpdate();
            if(rs>0)
                result = true;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        } finally {
            try {
                stmt.close();
                c.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return result;
    }
}

