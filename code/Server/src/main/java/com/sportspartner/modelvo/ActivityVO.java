package com.sportspartner.modelvo;

import com.sportspartner.model.Activity;
import com.sportspartner.model.ActivityComment;
import com.sportspartner.model.ActivityMember;
import com.sportspartner.model.Sport;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class ActivityVO {
    private String id;
    private String status;
    private String sportIconUUID;
    private String sportIconPath;
    private String sportName;
    private Date startTime;
    private Date endTime;
    private String facilityId;
    private int capacity;
    private int size;
    private String creatorId;
    private List <String> members;
    private String detail;
    private List <ActivityComment> discussion;

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getSportIconUUID() {
        return sportIconUUID;
    }

    public String getSportIconPath() {
        return sportIconPath;
    }

    public String getSportName() {
        return sportName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public String getFacilityId() {
        return facilityId;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getSize() {
        return size;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public List<String> getMembers() {
        return members;
    }
    public String getDetail(){
        return detail;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSportIconUUID(String sportIconUUID) {
        this.sportIconUUID = sportIconUUID;
    }

    public void setSportIconPath(String sportIconPath) {
        this.sportIconPath = sportIconPath;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public List<ActivityComment> getDiscussion() {
        return discussion;
    }

    public void setDiscussion(List<ActivityComment> discussion) {
        this.discussion = discussion;
    }

    public boolean isMissingField(){
        return this.id.equals(null);
    }

    public void setFromActivity(Activity activity){
        this.id = activity.getActivityId();
        this.status = activity.getStatus();
        this.startTime = activity.getStartTime();
        this.endTime = activity.getEndTime();
        this.facilityId = activity.getFacilityId();
        this.capacity = activity.getCapacity();
        this.size = activity.getSize();
        this.creatorId = activity.getCreatorId();
        this.detail = activity.getDescription();
    }
    public void setFromSport (Sport sport){
        this.sportName = sport.getSportName();
        this.sportIconUUID = sport.getSportIconUUID();
        this.sportIconPath = sport.getSportIconPath();
    }

    public void setFromMembers (List<ActivityMember> members, String creatorId){
        List <String> membersOutput = new ArrayList<>();
        for (ActivityMember member :members){
            String memberId = member.getUserId();
            if(!memberId.equals(creatorId)){
                membersOutput.add(memberId);
            }
        }
        this.members = membersOutput;
    }

    public void setFromComments (List<ActivityComment> comments){
        this.discussion = comments;
    }
}
