package fr.dauphine.mido.as.projet.mail;

import java.util.Date;
import java.util.Properties;
 
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {
	
	public void sendMail(String from, String to, String subject, String content) {
		InternetAddress[] distributionList;
		try {
			distributionList = InternetAddress.parse(to,false);
			Properties props = new Properties();
			props.put("mail.smtp.host", "localhost");
			props.put("mail.smtp.port", "25");
			Session session = Session.getDefaultInstance(props, null);
			session.setDebug(false);
			
			Message msg = new MimeMessage(session);			
			msg.setContent(content, "text/html; charset=utf-8");
			msg.setFrom(new InternetAddress(from));
			msg.setRecipients(Message.RecipientType.TO, distributionList);
			msg.setSubject(subject);
			msg.setSentDate(new Date());
			Transport.send(msg);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
