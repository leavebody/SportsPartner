package com.sportspartner.modelvo;
//
//import com.sportspartner.model.Facility;
//import com.sportspartner.model.Sport;
//
//public class FacilityOutlineVO {
//    private String facility;
//    private String facilityName;
//    private String sportId;
//    private String sportName;
//    private String sportUUID;
//    private double longitude;
//    private double latitude;
//    private String address;
//    private double score;
//
//    public FacilityOutlineVO(){}
//
//    public FacilityOutlineVO(String facility, String facilityName, String sportId, String sportName, String sportUUID, double longitude, double latitude, String address, double score) {
//        this.facility = facility;
//        this.facilityName = facilityName;
//        this.sportId = sportId;
//        this.sportName = sportName;
//        this.sportUUID = sportUUID;
//        this.longitude = longitude;
//        this.latitude = latitude;
//        this.address = address;
//        this.score = score;
//    }
//
//    public String getFacility() {
//        return facility;
//    }
//
//    public void setFacility(String facility) {
//        this.facility = facility;
//    }
//
//    public String getFacilityName() {
//        return facilityName;
//    }
//
//    public void setFacilityName(String facilityName) {
//        this.facilityName = facilityName;
//    }
//
//    public String getSportId() {
//        return sportId;
//    }
//
//    public void setSportId(String sportId) {
//        this.sportId = sportId;
//    }
//
//    public String getSportName() {
//        return sportName;
//    }
//
//    public void setSportName(String sportName) {
//        this.sportName = sportName;
//    }
//
//    public String getSportUUID() {
//        return sportUUID;
//    }
//
//    public void setSportUUID(String sportUUID) {
//        this.sportUUID = sportUUID;
//    }
//
//    public double getLongitude() {
//        return longitude;
//    }
//
//    public void setLongitude(double longitude) {
//        this.longitude = longitude;
//    }
//
//    public double getLatitude() {
//        return latitude;
//    }
//
//    public void setLatitude(double latitude) {
//        this.latitude = latitude;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public double getScore() {
//        return score;
//    }
//
//    public void setScore(double score) {
//        this.score = score;
//    }
//
//    public void setFromFacility(Facility facility){
//         this.facility = facility.getFacilityId();
//         this.facilityName = facility.getFacilityName();
//         this.sportId = facility.getSportId();
//         this.longitude = facility.getLongitude();
//         this.latitude = facility.getLatitude();
//         this.address = facility.getAddress();
//         this.score = facility.getScore();
//    }
//
//    public void setFromSport(Sport sport){
//        this.sportName = sport.getSportName();
//        this.sportUUID = sport.getSportIconUUID();
//    }
//}
