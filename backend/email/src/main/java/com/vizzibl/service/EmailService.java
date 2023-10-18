package com.vizzibl.service;

import com.vizzibl.request.dto.UserRegistrationDto;
import com.vizzibl.response.dto.ResponseObject;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface EmailService {

    ResponseEntity<ResponseObject> sendRegistrationEmail(List<UserRegistrationDto> user);

    ResponseEntity<ResponseObject> emailBadgeNotification();
}
