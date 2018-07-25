package com.zhaopin.uitest.util;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import com.zhaopin.uitest.base.Log;

public class EmailSender {

	public static void sendMail(String text) {
		
		try {
			Properties properties = new Properties();			
			properties.setProperty("mail.smtp.host", "172.17.3.15");//邮件服务器
			properties.setProperty("mail.smtp.port", "25");//默认端口号
			properties.setProperty("mail.smtp.auth", "true");//需要用户认证
			properties.setProperty("mail.transport.protocol", "smtp");//设置邮件协议			
			Session session = Session.getInstance(properties);			
			Message message = new MimeMessage(session);
			message.addRecipient(RecipientType.TO, new InternetAddress("469679770@qq.com"));
			message.setContent(text, "text/html;charset=UTF-8");
			message.setSubject("测试邮件");
			message.setFrom(new InternetAddress("xu.yao@zhaopin.com.cn","自动化测试组"));
			Transport transport = session.getTransport();
			transport.connect("172.17.3.15","xu.yao@zhaopin.com.cn","user_0502");
			Log.info("开始发送");
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			Log.info("发送完成");
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}
