package com.vizzibl.controller;

import com.vizzibl.request.dto.BadgeEmailDetailsDto;
import com.vizzibl.response.dto.ResponseObject;
import com.vizzibl.service.EmailScheduleService;
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
public class EmailScheduleController {

    final EmailScheduleService emailSchedulerService;

    @PostMapping("/email/schedule/badge")
    public ResponseEntity<ResponseObject> scheduleEmailBadge(@RequestBody List<BadgeEmailDetailsDto> emailDetails) {
        return emailSchedulerService.scheduleEmailBadge(emailDetails);
    }

}
