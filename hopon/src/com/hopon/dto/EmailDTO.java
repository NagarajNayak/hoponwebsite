package com.hopon.dto;

import java.util.Map;

public class EmailDTO {
	public final static String username = "info@hopon.co.in";
	public final static String password = "Creshya123";
	public final static String smtpAuth = "true";
	public final static String smtpStarttlsEnable = "true";
	
	public final static String smtpHost = "smtpout.secureserver.net";
	public final static String smtpPort = "80";	
	
	//imap.secureserver.net:143	
	//smtpout.secureserver.net:80
	private String senderEmailId;
	private String receiverEmailId;
	private String subject;
	private String emailBody;
	private String emailTemplateBody;
	private Map<String, String> attachements;
	
	public String getSenderEmailId() {
		if(senderEmailId == null) return username;
		return senderEmailId;
	}
	public void setSenderEmailId(String senderEmailId) {
		this.senderEmailId = senderEmailId;
	}
	public String getReceiverEmailId() {
		return receiverEmailId;
	}
	public void setReceiverEmailId(String receiverEmailId) {
		this.receiverEmailId = receiverEmailId;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getEmailBody() {
		return emailBody;
	}
	public void setEmailBody(String emailBody) {
		this.emailBody = emailBody;
	}
	public String getEmailTemplateBody() {
		return emailTemplateBody;
	}
	public void setEmailTemplateBody(String emailTemplateBody) {
		this.emailTemplateBody = emailTemplateBody;
	}
	public static String getUsername() {
		return username;
	}
	public static String getPassword() {
		return password;
	}
	public static String getSmtpauth() {
		return smtpAuth;
	}
	public static String getSmtpstarttlsenable() {
		return smtpStarttlsEnable;
	}
	public static String getSmtphost() {
		return smtpHost;
	}
	public static String getSmtpport() {
		return smtpPort;
	}
	public Map<String, String> getAttachements() {
		return attachements;
	}
	public void setAttachements(Map<String, String> attachements) {
		this.attachements = attachements;
	}
	
}
