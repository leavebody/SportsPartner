package com.sportspartner.dao;

import com.sportspartner.model.Facility;

import java.util.List;

public interface FacilityDao {
    public List<Facility> getAllFacilities();
    public List<Facility> getNearbyFacilities(double longitude_small, double longitude_large, double latitude_small, double latitude_large);
    public boolean newFacility(Facility facility);
    public boolean updateFacility(Facility facility);
    public boolean deleteFacility(String facilityId);

}