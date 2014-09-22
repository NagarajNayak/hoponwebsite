package com.hopon.dto;

import java.util.Map;

public class MessageBoardDTO {
	private int messageId;
	private int submittedBy;
	private String message;
	private String messageChannel;	//A- all, S-SMS, E-Email, M-message board
	private String messageStatus; //N -new
	private int toMember;
	private String toMemberEmail;
	private int circleId;
	private String messageType; //C-cancelled, M-matched, D-done or completed
	private String createdByDt;
	private int createdBy;
	private String createdByEmail;
	private String updatedDt;
	private int updatedBy;
	private int poolRequestId; //ride id from pool_request_table
	public String emailSubject;
	public int rideId;
	private Map<String, String> attachements;
	
	public int getMessageId() {
		return messageId;
	}
	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}
	public int getSubmittedBy() {
		return submittedBy;
	}
	public void setSubmittedBy(int submittedBy) {
		this.submittedBy = submittedBy;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMessageChannel() {
		return messageChannel;
	}
	public void setMessageChannel(String messageChannel) {
		this.messageChannel = messageChannel;
	}
	public String getMessageStatus() {
		return messageStatus;
	}
	public void setMessageStatus(String messageStatus) {
		this.messageStatus = messageStatus;
	}
	public int getToMember() {
		return toMember;
	}
	public void setToMember(int toMember) {
		this.toMember = toMember;
	}
	public int getCircleId() {
		return circleId;
	}
	public void setCircleId(int circleId) {
		this.circleId = circleId;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public String getCreatedByDt() {
		return createdByDt;
	}
	public void setCreatedByDt(String createdByDt) {
		this.createdByDt = createdByDt;
	}
	public int getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	public String getUpdatedDt() {
		return updatedDt;
	}
	public void setUpdatedDt(String updatedDt) {
		this.updatedDt = updatedDt;
	}
	public int getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}
	public int getPoolRequestId() {
		return poolRequestId;
	}
	public void setPoolRequestId(int poolRequestId) {
		this.poolRequestId = poolRequestId;
	}
	public String getEmailSubject() {
		return emailSubject;
	}
	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}
	public String getToMemberEmail() {
		return toMemberEmail;
	}
	public void setToMemberEmail(String toMemberEmail) {
		this.toMemberEmail = toMemberEmail;
	}
	public String getCreatedByEmail() {
		return createdByEmail;
	}
	public void setCreatedByEmail(String createdByEmail) {
		this.createdByEmail = createdByEmail;
	}
	public int getRideId() {
		return rideId;
	}
	public void setRideId(int rideId) {
		this.rideId = rideId;
	}
	public Map<String, String> getAttachements() {
		return attachements;
	}
	public void setAttachements(Map<String, String> attachements) {
		this.attachements = attachements;
	}
	
}
