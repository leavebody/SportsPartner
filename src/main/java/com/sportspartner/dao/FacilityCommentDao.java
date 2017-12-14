package com.sportspartner.dao;

import com.sportspartner.model.FacilityComment;

import java.sql.SQLException;
import java.util.List;

public interface FacilityCommentDao {
    public List<FacilityComment> getAllFacilityComments(String facilityId) throws SQLException;
    public boolean newFacilityComment(FacilityComment facilityComment) throws SQLException;
}
