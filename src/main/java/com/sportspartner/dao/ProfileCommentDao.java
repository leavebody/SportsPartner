package com.sportspartner.dao;

import java.sql.SQLException;
import java.util.List;
import com.sportspartner.model.ProfileComment;

public interface ProfileCommentDao {
    public List<ProfileComment> getAllProfileComments(String UserId) throws SQLException;
//    public boolean hasProfileComment(ProfileComment profileComment) throws SQLException;
    public boolean newProfileComment(ProfileComment profileComment) throws SQLException;
//    public boolean updateProfileComment(ProfileComment profileComment) throws SQLException;
//    public boolean deleteProfileComment(ProfileComment profileComment) throws SQLException;
}
