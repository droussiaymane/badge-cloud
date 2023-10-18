package com.vizzibl.scheduler.proxy;

import com.vizzibl.scheduler.dto.ResponseObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "digital-wallet-service")
public interface DigitalWalletServiceProxy {

    @PostMapping("/users/trigger/registration")
    ResponseEntity<ResponseObject> scheduler();

}
