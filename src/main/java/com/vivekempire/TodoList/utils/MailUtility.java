package com.vivekempire.TodoList.utils;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@Component
public class MailUtility {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine engine;

    @Async
    public void sendNormalMail(String to,String subject,String body){
        SimpleMailMessage message=new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom("csgptmail@gmail.com");
        javaMailSender.send(message);
    }

    @Async
    public void sendHtmlMail(String to,String subject,String html) throws MessagingException {
        MimeMessage mimeMessage =javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,true);
        mimeMessageHelper.setFrom("csgptmail@gmail.com");
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(html,true);
        javaMailSender.send(mimeMessage);

    }

    @Async
    public void sendTemplateMail(String to, String subject, Map<String,Object> data) throws Exception{
        Context context=new Context();
        context.setVariables(data);
        String htmlContent=engine.process("register_welcome",context);
        MimeMessage message=javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper=new MimeMessageHelper(message,true);
        messageHelper.setText(htmlContent,true);
        messageHelper.setTo(to);
        messageHelper.setSubject(subject);
        messageHelper.setFrom("csgptmail@gmail.com");
        javaMailSender.send(message);



    }
}
