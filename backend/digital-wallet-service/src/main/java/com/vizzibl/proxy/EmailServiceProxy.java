package com.vizzibl.proxy;

import com.vizzibl.dto.UserRegistrationDto;
import com.vizzibl.response.ResponseObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "email-service")
public interface EmailServiceProxy {

    @PostMapping("email/registration")
    ResponseEntity<ResponseObject> sendRegistrationsEmail(@RequestBody List<UserRegistrationDto> user);
}
