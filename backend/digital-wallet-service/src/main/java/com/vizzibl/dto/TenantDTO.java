package com.vizzibl.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TenantDTO {
    private Long id;
    private String tenantId;
    private String companyName;
    private LocalDateTime creationDate;
    private String email;
    private String url;
    private String issuerId;
}
