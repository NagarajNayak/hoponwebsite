package com.hopon.dto;

public class SmsReplyDTO {
	private int id;
	private String smsSid;
	private String fromNumber;
	private String toNumber;
	private String body;
	private String exotelDate;
	private String createdDate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSmsSid() {
		return smsSid;
	}
	public void setSmsSid(String smsSid) {
		this.smsSid = smsSid;
	}
	public String getFromNumber() {
		return fromNumber;
	}
	public void setFromNumber(String fromNumber) {
		this.fromNumber = fromNumber;
	}
	public String getToNumber() {
		return toNumber;
	}
	public void setToNumber(String toNumber) {
		this.toNumber = toNumber;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getExotelDate() {
		return exotelDate;
	}
	public void setExotelDate(String exotelDate) {
		this.exotelDate = exotelDate;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	
}
