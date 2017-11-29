package com.sportspartner.dao;

import com.sportspartner.model.Sport;

import java.sql.SQLException;
import java.util.List;

public interface SportDao {
    public List<Sport> getAllSports() throws SQLException;
    public Sport getSport(String sportId) throws SQLException;
    public boolean newSport(Sport sport) throws SQLException;
//    public boolean updateSport(Sport sport) throws SQLException;
    public boolean deleteSport(String sportId) throws SQLException;
}
