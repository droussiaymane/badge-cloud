package com.vizzibl.proxy;

import com.vizzibl.config.FeignClientInterceptor;
import com.vizzibl.entity.CreateUserDto;
import com.vizzibl.response.KeycloakResponseObject;
import com.vizzibl.response.ResponseObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "keycloak-interface-service", url = "http://localhost:8090/admin", configuration = {FeignClientInterceptor.class})
public interface KeycloakServiceProxy {
    @PostMapping("/users")
    ResponseEntity<KeycloakResponseObject> addUser(@RequestBody CreateUserDto createUserDto);

    @PutMapping("/updateUser/{userId}")
    ResponseEntity<ResponseObject> updateUser(@RequestBody CreateUserDto createUserDto, @PathVariable("userId") String userId);
}