package com.vizzibl.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProgramBadgeDTO {

    private Long id;
    private String badgeName;
    private String image;
    private String programName;
    private Long programId;

}
