package com.sportspartner.dao;

import com.sportspartner.model.Facility;

import java.util.List;

public interface FacilityDao {
    public List<Facility> getAllFacilities();
    public Facility getFacility(String facilityId);
//    public boolean newFacility(Facility facility);
//    public boolean updateFacility(Facility facility);
//    public boolean deleteFacility(String facilityId);

}