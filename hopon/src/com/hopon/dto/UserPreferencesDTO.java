package com.hopon.dto;

public class UserPreferencesDTO {
	private int preferenceId;
	private int userId;
	private String gender;
	private int maxWaitTime;
	private String facebookId = "false";
	private String twitterId = "false";
	private String linkedinId = "false";
	private String googleplusId = "false";
	private String currentDate;
	private String updatedDate;
	public int getPreferenceId() {
		return preferenceId;
	}
	public void setPreferenceId(int preferenceId) {
		this.preferenceId = preferenceId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getMaxWaitTime() {
		return maxWaitTime;
	}
	public void setMaxWaitTime(int maxWaitTime) {
		this.maxWaitTime = maxWaitTime;
	}
	public String getFacebookId() {
		return facebookId;
	}
	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}
	public String getTwitterId() {
		return twitterId;
	}
	public void setTwitterId(String twitterId) {
		this.twitterId = twitterId;
	}
	public String getLinkedinId() {
		return linkedinId;
	}
	public void setLinkedinId(String linkedinId) {
		this.linkedinId = linkedinId;
	}
	public String getGoogleplusId() {
		return googleplusId;
	}
	public void setGoogleplusId(String googleplusId) {
		this.googleplusId = googleplusId;
	}
	public String getCurrentDate() {
		return currentDate;
	}
	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}
	public String getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}
	
}
