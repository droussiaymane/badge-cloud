package com.vizzibl.dto;

import lombok.Data;


@Data
public class AddTenantDTO {

    private String tenantId;
    private String companyName;
    private String email;
    private String url;
    private String issuerId;
}
