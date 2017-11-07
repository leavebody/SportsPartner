package com.sportspartner.dao.impl;

import com.sportspartner.dao.FriendDao;
import com.sportspartner.model.User;
import com.sportspartner.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;

public class FriendDaoImpl implements FriendDao {

    public FriendDaoImpl(){}

    /**
     * Get all friends of a user.
     * @param user the userId of the user
     * @return a list of User objects for all friends of the user
     */
    public List<User> getAllFriends(String user){

        Connection c = new ConnectionUtil().connectDB();
        List<User> users = new ArrayList<User>();
        ResultSet rs = null;
        PreparedStatement statement;
        try {
            statement =c.prepareStatement("SELECT * from \"Friend\" WHERE \"userId\" = ? OR \"friendId\" = ? ");
            statement.setString(1, user);
            statement.setString(2, user);
            rs = statement.executeQuery();


            while (rs.next()) {
                String userId = rs.getString("userId");
                String friendId = rs.getString("friendId");
                if(userId.equals(user)) {
                    users.add(new UserDaoImpl().getUser(friendId));
                }
                else{
                    users.add(new UserDaoImpl().getUser(userId));
                }
            }
        }catch( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }finally{
            try {
                rs.close();
                c.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return users;
    }

    /**
     * Check whether user1 has user2 as a friend.
     * @param user1
     * @param user2
     * @return "true" or "false" for whether user2 is a friend of user1
     */
    public boolean getFriend(String user1, String user2){
        Connection c = new ConnectionUtil().connectDB();
        ResultSet rs = null;
        PreparedStatement statement = null;
        boolean indicator = false;
        try {
            statement =c.prepareStatement("SELECT * from \"Friend\" WHERE \"userId\" = ? AND \"friendId\" = ?",ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            statement.setString(1, user1);
            statement.setString(2, user2);
            rs = statement.executeQuery();
            if (rs.next()) {
                indicator = true;
            }
        }catch( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);

        }finally{
            try {
                rs.close();
                c.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return indicator;
    }

    /**
     * Add a new friend user2 to user1's friend list.
     * @param user1
     * @param user2
     * @return "true" or "false" for whether the user2 has been added to the user1's list
     */
    public boolean newFriend(String user1, String user2){
        Connection c = new ConnectionUtil().connectDB();
        PreparedStatement statement = null;
        boolean indicator = false;
        try {
            statement =c.prepareStatement("INSERT INTO \"Friend\" (\"userId\", \"friendId\") VALUES (?, ?)");
            statement.setString(1, user1);
            statement.setString(2, user2);
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

    /**
     * Delete a friend user2 from user1's friend list.
     * @param user1
     * @param user2
     * @return "ture" or "false" for whether successfully deleted the friend.
     */
    public boolean deleteFriend(String user1, String user2){
        Connection c = new ConnectionUtil().connectDB();
        PreparedStatement statement;
        boolean indicator = false;
        try {
            statement =c.prepareStatement("DELETE FROM \"Friend\" WHERE \"userId\" = ? AND \"friendId\" = ?");
            statement.setString(1, user1);
            statement.setString(2, user2);
            int rs = statement.executeUpdate();

            if (rs != 0) {
                indicator = true;
            }
        }catch( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);

        }finally{
            try {
                c.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return indicator;
    }

}
