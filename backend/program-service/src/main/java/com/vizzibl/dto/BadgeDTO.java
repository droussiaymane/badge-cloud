package com.vizzibl.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BadgeDTO {

    private String createdById;
    private String tenantId;
    private Long id;
    private String badgeName;
    private String image;
}
