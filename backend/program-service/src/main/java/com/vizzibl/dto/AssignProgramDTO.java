package com.vizzibl.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssignProgramDTO {

    private Long studentId;
    private Set<Long> currentProgramIds;
    private Set<Long> assignedProgramIds;
}
