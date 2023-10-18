package com.vizzibl.controller;

import com.vizzibl.proxy.DigitalWalletServiceProxy;
import com.vizzibl.response.ResponseObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/test")
public class TestController {


    private final DigitalWalletServiceProxy digitalWalletServiceProxy;

    public TestController(DigitalWalletServiceProxy digitalWalletServiceProxy) {
        this.digitalWalletServiceProxy = digitalWalletServiceProxy;
    }

    @GetMapping("/anonymous")
    public ResponseEntity<String> getAnonymous() {
        return ResponseEntity.ok("Hello Anonymous");
    }

    @GetMapping("/admin/{id}")
    public ResponseEntity<String> getAdmin(@RequestHeader(required = false) String tenantId, @PathVariable String id) {
//        JwtAuthenticationToken token = (JwtAuthenticationToken) principal;
//        String userName = (String) token.getTokenAttributes().get("name");
//        String userEmail = (String) token.getTokenAttributes().get("email");
        ResponseEntity<ResponseObject> userId = digitalWalletServiceProxy.findByUserId(null, null, id);


        return ResponseEntity.ok("Hello Admin tenantId : " + tenantId + " : " + userId.getBody().getData().toString());
    }

    @GetMapping("/user")
    public ResponseEntity<String> getUser(Principal principal) {
        JwtAuthenticationToken token = (JwtAuthenticationToken) principal;
        String userName = (String) token.getTokenAttributes().get("name");
        String userEmail = (String) token.getTokenAttributes().get("email");
        return ResponseEntity.ok("Hello User \nUser Name : " + userName + "\nUser Email : " + userEmail);
    }

}