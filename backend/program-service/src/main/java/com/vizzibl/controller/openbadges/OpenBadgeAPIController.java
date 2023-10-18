package com.vizzibl.controller.openbadges;


import com.vizzibl.service.openbadges.BadgeBakingService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class OpenBadgeAPIController {

    private final BadgeBakingService badgeService;

    public OpenBadgeAPIController(BadgeBakingService badgeService) {
        this.badgeService = badgeService;
    }

    @GetMapping(value = "/api/v1/obv2/assertion/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<String> assertion(@PathVariable("id") String assertionKey) throws IOException {
        return badgeService.getAssertion(assertionKey);
    }

    @GetMapping(value = "/api/v1/obv2/badge/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<String> badge(@PathVariable("id") String badgeKey) throws IOException {
        return ResponseEntity.ok(badgeService.getBadge(badgeKey));
    }

    @GetMapping(value = "/api/v1/obv2/issuer/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<String> issuer(@PathVariable("id") String issuerKey) throws IOException {
        return ResponseEntity.ok(badgeService.getIssuer(issuerKey));
    }

}
