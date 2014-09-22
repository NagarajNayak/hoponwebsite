package com.hopon.dto;

import java.util.Date;

public class RideBillingReportDTO implements Comparable<RideBillingReportDTO> {
	private int circleId;
	private String circleName;
	private int circleOwnerUserId;
	private String circleOwnerUserEmailId;
	private String circleOwnerUserName;	
	private int requestUserId;
	private String requestUserEmailId;
	private String requestUserName;
	private int giverUserId;
	private String giverUserEmailId;
	private String giverUserName;
	private int rideSeekerId;
	private String startPoint;
	private String endPoint;
	private float rideCost;
	private float rideDistance;
	private Date rideDate;
	private Date requestDate;
	private int rideGiverId;
	private boolean fullDay;
	public int getCircleId() {
		return circleId;
	}
	public void setCircleId(int circleId) {
		this.circleId = circleId;
	}
	public String getCircleName() {
		return circleName;
	}
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}
	public int getCircleOwnerUserId() {
		return circleOwnerUserId;
	}
	public void setCircleOwnerUserId(int circleOwnerUserId) {
		this.circleOwnerUserId = circleOwnerUserId;
	}
	public String getCircleOwnerUserEmailId() {
		return circleOwnerUserEmailId;
	}
	public void setCircleOwnerUserEmailId(String circleOwnerUserEmailId) {
		this.circleOwnerUserEmailId = circleOwnerUserEmailId;
	}
	public String getCircleOwnerUserName() {
		return circleOwnerUserName;
	}
	public void setCircleOwnerUserName(String circleOwnerUserName) {
		this.circleOwnerUserName = circleOwnerUserName;
	}
	public int getRequestUserId() {
		return requestUserId;
	}
	public void setRequestUserId(int requestUserId) {
		this.requestUserId = requestUserId;
	}
	public String getRequestUserEmailId() {
		return requestUserEmailId;
	}
	public void setRequestUserEmailId(String requestUserEmailId) {
		this.requestUserEmailId = requestUserEmailId;
	}
	public String getRequestUserName() {
		return requestUserName;
	}
	public void setRequestUserName(String requestUserName) {
		this.requestUserName = requestUserName;
	}
	public int getGiverUserId() {
		return giverUserId;
	}
	public void setGiverUserId(int giverUserId) {
		this.giverUserId = giverUserId;
	}
	public String getGiverUserEmailId() {
		return giverUserEmailId;
	}
	public void setGiverUserEmailId(String giverUserEmailId) {
		this.giverUserEmailId = giverUserEmailId;
	}
	public String getGiverUserName() {
		return giverUserName;
	}
	public void setGiverUserName(String giverUserName) {
		this.giverUserName = giverUserName;
	}
	public int getRideSeekerId() {
		return rideSeekerId;
	}
	public void setRideSeekerId(int rideSeekerId) {
		this.rideSeekerId = rideSeekerId;
	}
	public String getStartPoint() {
		return startPoint;
	}
	public void setStartPoint(String startPoint) {
		this.startPoint = startPoint;
	}
	public String getEndPoint() {
		return endPoint;
	}
	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}
	public float getRideCost() {
		return rideCost;
	}
	public void setRideCost(float rideCost) {
		this.rideCost = rideCost;
	}
	public float getRideDistance() {
		return rideDistance;
	}
	public void setRideDistance(float rideDistance) {
		this.rideDistance = rideDistance;
	}
	public Date getRideDate() {
		return rideDate;
	}
	public void setRideDate(Date rideDate) {
		this.rideDate = rideDate;
	}
	public Date getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
	public int getRideGiverId() {
		return rideGiverId;
	}
	public void setRideGiverId(int rideGiverId) {
		this.rideGiverId = rideGiverId;
	}
	public boolean isFullDay() {
		return fullDay;
	}
	public void setFullDay(boolean fullDay) {
		this.fullDay = fullDay;
	}
	@Override
	public int compareTo(RideBillingReportDTO obj) {
		if(this.getCircleId() == obj.getCircleId()) return this.getCircleId() - obj.getCircleId();
		return this.getRideDate().compareTo(obj.getRideDate());
	}
}
