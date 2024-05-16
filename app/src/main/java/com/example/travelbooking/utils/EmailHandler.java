package com.example.travelbooking.utils;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailHandler {

    public static final String Sender_Email_Address = "codefestfinal@gmail.com";
    public static final String Sender_Email_Password = "rikmwnwlanhyinxk";
    public static final String Gmail_Host = "smtp.gmail.com";

    public void sendEmail(String subject, String content, String to_email){

        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", Gmail_Host);
        properties.put("mail.smtp,port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Sender_Email_Address,Sender_Email_Password);
            }
        });
        MimeMessage message = new MimeMessage(session);
        try{
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to_email));
            message.setSubject(subject);
            message.setText(content);
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        Transport.send(message);
                    }catch (MessagingException e){
                        e.printStackTrace();

                    }
                }
            });
            thread.start();
        }catch(MessagingException e){
            throw  new RuntimeException(e);
        }
    }

}
