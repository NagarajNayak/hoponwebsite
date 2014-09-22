package com.hopon.dto;

public class CircleDTO {

	private int circleID;
	private String name;
	private String description;
	private String date_of_creation;
	private int circleOwner_Member_Id_P;
	private int autoEnroll_YN;
	private String Affiliations;
	private String companyID;
	private String creater;
	private String status;
	private String ownerName;
	private boolean isTaxiCircle;
	private String circleType;
	
	public int getCircleID() {
		return circleID;
	}
	public void setCircleID(int circleID) {
		this.circleID = circleID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDate_of_creation() {
		return date_of_creation;
	}
	public void setDate_of_creation(String date_of_creation) {
		this.date_of_creation = date_of_creation;
	}
	public int getCircleOwner_Member_Id_P() {
		return circleOwner_Member_Id_P;
	}
	public void setCircleOwner_Member_Id_P(int circleOwner_Member_Id_P) {
		this.circleOwner_Member_Id_P = circleOwner_Member_Id_P;
	}
	public int getAutoEnroll_YN() {
		return autoEnroll_YN;
	}
	public void setAutoEnroll_YN(int autoEnroll_YN) {
		this.autoEnroll_YN = autoEnroll_YN;
	}
	public String getAffiliations() {
		return Affiliations;
	}
	public void setAffiliations(String affiliations) {
		Affiliations = affiliations;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public boolean isTaxiCircle() {
		return isTaxiCircle;
	}
	public void setTaxiCircle(boolean isTaxiCircle) {
		this.isTaxiCircle = isTaxiCircle;
	}
	public String getCircleType() {
		return circleType;
	}
	public void setCircleType(String circleType) {
		this.circleType = circleType;
	}
	
	
}
