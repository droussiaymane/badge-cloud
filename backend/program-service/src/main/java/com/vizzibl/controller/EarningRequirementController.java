package com.vizzibl.controller;


import com.vizzibl.dto.EarningRequirementDTO;
import com.vizzibl.response.ResponseObject;
import com.vizzibl.service.earningRequirement.EarningRequirementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/earningRequirements")
public class EarningRequirementController {
    private final EarningRequirementService earningRequirementService;

    public EarningRequirementController(EarningRequirementService earningRequirementService) {
        this.earningRequirementService = earningRequirementService;
    }

    @PostMapping
    public ResponseEntity<ResponseObject> addOrUpdateEarningRequirement(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String userId, @RequestBody EarningRequirementDTO earningRequirementDTO) {
        return ResponseEntity.ok(earningRequirementService.addOrUpdateEarningRequirement(tenantId, userId, earningRequirementDTO));
    }

    @GetMapping
    public ResponseEntity<ResponseObject> getEarningRequirementList(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String userId, @RequestParam(defaultValue = "0") Integer page, @RequestHeader(defaultValue = "10") Integer limit) {
        return ResponseEntity.ok(earningRequirementService.getEarningRequirementList(tenantId, page, limit));
    }

    @GetMapping("/searchByName")
    public ResponseEntity<ResponseObject> searchByName(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String userId, @RequestParam String name) {
        return ResponseEntity.ok(earningRequirementService.searchByName(tenantId, name));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteEarningRequirement(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String userId, @PathVariable Long id) {

        return ResponseEntity.ok(earningRequirementService.deleteEarningRequirement(tenantId, id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> findById(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String userId, @PathVariable Long id) {

        return ResponseEntity.ok(earningRequirementService.findById(tenantId, id));
    }


    @GetMapping("/assign")
    public ResponseEntity<ResponseObject> getUnmappedEntries(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String userId) {

        return ResponseEntity.ok(earningRequirementService.getUnmappedEntries(tenantId));
    }
}
