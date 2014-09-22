package com.hopon.dto;

public class MatchedTripDTO {
 private String circleId;
 private String userName;
 private String circleName;
 private String startPoint;
 private String endPoint;
 private String companyName;
 private String seekerID;
 private String vehicleID;
 private String count;
 private String frequency;
 private String startDate;
 private String rideId;
 private String countTemp;
 private String groupId;
 private String memberCount;
 private boolean selectGroup;
 private boolean recurring;
 private boolean fullDay;
 
public String getCountTemp() {
	return countTemp;
}
public void setCountTemp(String countTemp) {
	this.countTemp = countTemp;
}
public String getCircleId() {
	return circleId;
}
public void setCircleId(String circleId) {
	this.circleId = circleId;
}
public String getUserName() {
	return userName;
}
public void setUserName(String userName) {
	this.userName = userName;
}
public String getCircleName() {
	return circleName;
}
public void setCircleName(String circleName) {
	this.circleName = circleName;
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
public String getCompanyName() {
	return companyName;
}
public void setCompanyName(String companyName) {
	this.companyName = companyName;
}
public String getSeekerID() {
	return seekerID;
}
public void setSeekerID(String seekerID) {
	this.seekerID = seekerID;
}
public String getVehicleID() {
	return vehicleID;
}
public void setVehicleID(String vehicleID) {
	this.vehicleID = vehicleID;
}
public String getCount() {
	return count;
}
public void setCount(String count) {
	this.count = count;
}
public String getFrequency() {
	return frequency;
}
public void setFrequency(String frequency) {
	this.frequency = frequency;
}
public String getStartDate() {
	return startDate;
}
public void setStartDate(String startDate) {
	this.startDate = startDate;
}
public String getRideId() {
	return rideId;
}
public void setRideId(String rideId) {
	this.rideId = rideId;
}
public String getGroupId() {
	return groupId;
}
public void setGroupId(String groupId) {
	this.groupId = groupId;
}
public String getMemberCount() {
	return memberCount;
}
public void setMemberCount(String memberCount) {
	this.memberCount = memberCount;
}
public boolean isSelectGroup() {
	return selectGroup;
}
public void setSelectGroup(boolean selectGroup) {
	this.selectGroup = selectGroup;
}
public boolean isRecurring() {
	return recurring;
}
public void setRecurring(boolean recurring) {
	this.recurring = recurring;
}
public boolean isFullDay() {
	return fullDay;
}
public void setFullDay(boolean fullDay) {
	this.fullDay = fullDay;
}
}
