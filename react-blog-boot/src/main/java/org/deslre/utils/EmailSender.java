package org.deslre.utils;

import lombok.extern.slf4j.Slf4j;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

/**
 * ClassName: EmailSender
 * Description: TODO
 * Author: Deslrey
 * Date: 2025-06-08 20:20
 * Version: 1.0
 */
@Slf4j
public class EmailSender {
    public static void sendEmail(String toEmail, String verificationCode) {
        // 邮件服务器配置信息
        String host = "smtp.qq.com";
        final String username = "2135232170@qq.com"; // QQ邮箱
        final String password = "ymkykemycytybaed"; // QQ邮箱密码或应用专用密码

        // 配置邮件属性
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        // 获取邮件会话对象
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // 创建邮件消息
            MimeMessage message = new MimeMessage(session);

            // 设置发件人
            message.setFrom(new InternetAddress(username));

            // 设置收件人
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));

            // 设置邮件主题
            message.setSubject("注册验证码");

            // 设置邮件内容为HTML格式
            String htmlContent = "<html>" +
                    "<body>" +
                    "<h2>欢迎注册！</h2>" +
                    "<p>您的验证码是：<b style='color:blue;'>" + verificationCode + "</b></p>" +
                    "<p>请在10分钟内使用此验证码完成注册。</p>" +
                    "<p>如果您未申请此验证码，请忽略此邮件。</p>" +
                    "<hr>" +
                    "<p>此致，<br>注册团队</p>" +
                    "</body>" +
                    "</html>";
            message.setContent(htmlContent, "text/html;charset=UTF-8");

            // 发送邮件
            Transport.send(message);

            System.out.println("邮件发送成功到: " + toEmail);

        } catch (MessagingException e) {
            log.error("邮件发送失败: {}", e.getMessage());
        }
    }

    public static void main(String[] args) {
        // 测试发送邮件
        sendEmail("2351825855@qq.com", "2345");
    }
}

