package com.sportspartner.dao;

import com.sportspartner.model.Sport;

import java.util.List;

public interface SportDao {
    public List<Sport> getAllSports();
    public Sport getSport(String sportId);
    public boolean newSport(Sport sport);
    public boolean updateSport(Sport sport);
    public boolean deleteSport(String sportId);
}
