package com.vizzibl.service;

import com.vizzibl.request.dto.BadgeEmailDetailsDto;
import com.vizzibl.response.dto.ResponseObject;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EmailScheduleService {
    ResponseEntity<ResponseObject> scheduleEmailBadge(List<BadgeEmailDetailsDto> emailDetails);
}
