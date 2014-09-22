package com.hopon.dto;

public class ContactusDTO {

	private String first_name;
	private String last_name;
	private String mobile_no;
	private String email_id;
	private String comment;
	
	public ContactusDTO() {
	}
	public ContactusDTO(String first_name, String last_name, String mobile_no,
			String email_id, String comment) {
		this.first_name = first_name;
		this.last_name = last_name;
		this.mobile_no = mobile_no;
		this.email_id = email_id;
		this.comment = comment;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getMobile_no() {
		return mobile_no;
	}
	public void setMobile_no(String mobile_no) {
		this.mobile_no = mobile_no;
	}
	public String getEmail_id() {
		return email_id;
	}
	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	@Override
	public String toString() {
		return "ContactusDTO [first_name=" + first_name + ", last_name="
				+ last_name + ", mobile_no=" + mobile_no + ", email_id="
				+ email_id + ", comment=" + comment + "]";
	}

}
