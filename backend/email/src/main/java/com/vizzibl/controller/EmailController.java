package com.vizzibl.controller;

import com.vizzibl.request.dto.BadgeEmailDetailsDto;
import com.vizzibl.request.dto.UserRegistrationDto;
import com.vizzibl.response.dto.ResponseObject;
import com.vizzibl.service.EmailService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailController {

    final EmailService emailService;

    @PostMapping("/email/badge")
    public ResponseEntity<ResponseObject> emailBadgeNotification() {
        return emailService.emailBadgeNotification();
    }

    @PostMapping("/email/registration")
    public ResponseEntity<ResponseObject> sendRegistrationsEmail(@RequestBody List<UserRegistrationDto> user) {
        return emailService.sendRegistrationEmail(user);
    }


}
