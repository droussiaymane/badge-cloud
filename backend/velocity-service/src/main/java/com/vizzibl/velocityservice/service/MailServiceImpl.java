package com.vizzibl.velocityservice.service;


import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MailServiceImpl implements MailService{

    @Autowired
    JavaMailSender javaMailSender;

    @Override
    @Async
    public void sendEmail(String emailDestination, String message, String objet, FileSystemResource file) {
        try {
            MimeMessage mymessage = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mymessage, true);
            helper.setFrom("icommunicate@icommunicate.io");
            helper.setTo(emailDestination);
            helper.setSubject(objet);


            helper.setText(message,true);
            helper.addInline("myqrcode", file);



            javaMailSender.send(mymessage);
            log.info("Email sent successfully");
        } catch (Exception e) {
            log.error("Email not sent successfully. Email: "+ emailDestination , e);
            throw new MailSendException("An error occurred while sending the email. Please try again later.", e);
        }



    }


    @Async
    public void sendEmailMethod(SimpleMailMessage email) {
        javaMailSender.send(email);
    }
}
