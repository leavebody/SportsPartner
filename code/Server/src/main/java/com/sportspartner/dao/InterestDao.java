package com.sportspartner.dao;

        import com.sportspartner.model.Interest;

        import java.sql.SQLException;
        import java.util.List;

public interface InterestDao {
    //public List<Interest> getAllInterests() throws SQLException;
    public Interest getInterest(String userId) throws SQLException;
    public boolean newInterest(Interest interest) throws SQLException;
    public boolean updateInterest(Interest interest, String sportId) throws SQLException;
    public boolean deleteInterest(Interest interest) throws SQLException;
}

