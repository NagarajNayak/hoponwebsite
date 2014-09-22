package com.hopon.dto;

public class HoponAccountDTO {
	private int idHoponAccounts;
	private String name;
	private float balance;
	private String description;
	private String updateDate;
	private int updatedBy;
	private String createdDate;
	private int createdBy;
	public int getIdHoponAccounts() {
		return idHoponAccounts;
	}
	public void setIdHoponAccounts(int idHoponAccounts) {
		this.idHoponAccounts = idHoponAccounts;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getBalance() {
		return balance;
	}
	public void setBalance(float balance) {
		this.balance = balance;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
}
