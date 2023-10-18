package com.vizzibl.controller;


import com.vizzibl.dto.AddBadgeDTO;
import com.vizzibl.response.ResponseObject;
import com.vizzibl.service.badge.BadgeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/badges")
public class BadgesController {

    private final BadgeService badgeService;

    public BadgesController(BadgeService badgeService) {
        this.badgeService = badgeService;
    }

    @PostMapping
    public ResponseEntity<ResponseObject> addOrUpdateBadge(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String userId, @ModelAttribute AddBadgeDTO badgeDTO) throws IOException {
        return ResponseEntity.ok(badgeService.addOrUpdateBadge(tenantId, userId, badgeDTO));
    }

    @GetMapping
    public ResponseEntity<ResponseObject> getBadgesList(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String userId, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer limit) {
        return ResponseEntity.ok(badgeService.getBadgesList(tenantId, page, limit));
    }

    @GetMapping("/searchByBadgeName")
    public ResponseEntity<ResponseObject> getBadgesByBadgeName(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String userId, @RequestParam String badgeName) {
        return ResponseEntity.ok(badgeService.getBadgesByBadgeName(tenantId, badgeName));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteBadge(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String userId, @PathVariable Long id) {
        return ResponseEntity.ok(badgeService.deleteBadge(tenantId, id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> findById(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String userId, @PathVariable Long id) {
        return ResponseEntity.ok(badgeService.findById(tenantId, id));
    }

    @GetMapping("/assign")
    public ResponseEntity<ResponseObject> getUnmappedEntries(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String userId) {
        return ResponseEntity.ok(badgeService.getUnmappedEntries(tenantId));
    }
}
