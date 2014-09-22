package com.hopon.dto;

public class PaymentTransactionDTO {
	private int paymentTxnsId;
	private int fromPayer;
	private int toPayee;
	private int poolRequestId;
	private String tripDetails;
	private String paymentDT;
	private String notes;
	private String updatedDT;
	private String updatedBy;
	private String createdDT;
	private String createdBy;
	private String dist;
	public int getPaymentTxnsId() {
		return paymentTxnsId;
	}
	public void setPaymentTxnsId(int paymentTxnsId) {
		this.paymentTxnsId = paymentTxnsId;
	}
	public int getFromPayer() {
		return fromPayer;
	}
	public void setFromPayer(int fromPayer) {
		this.fromPayer = fromPayer;
	}
	public int getToPayee() {
		return toPayee;
	}
	public void setToPayee(int toPayee) {
		this.toPayee = toPayee;
	}
	public int getPoolRequestId() {
		return poolRequestId;
	}
	public void setPoolRequestId(int poolRequestId) {
		this.poolRequestId = poolRequestId;
	}
	public String getTripDetails() {
		return tripDetails;
	}
	public void setTripDetails(String tripDetails) {
		this.tripDetails = tripDetails;
	}
	public String getPaymentDT() {
		return paymentDT;
	}
	public void setPaymentDT(String paymentDT) {
		this.paymentDT = paymentDT;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getUpdatedDT() {
		return updatedDT;
	}
	public void setUpdatedDT(String updatedDT) {
		this.updatedDT = updatedDT;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getCreatedDT() {
		return createdDT;
	}
	public void setCreatedDT(String createdDT) {
		this.createdDT = createdDT;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getDist() {
		return dist;
	}
	public void setDist(String dist) {
		this.dist = dist;
	}
}
