package com.sportspartner.dao.impl;

import com.sportspartner.dao.FacilityCommentDao;
import com.sportspartner.model.FacilityComment;
import com.sportspartner.model.ProfileComment;
import com.sportspartner.util.ConnectionUtil;
import com.sportspartner.util.DaoUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FacilityCommentDaoImpl implements FacilityCommentDao {
    /**
     * Get the all the ProfileComments of a user
     * @param facilityId Id of the facility
     * @return List of ProfileComment
     */
    public List<FacilityComment> getAllFacilityComments(String facilityId) throws SQLException {
        Connection c = new ConnectionUtil().connectDB();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<FacilityComment> facilityComments = new ArrayList<>();
        try {
            stmt = c.prepareStatement("SELECT * FROM \"Comment_Facility\" \" WHERE \"facilityId\"=?;");
            stmt.setString(1, facilityId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String commentId = rs.getString("commentId");
                String authorId = rs.getString("authorId");
                String providerId = rs.getString("providerId");
                Timestamp timestamp = rs.getTimestamp("time");
                java.util.Date time = new java.util.Date(timestamp.getTime());
                String content = rs.getString("content");
                facilityComments.add(new FacilityComment(facilityId, commentId, authorId, providerId, content, time));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DaoUtil.CloseDao(rs,stmt,c);
        }
        return facilityComments;
    }


    /**
     *  Create a new ProfileComment in the database
     * @param facilityComment ProfileComment Object
     * @return true if the process succeeds; false if not
     */
    public boolean newFacilityComment(FacilityComment facilityComment) throws SQLException{

        Connection c = new ConnectionUtil().connectDB();

        PreparedStatement stmt = null;
        int rs;
        String authorId = facilityComment.getAuthorId();
        String commentId = facilityComment.getCommentId();
        String facilityId = facilityComment.getFacilityId();
        Date time  = facilityComment.getTime();
        Timestamp timestamp = new Timestamp(time.getTime());
        String content = facilityComment.getContent();
        String providerId = facilityComment.getProviderId();

        boolean result = false;

        try {
            stmt = c.prepareStatement("INSERT INTO \"Comment_Facility\" (\"authorId\", \"commentId\",\"facilityId\", \"time\", \"content\", \"providerId\")"+
                    "VALUES (?, ?, ?, ?, ?, ?)");
            stmt.setString(1, authorId);
            stmt.setString(2, commentId);
            stmt.setString(3, facilityId);
            stmt.setTimestamp(4, timestamp);
            stmt.setString(5, content);
            stmt.setString(6, providerId);
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
}
