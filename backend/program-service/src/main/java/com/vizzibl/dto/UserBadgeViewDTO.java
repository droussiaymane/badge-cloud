package com.vizzibl.dto;

import lombok.Data;

import java.util.List;


@Data
public class UserBadgeViewDTO {

    private Long id;
    private String publicViewId;
    private String programName;
    private String startDate;
    private String endDate;
    private String tenantId;
    private String createdById;
    private StudentDTO studentDTO;
    private List<InstructorDTO> instructorDTOS;
    private BadgeDTO badgeDTO;
    private List<CompetencyDTO> competencyDTOS;
    private EvidenceDTO evidenceDTO;
    private EarningRequirementDTO earningRequirementDTO;
    private StandardDTO standardDTO;
    private AdditionalDetailsDTO additionalDetailsDTO;
    private String description;
    private String issuedBy;

}
