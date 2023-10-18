package com.vizzibl.scheduler.proxy;

import com.vizzibl.scheduler.dto.ResponseObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "email-service")
public interface EmailServiceProxy {

    @PostMapping("/email/badge")
    ResponseEntity<ResponseObject> emailBadgeNotification();
}
