package com.vizzibl.controller;


import com.vizzibl.dto.AdditionalDetailsDTO;
import com.vizzibl.response.ResponseObject;
import com.vizzibl.service.additionalDetails.AdditionalDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/AdditionalDetails")
public class AdditionalDetailsController {

    private final AdditionalDetailsService additionalDetailsService;

    public AdditionalDetailsController(AdditionalDetailsService additionalDetailsService) {
        this.additionalDetailsService = additionalDetailsService;
    }

    @PostMapping
    public ResponseEntity<ResponseObject> addOrUpdateEAdditionalDetails(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String userId, @RequestBody AdditionalDetailsDTO additionalDetailsDTO) throws IOException {

        return ResponseEntity.ok(additionalDetailsService.addOrUpdateAdditionalDetails(tenantId, userId, additionalDetailsDTO));
    }

    @GetMapping
    public ResponseEntity<ResponseObject> getAdditionalDetailsList(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String userId, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer limit) {

        return ResponseEntity.ok(additionalDetailsService.getAdditionalDetailsList(tenantId, page, limit));
    }

    @GetMapping("/searchByName")
    public ResponseEntity<ResponseObject> searchByName(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String userId, @RequestParam String name) {

        return ResponseEntity.ok(additionalDetailsService.searchByName(tenantId, name));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteAdditionalDetails(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String userId, @PathVariable Long id) {

        return ResponseEntity.ok(additionalDetailsService.deleteAdditionalDetails(tenantId, id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> findById(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String userId, @PathVariable Long id) {

        return ResponseEntity.ok(additionalDetailsService.findById(tenantId, id));
    }

    @GetMapping("/assign")
    public ResponseEntity<ResponseObject> findUnMappedEntries(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String userId) {

        return ResponseEntity.ok(additionalDetailsService.findUnmappedEntries(tenantId));
    }
}
