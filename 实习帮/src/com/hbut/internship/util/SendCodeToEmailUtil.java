package com.hbut.internship.util;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import android.content.Intent;

/**
 * 向邮箱发送验证码。获取验证码。
 */
public class SendCodeToEmailUtil {

	/**
	 * 向指定邮箱发送验证码
	 * 
	 * @param intent
	 *            上层界面传递下来的Intent，用于获取邮箱账号
	 * @param Code
	 *            随机验证码
	 * @throws Exception
	 */
	public static void sendEmail(Intent intent, String Code) throws Exception {
		String email = intent.getStringExtra("email");
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.126.com");
		props.put("mail.smtp.auth", "true");
		PopupAuthenticator auth = new PopupAuthenticator();
		Session session = Session.getInstance(props, auth);
		MimeMessage message = new MimeMessage(session);

		Address addressFrom = new InternetAddress("internshiphelper@126.com");
		Address addressTo = new InternetAddress(email);

		message.setContent("亲爱的用户：" + "   您好！感谢您使用实习帮服务，您正在进行邮箱验证，本次请求的验证码为:"
				+ "<font size='10',color=red>" + Code + "</font>"
				+ "(为了保障您账号的安全性，请在10分钟之内完成验证。)如果您没有要求绑定邮箱，请忽略此邮件。", "text/html");// 或者使用message.setText("Hello");
		message.setSubject("实习帮——找回密码验证");
		message.setFrom(addressFrom);
		message.addRecipient(Message.RecipientType.TO, addressTo);
		message.saveChanges();

		Transport transport = session.getTransport("smtp");
		transport.connect("smtp.126.com", "internshiphelper@126.com",
				"shixibang14");
		transport.send(message);
		transport.close();
	}
}

/**
 * 邮箱验证
 * 
 * @author Nie
 * 
 */
class PopupAuthenticator extends Authenticator {

	public PasswordAuthentication getPasswordAuthentication() {
		String username = "internshiphelper@126.com"; // 126邮箱登录帐号
		String pwd = "shixibang14"; // 登录密码
		return new PasswordAuthentication(username, pwd);
	}
}
