package com.vizzibl.service.openbadges;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.vizzibl.entity.OpenBadgeMappingModel;
import com.vizzibl.entity.assertion.AssertionBakingDTO;
import org.springframework.http.ResponseEntity;

public interface BadgeBakingService {
    ResponseEntity<String> getAssertion(String assertionKey) throws JsonProcessingException;

    String getBadge(String badgeKey) throws JsonProcessingException;

    AssertionBakingDTO createAssertion(OpenBadgeMappingModel badgeMappingModel);

    String getIssuer(String issuerKey) throws JsonProcessingException;
}
