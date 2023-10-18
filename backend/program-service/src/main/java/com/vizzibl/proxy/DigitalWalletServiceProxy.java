package com.vizzibl.proxy;

import com.vizzibl.config.FeignClientInterceptor;
import com.vizzibl.response.ResponseObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "digital-wallet-service", url = "localhost:8073", configuration = {FeignClientInterceptor.class})
public interface DigitalWalletServiceProxy {

    @GetMapping("/users/byIds")
    ResponseEntity<ResponseObject> getUserByUserIds(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String userId, @RequestParam List<String> userIds);

    @GetMapping("/users/{userId}")
    ResponseEntity<ResponseObject> findByUserId(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String headerUserId, @PathVariable String userId);

    @GetMapping("/users/searchByEmail")
    ResponseEntity<ResponseObject> findByEmails(@RequestParam String category, @RequestParam List<String> emails);

    @GetMapping("/users/getKUser/{sub}")
    ResponseEntity<ResponseObject> getUserProfileByKid(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String headerUserId, @PathVariable String sub);

    @GetMapping("/users/getKUser/details/{sub}")
    ResponseEntity<ResponseObject> getUserProfileDetailsByKid(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String headerUserId, @PathVariable String sub);
}
