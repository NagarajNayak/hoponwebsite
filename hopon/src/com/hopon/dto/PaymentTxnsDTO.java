package com.hopon.dto;

public class PaymentTxnsDTO {
	private int paymentTxnsId;
	private float amount;
	private int FromPayer;
	private int ToPayee;
	private int seekerId;
	private String TripDetails;
	private String PaymentDT;
	private String Notes;
	private String updateDate;
	private int updatedBy;
	private String createdDate;
	private int createdBy;
	private float Dist;
	public int getPaymentTxnsId() {
		return paymentTxnsId;
	}
	public void setPaymentTxnsId(int paymentTxnsId) {
		this.paymentTxnsId = paymentTxnsId;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public int getFromPayer() {
		return FromPayer;
	}
	public void setFromPayer(int fromPayer) {
		FromPayer = fromPayer;
	}
	public int getToPayee() {
		return ToPayee;
	}
	public void setToPayee(int toPayee) {
		ToPayee = toPayee;
	}
	public int getSeekerId() {
		return seekerId;
	}
	public void setSeekerId(int seekerId) {
		this.seekerId = seekerId;
	}
	public String getTripDetails() {
		return TripDetails;
	}
	public void setTripDetails(String tripDetails) {
		TripDetails = tripDetails;
	}
	public String getPaymentDT() {
		return PaymentDT;
	}
	public void setPaymentDT(String paymentDT) {
		PaymentDT = paymentDT;
	}
	public String getNotes() {
		return Notes;
	}
	public void setNotes(String notes) {
		Notes = notes;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public int getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public int getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	public float getDist() {
		return Dist;
	}
	public void setDist(float dist) {
		Dist = dist;
	}	
}
