package com.sportspartner.service;

import com.sportspartner.dao.ActivityDao;
import com.sportspartner.dao.FacilityCommentDao;
import com.sportspartner.dao.SportDao;
import com.sportspartner.dao.impl.ActivityDaoImpl;
import com.sportspartner.dao.impl.FacilityCommentDaoImpl;
import com.sportspartner.dao.impl.FacilityDaoImpl;
import com.sportspartner.dao.impl.SportDaoImpl;
import com.sportspartner.model.Facility;
import com.sportspartner.model.FacilityComment;
import com.sportspartner.model.ProfileComment;
import com.sportspartner.model.Sport;
import com.sportspartner.modelvo.FacilityMarkerVO;
import com.sportspartner.modelvo.FacilityOutlineVO;
import com.sportspartner.modelvo.FacilityReviewVO;
import com.sportspartner.modelvo.FacilityVO;
import com.sportspartner.util.JsonResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class FacilityService {
    private FacilityDaoImpl facilityDaoImpl = new FacilityDaoImpl();
    private FacilityCommentDao facilityCommentDao = new FacilityCommentDaoImpl();
    private SportDao sportDao = new SportDaoImpl();

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

    public JsonResponse getFacilityOutline(String facilityId) throws Exception {
        JsonResponse resp = new JsonResponse();

        Facility facility = facilityDaoImpl.getFacility(facilityId);
        if (facility==null){
            return new JsonResponse("no such facility: "+facilityId);
        }
        FacilityOutlineVO facilityOutlineVO = new FacilityOutlineVO();
        facilityOutlineVO.setFromFacility(facility);

        Sport sport = sportDao.getSport(facility.getSportId());
        facilityOutlineVO.setFromSport(sport);

        resp.setResponse("true");
        resp.setFacilityOutline(facilityOutlineVO);
        return resp;
    }

    public JsonResponse reviewFacility(FacilityReviewVO facilityReviewVO) throws Exception {
        String facilityId = facilityReviewVO.getReviewee();

        Facility facility = facilityDaoImpl.getFacility(facilityId);
        if (facility == null){
            return new JsonResponse("false", "no such facility: "+facilityId);
        }
        double scoreBefore = facility.getScore();
        int countBefore = facility.getScoreCount();
        facility.setScore((scoreBefore*countBefore+facilityReviewVO.getScore())/(countBefore+1));
        facility.setScoreCount(countBefore+1);
        facilityDaoImpl.updateFacility(facility);

        FacilityComment facilityComment = new FacilityComment();

        facilityComment.setAuthorId(facilityReviewVO.getReviewer());
        facilityComment.setCommentId(UUID.randomUUID().toString());
        facilityComment.setContent(facilityReviewVO.getComments());
        facilityComment.setFacilityId(facilityId);
        facilityComment.setProviderId(facility.getProviderId());
        facilityComment.setTime(new Date());
        facilityCommentDao.newFacilityComment(facilityComment);

        return new JsonResponse(true);
    }
}
