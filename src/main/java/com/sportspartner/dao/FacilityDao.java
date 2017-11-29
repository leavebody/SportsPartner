package com.sportspartner.dao;

import com.sportspartner.model.Facility;

import java.sql.SQLException;
import java.util.List;

public interface FacilityDao {
//    public List<Facility> getAllFacilities();
    public List<Facility> getNearbyFacilities(double longitude_small, double longitude_large, double latitude_small, double latitude_large) throws SQLException;
    public boolean newFacility(Facility facility) throws SQLException;
//    public boolean updateFacility(Facility facility) throws SQLException;
    public boolean deleteFacility(String facilityId) throws SQLException;

}