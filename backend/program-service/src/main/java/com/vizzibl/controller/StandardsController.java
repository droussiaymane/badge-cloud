package com.vizzibl.controller;


import com.vizzibl.dto.AddStandardDTO;
import com.vizzibl.response.ResponseObject;
import com.vizzibl.service.standard.StandardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/standards")
public class StandardsController {

    private final StandardService standardService;

    public StandardsController(StandardService standardService) {
        this.standardService = standardService;
    }

    @PostMapping
    public ResponseEntity<ResponseObject> addStandard(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String userId, @RequestBody AddStandardDTO standardDTO) {
        return ResponseEntity.ok(standardService.addOrUpdateStandard(tenantId, userId, standardDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteStandard(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String userId, @PathVariable Long id) {
        return ResponseEntity.ok(standardService.deleteStandard(tenantId, id));
    }

    @GetMapping
    public ResponseEntity<ResponseObject> getsStandardList(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String userId, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer limit) {
        return ResponseEntity.ok(standardService.getStandardList(tenantId, page, limit));
    }


    @GetMapping("/searchByName")
    public ResponseEntity<ResponseObject> searchByName(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String userId, @RequestParam String name) {
        return ResponseEntity.ok(standardService.searchByName(tenantId, name));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> findById(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String userId, @PathVariable Long id) {
        return ResponseEntity.ok(standardService.findById(tenantId, id));
    }


    @GetMapping("/assign")
    public ResponseEntity<ResponseObject> getUnmappedEntries(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String userId) {
        return ResponseEntity.ok(standardService.getUnmappedEntries(tenantId));
    }

}
