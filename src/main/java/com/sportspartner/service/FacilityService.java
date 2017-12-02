package com.sportspartner.service;

import com.sportspartner.dao.impl.FacilityDaoImpl;
import com.sportspartner.model.Facility;
import com.sportspartner.modelvo.FacilityMarkerVO;
import com.sportspartner.util.JsonResponse;

import java.util.ArrayList;
import java.util.List;

public class FacilityService {
    private FacilityDaoImpl facilityDaoImpl = new FacilityDaoImpl();


    public JsonResponse getFacilityMarkers(double longitude_small, double longitude_large, double latitude_small, double latitude_large) throws Exception {
        JsonResponse resp = new JsonResponse();

        List<FacilityMarkerVO> facilityMarkerVOs = new ArrayList<FacilityMarkerVO>();
        List<Facility> facilities = facilityDaoImpl.getNearbyFacilities(longitude_small, longitude_large, latitude_small, latitude_large);
        for (Facility facility : facilities) {
            FacilityMarkerVO facilityMarkerVO = new FacilityMarkerVO();
            facilityMarkerVO.setFromFacility(facility);
            facilityMarkerVOs.add(facilityMarkerVO);
        }
        resp.setFacilities(facilityMarkerVOs);
        resp.setResponse("true");
        return resp;
    }
}
