/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.capanegocio;


import java.io.File;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author Diegogo
 */

@Service
public class CorreoService {
    
    @Value("${mail.smtp.host}")
    private String host;
    
    @Value("${mail.smtp.port}")
    private String port;
    
    @Value("${mail.smtp.username}")
    private String username;
    
    @Value("${mail.smtp.password}")
    private String password;
    

    public void sendCorreo(String to, String subject, String content, File file) throws MessagingException{
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.ssl.trust", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.starttls.required", "true");
        
        Session session = Session.getInstance(props,
            new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
        
        Message message =new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setText(content);
        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setText(content);
        
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(textPart);
        
        if(file !=null && file.exists()){
            MimeBodyPart attachmentPart = new MimeBodyPart();
            DataSource source = new FileDataSource(file);
            attachmentPart.setDataHandler(new DataHandler(source));
            attachmentPart.setFileName(file.getName());
            multipart.addBodyPart(attachmentPart);
        }
        
        message.setContent(multipart);
        Transport.send(message);
        
        
    }
    
}
