package com.sportspartner.dao;

import java.util.List;
import com.sportspartner.model.ProfileComment;

public interface ProfileCommentDao {
    public List<ProfileComment> getAllProfileComments(String UserId);
    public boolean hasProfileComment(ProfileComment profileComment);
    public boolean newProfileComment(ProfileComment profileComment);
    public boolean updateProfileComment(ProfileComment profileComment);
    public boolean deleteProfileComment(ProfileComment profileComment);
}
