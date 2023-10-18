package com.vizzibl.service.impl;

import com.vizzibl.constants.EmailConstants;
import com.vizzibl.constants.EmailStatusConstants;
import com.vizzibl.entity.EmailDetails;
import com.vizzibl.repository.EmailDetailsRepository;
import com.vizzibl.request.dto.UserRegistrationDto;
import com.vizzibl.response.dto.RegistrationEmailStatusDto;
import com.vizzibl.response.dto.ResponseObject;
import com.vizzibl.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.vizzibl.constants.EmailStatusConstants.SENT;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    JavaMailSender emailSender;

    @Autowired
    EmailDetailsRepository emailDetailsRepo;

    @Override
    public ResponseEntity<ResponseObject> sendRegistrationEmail(List<UserRegistrationDto> users) {
        List<RegistrationEmailStatusDto> emailStatusList = new ArrayList<>();
        for (UserRegistrationDto user : users) {
            RegistrationEmailStatusDto emailStatus = new RegistrationEmailStatusDto();
            try {
                emailStatus.setTo(user.getTo());
                emailStatus.setUserName(user.getUserName());
                String verbiage = EmailConstants.REGISTRATION_CONTENT
                        .formatted(user.getFirstName(), user.getLastName(), user.getUserName(), user.getPassword());

                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom(EmailConstants.FROM);
                message.setTo(user.getTo());
                message.setSubject(EmailConstants.REGISTRATION_TITLE);
                message.setText(verbiage);
                emailSender.send(message);
                emailStatus.setSent(true);
                emailStatusList.add(emailStatus);
            } catch (Exception e) {
                emailStatus.setSent(false);
                emailStatusList.add(emailStatus);
            }
        }
        return ResponseEntity.ok(new ResponseObject(200, "success", emailStatusList));
    }

    @Override
    public ResponseEntity<ResponseObject> emailBadgeNotification() {
        List<EmailDetails> emails = emailDetailsRepo.findByStatusAndType(EmailStatusConstants.PENDING, EmailConstants.TYPE_BADGE);
        for (EmailDetails email : emails) {
            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom(EmailConstants.FROM);
                message.setTo(email.getEmail());
                message.setSubject(email.getSubject());
                message.setText(email.getBody());
                emailSender.send(message);
                email.setModifiedBy("SCHEDULER");
                email.setStatus(SENT);
                email.setModifiedDate(new Date());
                emailDetailsRepo.save(email);
            } catch (Exception e) {
                log.error("Error ", e);
            }
        }
        return ResponseEntity.ok(new ResponseObject(200, "success", null));
    }
}
