package com.hopon.dto;

import java.util.Date;

public class RideSeekerDTO {
	private String fromAddressTag;
	private String fromAddress1;
	private String fromAddressCity;
	private String fromAddressPin;
	private String fromAddressTagLimit;
	private String userName;
	private String time;
	private String toAddressTag;
	private String toAddress1;
	private String toAddressCity;
	private String toAddressPin;
	private String toAddressTagLimit;
	private String flixibleTimeSharing;
	private String favoritePlaceID;
	private String userID;
	private String seekerID;
	private Date startDate;
	private Date endDate;
	private String startdateValue;
	private String enddateValue;
	private String frequencyinweek;
	private String frequencyID;
	private String status;
	private String custom;
	private int preMatchId;
	private String rideMatchRideId;
	private int approverID;
	private boolean isSharedTaxi = false;
	private String firstApproverName;
	private String firstApproverEmailId;
	private String secondApproverName;
	private String secondApproverEmailId;

	private String viaPoint;
	private float startPointLatitude;
	private float startPointLongitude;
	private float viaPointLatitude;
	private float viaPointLongitude;
	private float endPointLatitude;
	private float endPointLongitude;

	private String startDateEarly;
	private String startDateLate;
	private String endDateEarly;
	private String endDateLate;
	private float rideDistance;
	private String rideCost;
	private String isResult;
	private String updatedDt;
	private String createdBy;
	private boolean isNightRide;
	private String recurring;
	private String subSeekers;
	public String fullDay;
	private int circleId;
	
	public String getViaPoint() {
		return viaPoint;
	}
	public void setViaPoint(String viaPoint) {
		this.viaPoint = viaPoint;
	}
	public String getStartDateLate() {
		return startDateLate;
	}
	public void setStartDateLate(String startDateLate) {
		this.startDateLate = startDateLate;
	}
	public String getEndDateLate() {
		return endDateLate;
	}
	public void setEndDateLate(String endDateLate) {
		this.endDateLate = endDateLate;
	}
	public float getRideDistance() {
		return rideDistance;
	}
	public void setRideDistance(float rideDistance) {
		this.rideDistance = rideDistance;
	}
	public String getRideCost() {
		return rideCost;
	}
	public void setRideCost(String rideCost) {
		this.rideCost = rideCost;
	}
	public String getFromAddressTag() {
		return fromAddressTag;
	}
	public void setFromAddressTag(String fromAddressTag) {
		this.fromAddressTag = fromAddressTag;
	}
	public String getFromAddress1() {
		return fromAddress1;
	}
	public void setFromAddress1(String fromAddress1) {
		this.fromAddress1 = fromAddress1;
	}
	public String getFromAddressCity() {
		return fromAddressCity;
	}
	public void setFromAddressCity(String fromAddressCity) {
		this.fromAddressCity = fromAddressCity;
	}
	public String getFromAddressPin() {
		return fromAddressPin;
	}
	public void setFromAddressPin(String fromAddressPin) {
		this.fromAddressPin = fromAddressPin;
	}
	public String getFromAddressTagLimit() {
		return fromAddressTagLimit;
	}
	public void setFromAddressTagLimit(String fromAddressTagLimit) {
		this.fromAddressTagLimit = fromAddressTagLimit;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getToAddressTag() {
		return toAddressTag;
	}
	public void setToAddressTag(String toAddressTag) {
		this.toAddressTag = toAddressTag;
	}
	public String getToAddress1() {
		return toAddress1;
	}
	public void setToAddress1(String toAddress1) {
		this.toAddress1 = toAddress1;
	}
	public String getToAddressCity() {
		return toAddressCity;
	}
	public void setToAddressCity(String toAddressCity) {
		this.toAddressCity = toAddressCity;
	}
	public String getToAddressPin() {
		return toAddressPin;
	}
	public void setToAddressPin(String toAddressPin) {
		this.toAddressPin = toAddressPin;
	}
	public String getToAddressTagLimit() {
		return toAddressTagLimit;
	}
	public void setToAddressTagLimit(String toAddressTagLimit) {
		this.toAddressTagLimit = toAddressTagLimit;
	}
	public String getFlixibleTimeSharing() {
		return flixibleTimeSharing;
	}
	public void setFlixibleTimeSharing(String flixibleTimeSharing) {
		this.flixibleTimeSharing = flixibleTimeSharing;
	}
	public String getFavoritePlaceID() {
		return favoritePlaceID;
	}
	public void setFavoritePlaceID(String favoritePlaceID) {
		this.favoritePlaceID = favoritePlaceID;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getSeekerID() {
		return seekerID;
	}
	public void setSeekerID(String seekerID) {
		this.seekerID = seekerID;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getStartdateValue() {
		return startdateValue;
	}
	public void setStartdateValue(String startdateValue) {
		this.startdateValue = startdateValue;
	}
	public String getEnddateValue() {
		return enddateValue;
	}
	public void setEnddateValue(String enddateValue) {
		this.enddateValue = enddateValue;
	}
	public String getFrequencyinweek() {
		return frequencyinweek;
	}
	public void setFrequencyinweek(String frequencyinweek) {
		this.frequencyinweek = frequencyinweek;
	}
	public String getFrequencyID() {
		return frequencyID;
	}
	public void setFrequencyID(String frequencyID) {
		this.frequencyID = frequencyID;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCustom() {
		return custom;
	}
	public void setCustom(String custom) {
		this.custom = custom;
	}
	public int getPreMatchId() {
		return preMatchId;
	}
	public void setPreMatchId(int preMatchId) {
		this.preMatchId = preMatchId;
	}
	public float getStartPointLatitude() {
		return startPointLatitude;
	}
	public void setStartPointLatitude(float startPointLatitude) {
		this.startPointLatitude = startPointLatitude;
	}
	public float getStartPointLongitude() {
		return startPointLongitude;
	}
	public void setStartPointLongitude(float startPointLongitude) {
		this.startPointLongitude = startPointLongitude;
	}
	public float getViaPointLatitude() {
		return viaPointLatitude;
	}
	public void setViaPointLatitude(float viaPointLatitude) {
		this.viaPointLatitude = viaPointLatitude;
	}
	public float getViaPointLongitude() {
		return viaPointLongitude;
	}
	public void setViaPointLongitude(float viaPointLongitude) {
		this.viaPointLongitude = viaPointLongitude;
	}
	public float getEndPointLatitude() {
		return endPointLatitude;
	}
	public void setEndPointLatitude(float endPointLatitude) {
		this.endPointLatitude = endPointLatitude;
	}
	public float getEndPointLongitude() {
		return endPointLongitude;
	}
	public void setEndPointLongitude(float endPointLongitude) {
		this.endPointLongitude = endPointLongitude;
	}
	public String getRideMatchRideId() {
		return rideMatchRideId;
	}
	public void setRideMatchRideId(String rideMatchRideId) {
		this.rideMatchRideId = rideMatchRideId;
	}
	public String getStartDateEarly() {
		return startDateEarly;
	}
	public void setStartDateEarly(String startDateEarly) {
		this.startDateEarly = startDateEarly;
	}
	public String getEndDateEarly() {
		return endDateEarly;
	}
	public void setEndDateEarly(String endDateEarly) {
		this.endDateEarly = endDateEarly;
	}
	public String getIsResult() {
		return isResult;
	}
	public void setIsResult(String isResult) {
		this.isResult = isResult;
	}
	public String getUpdatedDt() {
		return updatedDt;
	}
	public void setUpdatedDt(String updatedDt) {
		this.updatedDt = updatedDt;
	}
	public int getApproverID() {
		return approverID;
	}
	public void setApproverID(int approverID) {
		this.approverID = approverID;
	}
	public boolean isSharedTaxi() {
		return isSharedTaxi;
	}
	public void setSharedTaxi(boolean isSharedTaxi) {
		this.isSharedTaxi = isSharedTaxi;
	}
	public String getFirstApproverName() {
		return firstApproverName;
	}
	public void setFirstApproverName(String firstApproverName) {
		this.firstApproverName = firstApproverName;
	}
	public String getFirstApproverEmailId() {
		return firstApproverEmailId;
	}
	public void setFirstApproverEmailId(String firstApproverEmailId) {
		this.firstApproverEmailId = firstApproverEmailId;
	}
	public String getSecondApproverName() {
		return secondApproverName;
	}
	public void setSecondApproverName(String secondApproverName) {
		this.secondApproverName = secondApproverName;
	}
	public String getSecondApproverEmailId() {
		return secondApproverEmailId;
	}
	public void setSecondApproverEmailId(String secondApproverEmailId) {
		this.secondApproverEmailId = secondApproverEmailId;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public boolean isNightRide() {
		return isNightRide;
	}
	public void setNightRide(boolean isNightRide) {
		this.isNightRide = isNightRide;
	}
	public String getRecurring() {
		return recurring;
	}
	public void setRecurring(String recurring) {
		this.recurring = recurring;
	}
	public String getSubSeekers() {
		return subSeekers;
	}
	public void setSubSeekers(String subSeekers) {
		this.subSeekers = subSeekers;
	}
	public String getFullDay() {
		return fullDay;
	}
	public void setFullDay(String fullDay) {
		this.fullDay = fullDay;
	}
	public int getCircleId() {
		return circleId;
	}
	public void setCircleId(int circleId) {
		this.circleId = circleId;
	}		
}
