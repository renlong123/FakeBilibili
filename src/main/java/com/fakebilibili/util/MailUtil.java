package com.fakebilibili.util;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class MailUtil {

    static Properties properties;
    static Message msg;
    static Transport transport;
    //初始化Mail信息
    public MailUtil(){
        properties = new Properties();

        properties.setProperty("mail.debug", "true");//调试模式发送
        properties.setProperty("mail.smtp.auth", "true");//身份验证设置
        properties.setProperty("mail.host", "smtp.qq.com");//发件人邮箱主机名
        properties.setProperty("mail.transport.protocol", "smtp");//发件协议
        properties.setProperty("mail.smtp.ssl.enable", "true");//qq邮箱的SSL加密

        Session session = Session.getInstance(properties);

        msg = new MimeMessage(session);

        try {
            msg.setSubject("FakeBilibili网站验证邮件");
            msg.setFrom(new InternetAddress("1720476708@qq.com"));//设置发件人

            transport = session.getTransport();
            transport.connect("1720476708@qq.com", "solbvabkucameied");//设置发件人在该邮箱主机上的用户名密码
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    /**
     * 得到邮箱地址邮箱内容发送。
     * @param 邮箱地址
     * @param 邮箱内容
     * @throws AddressException
     * @throws MessagingException
     */

    public void sendMail(String address,String text) throws AddressException, MessagingException{
        msg.setText(text);
        transport.sendMessage(msg, new Address[] {new InternetAddress(address)});
        transport.close();
    }


}
