package com.sportspartner.dao.impl;

import com.sportspartner.dao.ProfileCommentDao;
import com.sportspartner.model.ProfileComment;
import com.sportspartner.util.ConnectionUtil;
import com.sportspartner.util.DaoUtil;

import java.sql.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class ProfileCommentDaoImpl implements ProfileCommentDao {
    /**
     * Get the all the ProfileComments of a user
     * @param userId Id of person
     * @return List of ProfileComment
     */
    public List<ProfileComment> getAllProfileComments(String userId) throws SQLException {
        Connection c = new ConnectionUtil().connectDB();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<ProfileComment> profileComments = new ArrayList<ProfileComment>();
        try {
            stmt = c.prepareStatement("SELECT * FROM \"Comment_Profile\", \"Person\" WHERE \"Comment_Profile\".\"userId\"=? AND \"Person\".\"userId\"=?;");
            stmt.setString(1, userId);
            stmt.setString(2, userId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                userId = rs.getString("userId");
                String commentId = rs.getString("commentId");
                String authorId = rs.getString("authorId");
                String authorName = rs.getString("userName");
                Timestamp timestamp = rs.getTimestamp("time");
                Date time = new Date(timestamp.getTime());
                String content = rs.getString("content");
                profileComments.add(new ProfileComment(userId,commentId, authorId, authorName, time,content));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DaoUtil.CloseDao(rs,stmt,c);
        }
        return profileComments;
    }

    /**
     *  Jugdge whether a profileComment exists in the database
     * @param profileComment ProfileComment Object
     * @return true if there exists; false if it doesn't
     */
//    public boolean hasProfileComment(ProfileComment profileComment){
//
//        Connection c = new ConnectionUtil().connectDB();
//        PreparedStatement stmt = null;
//        ResultSet rs = null;
//
//        boolean isprofileCommentExist = false;
//        String userId = profileComment.getUserId();
//        String commentId = profileComment.getCommentId();
//        String authorId = profileComment.getAuthorId();
//        Date time = profileComment.getTime();
//        Timestamp timestamp = new Timestamp(time.getTime());
//        String content = profileComment.getContent();
//
//        try {
//            stmt = c.prepareStatement("SELECT * FROM \"Comment_Profile\" WHERE \"userId\" = ? AND \"commentId\" = ? AND \"authorId\" = ? AND \"time\" = ? AND  \"content\" = ?;");
//            stmt.setString(1, userId);
//            stmt.setString(2, commentId);
//            stmt.setString(3, authorId);
//            stmt.setTimestamp(4,timestamp);
//            stmt.setString(5,content);
//            rs = stmt.executeQuery();
//            if (rs.next()) {
//                isprofileCommentExist = true;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.err.println(e.getClass().getName() + ": " + e.getMessage());
//            //
//        } finally {
//            try {
//                rs.close();
//                stmt.close();
//                c.close();
//            } catch (SQLException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//        return isprofileCommentExist;
//
//    }

    /**
     *  Create a new ProfileComment in the database
     * @param profileComment ProfileComment Object
     * @return true if the process succeeds; false if not
     */
    public boolean newProfileComment(ProfileComment profileComment) throws SQLException{

        Connection c = new ConnectionUtil().connectDB();

        PreparedStatement stmt = null;
        int rs;
        String userId = profileComment.getUserId();
        String commentId = profileComment.getCommentId();
        String authorId = profileComment.getAuthorId();
        Date time  = profileComment.getTime();
        Timestamp timestamp = new Timestamp(time.getTime());
        String content = profileComment.getContent();

        boolean result = false;

        try {
            stmt = c.prepareStatement("INSERT INTO \"Comment_Profile\" (\"userId\", \"commentId\",\"authorId\", \"time\", \"content\")"+
                    "VALUES (?, ?, ?, ?, ?)");
            stmt.setString(1, userId);
            stmt.setString(2, commentId);
            stmt.setString(3, authorId);
            stmt.setTimestamp(4, timestamp);
            stmt.setString(5, content);
            rs = stmt.executeUpdate();
            if(rs>0)
                result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DaoUtil.CloseDaoNoRs(stmt,c);
        }
        return result;

    }
//    /**
//     *  Update a new ProfileComment in the database
//     * @param profileComment ProfileComment Object
//     * @return true if the process succeeds; false if not
//     */
//    public boolean updateProfileComment(ProfileComment profileComment){
//        Connection c = new ConnectionUtil().connectDB();
//
//        PreparedStatement stmt = null;
//        int rs;
//        String userId = profileComment.getUserId();
//        String commentId = profileComment.getCommentId();
//        String authorId = profileComment.getAuthorId();
//        Date time  = profileComment.getTime();
//        Timestamp timestamp = new Timestamp(time.getTime());
//        String content = profileComment.getContent();
//
//        boolean result = false;
//        try {
//            stmt = c.prepareStatement("UPDATE \"Comment_Profile\" SET \"authorId\" = ? , \"time\" = ? , \"content\" = ? WHERE \"userId\"=? AND \"commentId\" = ?;");
//            stmt.setString(1, authorId);
//            stmt.setTimestamp(2, timestamp);
//            stmt.setString(3, content);
//            stmt.setString(4, userId);
//            stmt.setString(5, commentId);
//            rs = stmt.executeUpdate();
//            if(rs>0)
//                result = true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.err.println(e.getClass().getName() + ": " + e.getMessage());
//
//        } finally {
//            try {
//                stmt.close();
//                c.close();
//            } catch (SQLException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//        return result;
//    }
//    /**
//     *  Delete a new ProfileComment in the database
//     * @param profileComment ProfileComment Object
//     * @return true if the process succeeds; false if not
//     */
//    public boolean deleteProfileComment(ProfileComment profileComment){
//        Connection c = new ConnectionUtil().connectDB();
//
//        PreparedStatement stmt = null;
//        int rs;
//        String userId = profileComment.getUserId();
//        String commentId = profileComment.getCommentId();
//        String authorId = profileComment.getAuthorId();
//        Date time  = profileComment.getTime();
//        Timestamp timestamp = new Timestamp(time.getTime());
//        String content = profileComment.getContent();
//        boolean result = false;
//
//        try {
//            stmt = c.prepareStatement("DELETE FROM \"Comment_Profile\" WHERE \"userId\"=? " +
//                    "AND \"commentId\" = ? AND \"authorId\" = ? AND \"time\" = ? AND  \"content\" = ?;");
//            stmt.setString(1, userId);
//            stmt.setString(2, commentId);
//            stmt.setString(3, authorId);
//            stmt.setTimestamp(4, timestamp);
//            stmt.setString(5, content);
//            rs = stmt.executeUpdate();
//            if(rs>0){
//                result = true;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.err.println(e.getClass().getName() + ": " + e.getMessage());
//
//        } finally {
//            try {
//                stmt.close();
//                c.close();
//            } catch (SQLException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//        return result;
//    }
//
//    public int countProfileComment(String userId){
//        Connection c = new ConnectionUtil().connectDB();
//        PreparedStatement stmt = null;
//        ResultSet rs = null;
//        int count = 0;
//
//        try {
//            stmt = c.prepareStatement("SELECT COUNT(\"commentId\") AS \"count\" FROM \"Comment_Profile\" WHERE \"userId\"=?;");
//            stmt.setString(1, userId);
//            rs = stmt.executeQuery();
//            if(rs.next()){
//                count = rs.getInt("count");
//            }
//        } catch (Exception e) {
//            System.err.println(e.getClass().getName() + ": " + e.getMessage());
//
//        } finally {
//            try {
//                rs.close();
//                stmt.close();
//                c.close();
//            } catch (SQLException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//        return count;
//    }

}