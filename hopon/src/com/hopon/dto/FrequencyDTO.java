package com.hopon.dto;

import java.util.Date;
import java.util.List;

public class FrequencyDTO {

	private List<String> frequency;
	private Date time;
	private String rideManagementId;
	private String rideSeekerId;
	private String copyDate;
	private String frequencyID;
	private String startDate;
	private String endDate;
	
	
	
	public String getCopyDate() {
		return copyDate;
	}
	public void setCopyDate(String copyDate) {
		this.copyDate = copyDate;
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
	
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getRideManagementId() {
		return rideManagementId;
	}
	public void setRideManagementId(String rideManagementId) {
		this.rideManagementId = rideManagementId;
	}
	public String getRideSeekerId() {
		return rideSeekerId;
	}
	public void setRideSeekerId(String rideSeekerId) {
		this.rideSeekerId = rideSeekerId;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
}
