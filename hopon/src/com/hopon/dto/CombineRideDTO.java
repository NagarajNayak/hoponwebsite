package com.hopon.dto;

public class CombineRideDTO {
	private int userId;
	private String userName;
	private String startPoint;
	private String endPoint;
 	private String rideId;
 	private int vehicleID;
 	private String vehicleRegNo;
 	private String rideTime;
 	private int usedCapacity;
 	private int capacity;
 	private boolean drop = false;
 	private boolean take = false;
 	
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
	public String getRideId() {
		return rideId;
	}
	public void setRideId(String rideId) {
		this.rideId = rideId;
	}
	public int getVehicleID() {
		return vehicleID;
	}
	public void setVehicleID(int vehicleID) {
		this.vehicleID = vehicleID;
	}
	public String getVehicleRegNo() {
		return vehicleRegNo;
	}
	public void setVehicleRegNo(String vehicleRegNo) {
		this.vehicleRegNo = vehicleRegNo;
	}
	public String getRideTime() {
		return rideTime;
	}
	public void setRideTime(String rideTime) {
		this.rideTime = rideTime;
	}
	public int getUsedCapacity() {
		return usedCapacity;
	}
	public void setUsedCapacity(int usedCapacity) {
		this.usedCapacity = usedCapacity;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public boolean isDrop() {
		return drop;
	}
	public void setDrop(boolean drop) {
		this.drop = drop;
	}
	public boolean isTake() {
		return take;
	}
	public void setTake(boolean take) {
		this.take = take;
	}
}
