package com.vizzibl.dto;


import lombok.Data;

@Data
public class ExpireRevokeOpenBadgeDTO {

    private Long id;
    private String tenantId;
    private Long studentId;
    private Long programId;
    private Boolean revokeStatus;
    private String revokeReason;
    private Boolean expiresStatus;
    private String expirationDate;

}
