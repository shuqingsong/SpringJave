package com.sqs.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

import java.io.UnsupportedEncodingException;
import java.util.*;

public class MailUtil {

	final Logger logger = LoggerFactory.getLogger(MailUtil.class);
    private String host;//smtp服务器
    private String port;//端口
    private String from;//发件人地址
    private String username;//用户名
    private String password;//密码(授权码)

	public boolean smtpSend(String to,String cc,String title,String content,List<String> filePath) {
		
		try {
			Properties properties = new Properties();//创建一个配置文件
			properties.setProperty("mail.smtp.host", host);//发送邮件服务器
			properties.setProperty("mail.smtp.port", port);//发送邮件服务器端口

			properties.setProperty("mail.transport.protocol", "smtp");//发送邮件协议
			properties.setProperty("mail.smtp.auth", "true");//需要验证
			properties.setProperty("mail.debug", "true");//设置debug模式

	        //QQ存在一个特性设置SSL加密
	        MailSSLSocketFactory sf = new MailSSLSocketFactory();
	        sf.setTrustAllHosts(true);
	        properties.put("mail.smtp.ssl.enable", "true");
	        properties.put("mail.smtp.ssl.socketFactory", sf);
		
			Session session = Session.getInstance(properties,
				new Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username,password);
					}
				});//创建一个session对象

			Message messgae = new MimeMessage(session);//创建邮件对象
			messgae.setFrom(new InternetAddress(this.from,"晓枫残乐"));//设置发件人
			messgae.setRecipient(Message.RecipientType.TO , new InternetAddress(to));//设置收件人
			messgae.setRecipient(Message.RecipientType.CC , new InternetAddress(cc));//设置抄送者
			messgae.setSubject(title);//设置邮件主题
//			messgae.setContent(content,"text/html;charset=utf-8");//设置邮件的内容(不带附件)
			
			Multipart multipart = new MimeMultipart();
			addExtentFile(multipart,content,filePath);//设置邮件的内容

			messgae.setContent(multipart);//设置邮件的内容
			messgae.setSentDate(new Date());//设置发邮件的时间
			messgae.saveChanges();//保存修改

			Transport tran = session.getTransport();//获取连接对象
			tran.connect(host, username, password);//连接服务器
			tran.sendMessage(messgae, messgae.getAllRecipients());//发送邮件
			tran.close();//关闭连接
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
		return true;
	}

	private void addExtentFile(Multipart multipart,String content, List<String> filePath) throws MessagingException, UnsupportedEncodingException {

		//准备文本
		BodyPart contentPart = new MimeBodyPart();
		contentPart.setContent(content,"text/html;charset=utf-8");
		multipart.addBodyPart(contentPart);
		//准备附件
		if (null!=filePath && 0<filePath.size()) {
			for (int i=0;i<filePath.size();i++){
				BodyPart mbpFile = new MimeBodyPart();
				FileDataSource fds = new FileDataSource(filePath.get(i));
				mbpFile.setDataHandler(new DataHandler(fds));
				String filename= fds.getName();
				//解决附件名称中文乱码
				mbpFile.setFileName(MimeUtility.encodeWord(filename));
				//向MimeMessage添加（Multipart代表附件）
				multipart.addBodyPart(mbpFile);
			}
		}
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}