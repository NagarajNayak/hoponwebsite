package com.hopon.dto;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RideManagementDTO {
	private String fromAddressTag;
	private String fromAddress1;
	private String fromAddressCity;
	private String fromAddressPin;
	private String fromAddressTagLimit;
	private List<String> frequency;
	private String time;
	private String toAddressTag;
	private String toAddress1;
	private String toAddressCity;
	private String toAddressPin;
	private String toAddressTagLimit;
	private String flixibleTimeSharing;
	private String favoritePlaceID;
	private String userID;
	private String rideID;
	private String frequencyID;
	private Date startDate;
	private Date endDate;
	private String startDate1;
	private String endDate1;
	private String startdateValue;
	private String enddateValue;
	private String frequencyinweek;
	private String status;
	private String vehicleID;
	private Date flexiTimeBefore;
	private Date flexiTimeAfter;
	private String createdBy;
	private Date created_dt;
	private String seatUsed;
	private String vehicleRegno;
	private boolean automatchInCircle=true;
	private boolean isSharedTaxi = false;
	private String custom;
	private float startPointLatitude;
	private float startPointLongitude;
	private float viaPointLatitude;
	private float viaPointLongitude;
	private float endPointLatitude;
	private float endPointLongitude;
	private String viaPoint;

	private String startDateEarly;
	private String startDateLate;
	private String endDateEarly;
	private String endDateLate;
	private float rideDistance;
	private String rideCost;

	private String updatedDt;
	private int approverID;
	private String recurring;
	private String subSeekers;
	public String fullDay;
	public int circleId;
	
	public void setStartDate1(String startDate1) {
		this.startDate1 = startDate1;
	}
	public String getEndDate1() {
		return endDate1;
	}
	public void setEndDate1(String endDate1) {
		this.endDate1 = endDate1;
	}
	
	
	public String getFrequencyID() {
		return frequencyID;
	}
	public void setFrequencyID(String frequencyID) {
		this.frequencyID = frequencyID;
	}
	public List<String> getFrequency() {
		return frequency;
	}
	public void setFrequency(List<String> frequency) {
		this.frequency = frequency;
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
	public String getRideID() {
		return rideID;
	}
	public void setRideID(String rideID) {
		this.rideID = rideID;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getVehicleID() {
		return vehicleID;
	}
	public void setVehicleID(String vehicleID) {
		this.vehicleID = vehicleID;
	}
	
	public boolean isAutomatchInCircle() {
		return automatchInCircle;
	}
	public void setAutomatchInCircle(boolean automatchInCircle) {
		this.automatchInCircle = automatchInCircle;
	}
	public Date getFlexiTimeBefore() {
		return flexiTimeBefore;
	}
	public void setFlexiTimeBefore(Date flexiTimeBefore) {
		Calendar cal = Calendar.getInstance(); // creates calendar
	    cal.setTime(new Date());
		try { cal.setTime(flexiTimeBefore); } catch(NullPointerException e) {}
	    cal.add(Calendar.HOUR_OF_DAY, 0); // adds one hour
	    cal.add(Calendar.MINUTE, 30);
	    flexiTimeBefore= cal.getTime(); //
		this.flexiTimeBefore = flexiTimeBefore;
	}
	public Date getFlexiTimeAfter() {
		return flexiTimeAfter;
	}
	public void setFlexiTimeAfter(Date flexiTimeAfter) {
		Calendar cal = Calendar.getInstance(); // creates calendar
	    cal.setTime(new Date());
		try { cal.setTime(flexiTimeAfter); } catch(NullPointerException e) {}
	    cal.add(Calendar.HOUR_OF_DAY, 0); // adds one hour
	    cal.add(Calendar.MINUTE, 30);
	    flexiTimeAfter=cal.getTime();
		this.flexiTimeAfter = flexiTimeAfter;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreated_dt() {
		return created_dt;
	}
	public void setCreated_dt(Date created_dt) {
		this.created_dt = created_dt;
	}
	public String getSeatUsed() {
		return seatUsed;
	}
	public void setSeatUsed(String seatUsed) {
		this.seatUsed = seatUsed;
	}
	public String getVehicleRegno() {
		return vehicleRegno;
	}
	public void setVehicleRegno(String vehicleRegno) {
		this.vehicleRegno = vehicleRegno;
	}
	public boolean isSharedTaxi() {
		return isSharedTaxi;
	}
	public void setSharedTaxi(boolean isSharedTaxi) {
		this.isSharedTaxi = isSharedTaxi;
	}
	public String getCustom() {
		return custom;
	}
	public void setCustom(String custom) {
		this.custom = custom;
	}
	public String getStartDate1() {
		return startDate1;
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

	public String getStartDateEarly() {
		return startDateEarly;
	}
	public void setStartDateEarly(String startDateEarly) {
		this.startDateEarly = startDateEarly;
	}
	public String getStartDateLate() {
		return startDateLate;
	}
	public void setStartDateLate(String startDateLate) {
		this.startDateLate = startDateLate;
	}
	public String getEndDateEarly() {
		return endDateEarly;
	}
	public void setEndDateEarly(String endDateEarly) {
		this.endDateEarly = endDateEarly;
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
	public void setRideDistance(float distance) {
		this.rideDistance = distance;
	}
	public String getRideCost() {
		return rideCost;
	}
	public void setRideCost(String rideCost) {
		this.rideCost = rideCost;
	}
	public String getViaPoint() {
		return viaPoint;
	}
	public void setViaPoint(String viaPoint) {
		this.viaPoint = viaPoint;
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
