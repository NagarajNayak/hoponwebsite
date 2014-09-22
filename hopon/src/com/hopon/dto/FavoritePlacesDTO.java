package com.hopon.dto;

public class FavoritePlacesDTO {
private String ID;
private String userID;
private String rideID;
private String address;
private String city;
private String pin;
private float latitude;
private float longitude;
private String tagtype;
private String boundtype;

public String getID() {
	return ID;
}
public void setID(String iD) {
	ID = iD;
}
public String getUserID() {
	return userID;
}
public void setUserID(String userID) {
	this.userID = userID;
}
public String getRideID() {
	return rideID;
}
public void setRideID(String rideID) {
	this.rideID = rideID;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public String getCity() {
	return city;
}
public void setCity(String city) {
	this.city = city;
}
public String getPin() {
	return pin;
}
public void setPin(String pin) {
	this.pin = pin;
}
public float getLatitude() {
	return latitude;
}
public void setLatitude(float latitude) {
	this.latitude = latitude;
}
public float getLongitude() {
	return longitude;
}
public void setLongitude(float longitude) {
	this.longitude = longitude;
}
public String getTagtype() {
	return tagtype;
}
public void setTagtype(String tagtype) {
	this.tagtype = tagtype;
}
public String getBoundtype() {
	return boundtype;
}
public void setBoundtype(String boundtype) {
	this.boundtype = boundtype;
}

}
