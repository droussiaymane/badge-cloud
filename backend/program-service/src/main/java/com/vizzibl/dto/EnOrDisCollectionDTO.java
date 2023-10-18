package com.vizzibl.dto;

import lombok.Data;

@Data
public class EnOrDisCollectionDTO {
    private Long programId;
    private Long studentId;
    private String programName;
    private String tenantId;
    private Boolean isEnableCollection;
    private Boolean isBulk;
    private String walletType;
}
