package com.vizzibl.controller;

import com.vizzibl.dto.UserBadgeViewUrlDTO;
import com.vizzibl.response.ResponseObject;
import com.vizzibl.service.publicview.PublicViewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicViewController {

    private final PublicViewService publicViewService;

    public PublicViewController(PublicViewService publicViewService) {
        this.publicViewService = publicViewService;
    }

    @GetMapping
    public ResponseEntity<ResponseObject> getUserBadgeView(@RequestParam String publicViewId) {
        return ResponseEntity.ok(publicViewService.getUserBadgeView(publicViewId));
    }

    @PostMapping("/userBadgeViewUrl")
    public ResponseEntity<ResponseObject> getUserBadgeViewUrl(@RequestHeader(required = false) String tenantId, @RequestHeader(required = false) String headerUserId, @RequestBody(required = false) UserBadgeViewUrlDTO userBadgeViewUrlDTO) {
        return ResponseEntity.ok(publicViewService.getUserBadgeViewUrl(tenantId, headerUserId, userBadgeViewUrlDTO));
    }
}
