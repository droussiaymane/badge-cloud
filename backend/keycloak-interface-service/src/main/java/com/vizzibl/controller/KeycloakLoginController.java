package com.vizzibl.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vizzibl.dto.LoginDto;
import com.vizzibl.dto.RefreshTokenDto;
import com.vizzibl.response.ResponseObject;
import com.vizzibl.service.KeycloakLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class KeycloakLoginController {
    @Autowired
    private KeycloakLoginService keycloakLoginService;

    @PostMapping("/login")
    public ResponseObject login(@RequestBody LoginDto loginDto) throws JsonProcessingException {
        return keycloakLoginService.login(loginDto);
    }

    @PostMapping("/refreshToken")
    public ResponseObject refreshToken(@RequestBody RefreshTokenDto refreshTokenDto) throws JsonProcessingException {
        return keycloakLoginService.refreshToken(refreshTokenDto);
    }
}
