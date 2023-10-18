package com.vizzibl.controller;


import com.vizzibl.dto.AddEvidenceDTO;
import com.vizzibl.response.ResponseObject;
import com.vizzibl.service.evidence.EvidenceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/evidences")
public class EvidenceController {

    private final EvidenceService evidenceService;

    public EvidenceController(EvidenceService evidenceService) {
        this.evidenceService = evidenceService;
    }

    @PostMapping
    public ResponseEntity<ResponseObject> addOrUpdateEvidence(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String userId, @ModelAttribute AddEvidenceDTO addEvidenceDTO) throws IOException {
        return ResponseEntity.ok(evidenceService.addOrUpdateEvidence(tenantId, userId, addEvidenceDTO));
    }

    @GetMapping
    public ResponseEntity<ResponseObject> getEvidenceList(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String userId, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer limit) {
        return ResponseEntity.ok(evidenceService.getEvidenceList(tenantId, page, limit));
    }

    @GetMapping("/searchByName")
    public ResponseEntity<ResponseObject> searchByName(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String userId, @RequestParam String name) {
        return ResponseEntity.ok(evidenceService.searchByName(tenantId, name));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteEvidence(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String userId, @PathVariable Long id) {
        return ResponseEntity.ok(evidenceService.deleteEvidence(tenantId, id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> findById(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String userId, @PathVariable Long id) {
        return ResponseEntity.ok(evidenceService.findById(tenantId, id));
    }

    @GetMapping("/assign")
    public ResponseEntity<ResponseObject> getUnmappedEntries(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String userId) {
        return ResponseEntity.ok(evidenceService.getUnmappedEntries(tenantId));
    }
}
