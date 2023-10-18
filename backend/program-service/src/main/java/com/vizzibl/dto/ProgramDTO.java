package com.vizzibl.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProgramDTO {

    private String createdById;
    private String tenantId;
    private String description;
    private String issuedBy;

    private Long id;
    private String programName;
    private String startDate;
    private String endDate;

    private BadgeDTO badgeDTO;
    private EvidenceDTO evidence;
    private StandardDTO standard;
    private AdditionalDetailsDTO additionalDetails;
    private EarningRequirementDTO earningRequirements;
    List<InstructorDTO> instructors;
    List<StudentDTO> students;
    List<CompetencyDTO> competencies;

}
