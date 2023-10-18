package com.vizzibl.proxy;

import com.vizzibl.config.FeignClientInterceptor;
import com.vizzibl.dto.BadgeEmailDetailsDto;
import com.vizzibl.response.ResponseObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "email-service", url = "localhost:8066", configuration = {FeignClientInterceptor.class})
public interface EmailServiceProxy {

    @PostMapping("/email/schedule/badge")
    ResponseEntity<ResponseObject> scheduleEmailBadge(@RequestBody List<BadgeEmailDetailsDto> emailDetails);
}
