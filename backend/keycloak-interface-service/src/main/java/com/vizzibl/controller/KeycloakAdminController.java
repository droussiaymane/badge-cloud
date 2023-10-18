package com.vizzibl.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vizzibl.dto.CreateUserDto;
import com.vizzibl.response.KeycloakResponseObject;
import com.vizzibl.response.ResponseObject;
import com.vizzibl.service.KeycloakAdminService;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class KeycloakAdminController {

    @Autowired
    private KeycloakAdminService keycloakAdminService;

    @PostMapping("/users")
    public KeycloakResponseObject createUser(@RequestBody CreateUserDto createUserDto) throws JsonProcessingException {
        return keycloakAdminService.createUser(createUserDto);
    }

    @PutMapping(path = "/updateUser/{userId}")
    public ResponseObject updateUser(@RequestBody CreateUserDto createUserDto, @PathVariable("userId") String userId)
            throws JsonProcessingException {
        return keycloakAdminService.updateUser(createUserDto, userId);
    }

    @GetMapping("/getAllUsers")
    public Map<String, List<String>> getAllusers() throws JsonProcessingException {
        Map<String, List<String>> data = new HashMap<String, List<String>>();
        data.put("car", Arrays.asList("toyota", "bmw", "honda"));
        data.put("fruit", Arrays.asList("apple", "banana"));
        data.put("computer", Arrays.asList("acer", "asus", "ibm"));
        return data;
    }

    @GetMapping("/getAllRealmRoles")
    public List<String> getAllRealmRoles() {
        return keycloakAdminService.getAllRoles();
    }

    @GetMapping("/getUserData")
    @ResponseBody
    public UserRepresentation getUserData(@RequestParam String name) {
        return keycloakAdminService.getUserData(name);
    }

    @PostMapping("/files")
    public String createBulkUser(@RequestParam MultipartFile file) throws Exception {
        keycloakAdminService.bulkCreateUsers(file);
        return "Bulk users creation is done!";
    }
}
