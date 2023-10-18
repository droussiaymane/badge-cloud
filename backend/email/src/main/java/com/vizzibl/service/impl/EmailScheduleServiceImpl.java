package com.vizzibl.service.impl;

import com.vizzibl.repository.EmailDetailsRepository;
import com.vizzibl.entity.EmailDetails;
import com.vizzibl.request.dto.BadgeEmailDetailsDto;
import com.vizzibl.response.dto.ResponseObject;
import com.vizzibl.service.EmailScheduleService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.vizzibl.constants.EmailConstants.*;
import static com.vizzibl.constants.EmailStatusConstants.PENDING;

@Service
@Slf4j
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailScheduleServiceImpl implements EmailScheduleService {

    EmailDetailsRepository emailDetailsRepo;

    @Override
    public ResponseEntity<ResponseObject> scheduleEmailBadge(List<BadgeEmailDetailsDto> emailDetails) {
        for (BadgeEmailDetailsDto emailDto : emailDetails) {
            EmailDetails email = new EmailDetails();
            try {
                email.setEmail(emailDto.getTo());
                String verbiage = BADGE_CONTENT
                        .formatted(emailDto.getFirstName(), emailDto.getLastName(), emailDto.getProgram(), emailDto.getIssuerName(), emailDto.getProgram(),
                                emailDto.getIssuerName(), emailDto.getProgram(),emailDto.getProgram());
                email.setSubject(BADGE_TITLE);
                email.setBody(verbiage);
                email.setStatus(PENDING);
                email.setCreatedDate(new Date());
                email.setCreatedBy(emailDto.getCreatedBy());
                email.setType(TYPE_BADGE);
                emailDetailsRepo.save(email);
            } catch (Exception e) {
                log.error("Error ", e);
            }
        }
        return ResponseEntity.ok(new ResponseObject(200, "success", null));
    }
}
