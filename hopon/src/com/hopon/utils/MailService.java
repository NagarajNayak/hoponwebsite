package com.hopon.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.hopon.dto.EmailDTO;

public class MailService {
	//public static void sendMail(String receiverEmailId, String subject, String emailBody) {
	public static void sendMail(EmailDTO emaildto) {		
		Properties props = new Properties();
		props.put("mail.smtp.auth", EmailDTO.smtpAuth);
		props.put("mail.smtp.starttls.enable", EmailDTO.smtpStarttlsEnable);
		props.put("mail.smtp.host", EmailDTO.smtpHost);
		props.put("mail.smtp.port", EmailDTO.smtpPort);
		
		Session session = Session.getInstance(props,
	  		  new javax.mail.Authenticator() {
	  			protected PasswordAuthentication getPasswordAuthentication() {
	  				return new PasswordAuthentication(EmailDTO.username, EmailDTO.password);
	  			}
	  		  });
		Message message = new MimeMessage(session); 
		
		try {    		
			message.setFrom(new InternetAddress(emaildto.getSenderEmailId()));
			if(!Validator.isEmail(emaildto.getReceiverEmailId())) {
				emaildto.setReceiverEmailId("info@hopon.co.in");
			}
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emaildto.getReceiverEmailId()));
			message.setSubject(emaildto.getSubject());
			if(emaildto.getEmailBody() != null) {
				message.setText(emaildto.getEmailBody());
			} else {
				message.setDataHandler(new DataHandler(new HTMLDataSource(emaildto.getEmailTemplateBody())));
			}
			
			if(emaildto.getAttachements() != null && emaildto.getAttachements().size() > 0) {
				Multipart multipart = new MimeMultipart();
	
				BodyPart messageBodyPart = new MimeBodyPart();
				for(Map.Entry<String, String> entry:emaildto.getAttachements().entrySet()) {
					entry.getKey();
					messageBodyPart = new MimeBodyPart();
					DataSource source = new FileDataSource(entry.getValue());
					messageBodyPart.setDataHandler(new DataHandler(source));
					messageBodyPart.setFileName(entry.getKey());
					multipart.addBodyPart(messageBodyPart);
				}
				message.setContent(multipart);
			}
			
			Transport.send(message);		
	    } catch (MessagingException e) {
			throw new RuntimeException(e);
		} 
    
	}
	
	static class HTMLDataSource implements DataSource {
        private String html;
 
        public HTMLDataSource(String htmlString) {
            html = htmlString;
        }
 
        // Return html string in an InputStream.
        // A new stream must be returned each time.
        public InputStream getInputStream() throws IOException {
            if (html == null) throw new IOException("Null HTML");
            return new ByteArrayInputStream(html.getBytes());
        }
 
        public OutputStream getOutputStream() throws IOException {
            throw new IOException("This DataHandler cannot write HTML");
        }
 
        public String getContentType() {
            return "text/html";
        }
 
        public String getName() {
            return "JAF text/html dataSource to send e-mail only";
        }
    }
	
}
