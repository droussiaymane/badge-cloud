package com.vizzibl.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentProgramDTO {

    private Long studentId;
    private List<ProgramDTO> currentPrograms;
    private List<ProgramDTO> availablePrograms;

}
