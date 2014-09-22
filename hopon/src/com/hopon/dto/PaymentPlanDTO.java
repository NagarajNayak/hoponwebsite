package com.hopon.dto;

import java.util.HashMap;
import java.util.Map;

public class PaymentPlanDTO {
	private int planId;
	private int circleId;
	private String circleName;
	private int userId;
	private String userName;
	private String type;
	private String plan;

	private String key1;
	private float value1;
	private String key2;
	private float value2;
	private String key3;
	private float value3;
	private String key4;
	private float value4;
	private String key5;
	private float value5;
	private Map<String, Float> charges;
	
	/*private float charge;
	private float smsIn;
	private float smsOut;
	private float minRate;
	private float maxRate;
	private float corpRate;*/
	
	private String updateBy;
	private String updateDt;
	private String createdBy;
	private String createdDt;
	public int getPlanId() {
		return planId;
	}
	public void setPlanId(int planId) {
		this.planId = planId;
	}
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
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPlan() {
		return plan;
	}
	public void setPlan(String plan) {
		this.plan = plan;
	}
	public String getKey1() {
		return key1;
	}
	public void setKey1(String key1) {
		this.key1 = key1;
	}
	public float getValue1() {
		return value1;
	}
	public void setValue1(float value1) {
		this.value1 = value1;
	}
	public String getKey2() {
		return key2;
	}
	public void setKey2(String key2) {
		this.key2 = key2;
	}
	public float getValue2() {
		return value2;
	}
	public void setValue2(float value2) {
		this.value2 = value2;
	}
	public String getKey3() {
		return key3;
	}
	public void setKey3(String key3) {
		this.key3 = key3;
	}
	public float getValue3() {
		return value3;
	}
	public void setValue3(float value3) {
		this.value3 = value3;
	}
	public String getKey4() {
		return key4;
	}
	public void setKey4(String key4) {
		this.key4 = key4;
	}
	public float getValue4() {
		return value4;
	}
	public void setValue4(float value4) {
		this.value4 = value4;
	}
	public String getKey5() {
		return key5;
	}
	public void setKey5(String key5) {
		this.key5 = key5;
	}
	public float getValue5() {
		return value5;
	}
	public void setValue5(float value5) {
		this.value5 = value5;
	}
	public Map<String, Float> getCharges() {
		return charges;
	}
	public void setCharges(Map<String, Float> charges) {
		this.charges = charges;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public String getUpdateDt() {
		return updateDt;
	}
	public void setUpdateDt(String updateDt) {
		this.updateDt = updateDt;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatedDt() {
		return createdDt;
	}
	public void setCreatedDt(String createdDt) {
		this.createdDt = createdDt;
	}
}
