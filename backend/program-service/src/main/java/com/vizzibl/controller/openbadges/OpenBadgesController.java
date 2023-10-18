package com.vizzibl.controller.openbadges;

import com.vizzibl.dto.ExpireRevokeOpenBadgeDTO;
import com.vizzibl.response.ResponseObject;
import com.vizzibl.service.openbadges.OpenBadgesService;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/openBadges")
public class OpenBadgesController {

    private final OpenBadgesService openBadgesService;


    public OpenBadgesController(OpenBadgesService openBadgesService) {
        this.openBadgesService = openBadgesService;

    }

    @PostMapping("/updateExpirationRevoke")
    public ResponseEntity<ResponseObject> updateRevokeStatus(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String userId, @RequestBody ExpireRevokeOpenBadgeDTO expireRevokeOpenBadgeDTO) {
        return ResponseEntity.ok(openBadgesService.updateExpirationRevokeOpenBadge(tenantId, expireRevokeOpenBadgeDTO));
    }

    @GetMapping("/studentOpenBadgeView")
    public ResponseEntity<ResponseObject> studentOpenBadgeView(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String userId, @RequestParam Long studentId, @RequestParam Long programId) {
        return ResponseEntity.ok(openBadgesService.studentOpenBadgeView(tenantId, studentId, programId));
    }

    @GetMapping("/downloadStudentOpenBadge")
    public ResponseEntity<Resource> downloadStudentOpenBadge(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String userId, @RequestParam Long studentId, @RequestParam Long programId) throws IOException {
        return openBadgesService.createCertificatedBadgeV2(tenantId, studentId, programId);
    }
}
