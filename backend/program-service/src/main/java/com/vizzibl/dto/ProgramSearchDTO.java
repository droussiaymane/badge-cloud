package com.vizzibl.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProgramSearchDTO {
    private Long programId;
    private String tenantId;
    private String programName;
    private String startDate;
    private String endDate;
}
