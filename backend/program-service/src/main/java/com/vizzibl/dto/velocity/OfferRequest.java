package com.vizzibl.dto.velocity;

import lombok.Data;

import java.util.List;

@Data
public class OfferRequest {
    private List<String> type;
    private CredentialSubject credentialSubject;
    private String offerCreationDate;
    private String offerExpirationDate;
    private String offerId;
}