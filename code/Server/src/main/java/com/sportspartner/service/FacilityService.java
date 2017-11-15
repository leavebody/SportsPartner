package com.sportspartner.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sportspartner.dao.impl.FacilityDaoImpl;
import com.sportspartner.model.Facility;
import com.sportspartner.modelvo.FacilityMarkerVO;
import com.sportspartner.util.JsonResponse;

import java.util.ArrayList;
import java.util.List;

public class FacilityService {
    private FacilityDaoImpl facilityDaoImpl = new FacilityDaoImpl();




    public JsonResponse getFacilityMarkers(double longitude_small, double longitude_large, double latitude_small, double latitude_large) throws FacilityServiceException {
        JsonResponse resp = new JsonResponse();
        try {
            List<FacilityMarkerVO> facilityMarkerVOs = new ArrayList<FacilityMarkerVO>();
            List<Facility> facilities = facilityDaoImpl.getNearbyFacilities(longitude_small, longitude_large, latitude_small, latitude_large);
            for (Facility facility : facilities) {
                FacilityMarkerVO facilityMarkerVO = new FacilityMarkerVO();
                facilityMarkerVO.setFromFacility(facility);
                facilityMarkerVOs.add(facilityMarkerVO);
            }
            resp.setFacilities(facilityMarkerVOs);
            resp.setResponse("true");
        } catch (Exception ex) {
            throw new FacilityServiceException("Facility Service getAllFacilityOutlineMaps Exception", ex);
        }
        return resp;
    }


    /**
     *  Exception class for Facility
     */
    public static class FacilityServiceException extends Exception {
        public FacilityServiceException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
