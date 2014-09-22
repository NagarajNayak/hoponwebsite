package com.hopon.dto;

public class ManageRideDTO {
	private int rideId;
	private int requestId;
	private String role;
	private String name;
	private String gender;
	private String from;
	private String to;
	private String rideTime;
	private String rideRating;
	private String rideOption;
	
	public int getRideId() {
		return rideId;
	}
	public void setRideId(int rideId) {
		this.rideId = rideId;
	}
	public int getRequestId() {
		return requestId;
	}
	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
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
	public String getRideTime() {
		return rideTime;
	}
	public void setRideTime(String rideTime) {
		this.rideTime = rideTime;
	}
	public String getRideRating() {
		return rideRating;
	}
	public void setRideRating(String rideRating) {
		this.rideRating = rideRating;
	}
	public String getRideOption() {
		return rideOption;
	}
	public void setRideOption(String rideOption) {
		this.rideOption = rideOption;
	}
	
}