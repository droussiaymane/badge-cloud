package com.vizzibl.dto;

import lombok.Data;

@Data
public class StudentOpenBadgeViewDTO {
    private Long badgeId;
    private Long studentId;
    private String name;
    private Long programId;
    private String photo;
    private String description;
    private String issuerName;
    private String issuedOn;
    private Boolean expirationStatus;
    private String expirationDate;
    private Boolean revokedStatus;
    private String revokedReason;

}
