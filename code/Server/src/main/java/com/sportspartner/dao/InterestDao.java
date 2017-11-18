package com.sportspartner.dao;

        import com.sportspartner.model.Interest;

        import java.util.List;

public interface InterestDao {
    //public List<Interest> getAllInterests();
    public Interest getInterest(String userId);
    public boolean newInterest(Interest interest);
    public boolean updateInterest(Interest interest, String sportId);
    public boolean deleteInterest(Interest interest);
}

