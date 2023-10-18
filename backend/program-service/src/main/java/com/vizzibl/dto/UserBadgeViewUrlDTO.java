package com.vizzibl.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class UserBadgeViewUrlDTO {
    private String UserBadgeViewUrl;
    private String tenantId;
    private Long programId;
    //    private String badgeId;
    private Long studentId;
}
