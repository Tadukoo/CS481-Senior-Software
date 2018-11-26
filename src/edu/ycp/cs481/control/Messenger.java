package edu.ycp.cs481.control;

import java.util.*; 
import javax.mail.*; 
import javax.mail.PasswordAuthentication;
import javax.mail.internet.*;


public class Messenger {
	
	public static void send(String email, String subject, String text) {
		final String username = "ctm.mkii@gmail.com";
		final String password = "cS4B1SrS0ft!";
		
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(username, password);
			}
		  });
	
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress("mailTester481@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
			InternetAddress.parse(email));
			message.setSubject(subject);
			message.setText(text, "UTF-8", "html");
			Transport.send(message);
			System.out.println("Message Sent");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
