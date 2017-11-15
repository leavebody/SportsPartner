package com.sportspartner.service;

import com.sportspartner.dao.impl.FacilityDaoImpl;
import com.sportspartner.model.Facility;
import com.sportspartner.modelvo.FacilityOutlineMapVO;
import com.sportspartner.util.JsonResponse;

import java.util.ArrayList;
import java.util.List;

public class FacilityService {
    private FacilityDaoImpl facilityDaoImpl = new FacilityDaoImpl();


    /**
     * Get all facilities for map display.
     * @return JsonResponse object.
     * @throws FacilityServiceException
     */
    public JsonResponse getAllFacilityOutlineMaps() throws FacilityServiceException {
        JsonResponse resp = new JsonResponse();
        try {
            List<FacilityOutlineMapVO> facilityOutlineMapVOs = new ArrayList<FacilityOutlineMapVO>();
            List<Facility> facilities = facilityDaoImpl.getAllFacilities();
            for (Facility facility : facilities) {
                FacilityOutlineMapVO facilityOutlineMapVO= new FacilityOutlineMapVO();
                facilityOutlineMapVO.setFromFacility(facility);
                facilityOutlineMapVOs.add(facilityOutlineMapVO);
            }
            resp.setFacilities(facilityOutlineMapVOs);
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
