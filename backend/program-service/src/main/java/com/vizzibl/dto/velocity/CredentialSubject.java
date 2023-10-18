package com.vizzibl.dto.velocity;

import lombok.Data;

@Data
public class CredentialSubject {
    private String vendorUserId;
    private Credential hasCredential;
}