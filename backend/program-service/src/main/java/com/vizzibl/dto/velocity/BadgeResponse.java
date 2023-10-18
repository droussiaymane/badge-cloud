package com.vizzibl.dto.velocity;

import lombok.Data;

import java.util.List;

@Data
public class BadgeResponse {
    private List<String> type;
    private CredentialSubject credentialSubject;
    private String offerCreationDate;
    private String offerExpirationDate;
    private String offerId;
    private String exchangeId;
    private String id;
    private String createdAt;
    private String updatedAt;
    private Issuer issuer;

    @Data
    public static class Issuer {
        private String id;
    }
}
