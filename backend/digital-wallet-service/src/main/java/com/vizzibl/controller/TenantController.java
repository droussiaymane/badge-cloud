package com.vizzibl.controller;

import com.vizzibl.dto.AddTenantDTO;
import com.vizzibl.response.ResponseObject;
import com.vizzibl.service.tenant.TenantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tenant")
public class TenantController {

    private final TenantService tenantService;

    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @PostMapping
    private ResponseEntity<ResponseObject> addTenant(@RequestBody AddTenantDTO addTenantDTO) {
        return ResponseEntity.ok(tenantService.addTenant(addTenantDTO));
    }

    @GetMapping("/{tenantId}")
    private ResponseEntity<ResponseObject> getTenant(@PathVariable("tenantId") String tenantId) {
        return ResponseEntity.ok(tenantService.getTenant(tenantId));
    }
}
