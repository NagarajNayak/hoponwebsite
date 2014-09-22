import javax.mail.*;

import javax.mail.internet.*;

import javax.activation.*;

/**

Author Mridul Chatterjee

 */

import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Message;

import javax.mail.MessagingException;

import javax.mail.PasswordAuthentication;

import javax.mail.Session;

import javax.mail.Transport;

import javax.mail.internet.InternetAddress;

import javax.mail.internet.MimeMessage;

import com.hopon.dto.EmailDTO;



public class SendMail {

	public static void main(String[] args) {

		Properties props = new Properties();
		
		props.put("mail.smtp.auth", EmailDTO.smtpAuth);
		props.put("mail.smtp.starttls.enable", EmailDTO.smtpStarttlsEnable);
		props.put("mail.smtp.host", EmailDTO.smtpHost);
		props.put("mail.smtp.port", EmailDTO.smtpPort);


		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(EmailDTO.username, EmailDTO.password);
				}
		});



		try {
			Message message = new MimeMessage(session);

			message.setFrom(new InternetAddress("yogesh@hopon.co.in"));

			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("kumar.yogesh.tct@gmail.com"));
			message.setSubject("Testing Subject");
			message.setText("Dear Mail Crawler," + "nn No spam to my email, please!");

			ArrayList<String> fileNames = new ArrayList<String>();
			fileNames.add("C:/Write1.txt");
			fileNames.add("C:/Write2.txt");
			
			Multipart multipart = new MimeMultipart();

			BodyPart messageBodyPart = new MimeBodyPart();

			for(int i=0;i <fileNames.size();i++)          {
				messageBodyPart = new MimeBodyPart();
				DataSource source = new FileDataSource((String)fileNames.get(i));
				messageBodyPart.setDataHandler(new DataHandler(source));
				messageBodyPart.setFileName("file" + i + ".txt");
				multipart.addBodyPart(messageBodyPart);
			}
			message.setContent(multipart); 
			
			Transport.send(message);

			System.out.println("Mail Sent Successfully….");



		} catch (MessagingException e) {

			throw new RuntimeException(e);

		}

	}

}