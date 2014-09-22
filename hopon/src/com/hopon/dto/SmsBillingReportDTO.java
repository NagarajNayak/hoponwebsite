package com.hopon.dto;

import java.util.Date;

public class SmsBillingReportDTO implements Comparable<SmsBillingReportDTO> {
	private int circleId;
	private String circleName;
	private int circleOwnerUserId;
	private String circleOwnerUserEmailId;
	private String circleOwnerUserName;	
	private int requestUserId;
	private String requestUserEmailId;
	private String requestUserName;
	private int smsId;
	private int messageId;
	private String messageToNumber;
	private Date sendDate;
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
	public int getSmsId() {
		return smsId;
	}
	public void setSmsId(int smsId) {
		this.smsId = smsId;
	}
	public int getMessageId() {
		return messageId;
	}
	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}
	public String getMessageToNumber() {
		return messageToNumber;
	}
	public void setMessageToNumber(String messageToNumber) {
		this.messageToNumber = messageToNumber;
	}
	public Date getSendDate() {
		return sendDate;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	@Override
	public int compareTo(SmsBillingReportDTO obj) {
		if(this.getCircleId() == obj.getCircleId()) return this.getCircleId() - obj.getCircleId();
		return this.getSendDate().compareTo(obj.getSendDate());
	}
}
