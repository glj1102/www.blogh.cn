package com.blog.util;

import java.security.Security;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;



/**
* 使用Gmail发送邮件
*
* @author Rain Chen
*/
public class GmailSender {


private String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

public String USERNAME = "";//设定邮箱的用户名

public String PASSWORD = "";//设定邮箱的密码

public String FROM = "";//设定发件件的人

public String SMTP = "";

public String PORT = "";

public GmailSender(){
	USERNAME = ConfigUtil.readValue("email_username");
	PASSWORD = ConfigUtil.readValue("email_password");
	FROM = ConfigUtil.readValue("email_from");
	SMTP = ConfigUtil.readValue("email_smtp");
	PORT = ConfigUtil.readValue("email_port");
}

public void sender(String subject, String content,String toEmail) throws Exception {
	Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
	// Get a Properties object
	Properties props = System.getProperties();
	props.setProperty("mail.smtp.starttls.enable", "true");
	props.setProperty("mail.smtp.host", SMTP);
	//qq邮箱使用SSL
	props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
	props.setProperty("mail.smtp.socketFactory.fallback", "false");
	props.setProperty("mail.smtp.port", PORT);
	props.setProperty("mail.smtp.socketFactory.port", PORT);
	props.setProperty("mail.smtp.auth", "true");
	Session session = Session.getDefaultInstance(props,
	new Authenticator() {
	protected PasswordAuthentication getPasswordAuthentication() {
	return new PasswordAuthentication(USERNAME, PASSWORD);
	}
	});

	Message msg = new MimeMessage(session);
	InternetAddress[] address = null;
	// 设定发邮件的人
	msg.setFrom(new InternetAddress(FROM));

	// 设定收信人的信箱
	address = InternetAddress.parse(toEmail, false);
	msg.setRecipients(Message.RecipientType.TO, address);

	// 设定信中的主题
	msg.setSubject(subject);

	// 设定送信的时间
	msg.setSentDate(new Date());

	Multipart mp = new MimeMultipart();
	MimeBodyPart mbp = new MimeBodyPart();

	// 设定邮件内容的类型为 text/plain 或 text/html
	mbp.setContent(content, "text/html;charset=GB2312");
	mp.addBodyPart(mbp);
	msg.setContent(mp);

	Transport transport = session.getTransport("smtp");
	transport.connect(SMTP, USERNAME, PASSWORD);
	transport.sendMessage(msg, msg.getAllRecipients());
	transport.close();
}

//	public static void main(String[] args) throws Exception {
//		GmailSender gs = new GmailSender();
//		System.out.println("Message sent."+gs.USERNAME);
//		gs.sender(
//			"测试",
//			"<html><head><meta http-equiv='Content-Type' content='text/html; charset=gb2312' /><title>hello world</title></head><body><p>恭喜你！收到邮件了！</p><p><a href='' target='_blank'>邮箱测试</a> </p></body></html>","380113060@qq.com");
//			System.out.println("Message sent."+gs.USERNAME);
//	}
}

