package com.hopon.dto;

public class ManageRideFormDTO {
	private String from;
	private String to;
	private String rideDate;
	private int myCircleId;
	private int affiliatedCircleId;
	private String rideOption;
	private int userId;
	private String userName;
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getRideDate() {
		return rideDate;
	}
	public void setRideDate(String rideDate) {
		this.rideDate = rideDate;
	}
	public int getMyCircleId() {
		return myCircleId;
	}
	public void setMyCircleId(int myCircleId) {
		this.myCircleId = myCircleId;
	}
	public int getAffiliatedCircleId() {
		return affiliatedCircleId;
	}
	public void setAffiliatedCircleId(int affiliatedCircleId) {
		this.affiliatedCircleId = affiliatedCircleId;
	}
	public String getRideOption() {
		return rideOption;
	}
	public void setRideOption(String rideOption) {
		this.rideOption = rideOption;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
