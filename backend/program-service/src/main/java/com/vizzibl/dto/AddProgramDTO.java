package com.vizzibl.dto;

import lombok.Data;

import java.util.List;

@Data
public class AddProgramDTO {


    private String createdById;
    private String tenantId;
    private Long id;
    private String programName;
    private String programDuration;
    private String startDate;
    private String endDate;
    private Long badgeId;
    private List<Long> instructorsId;
    private List<Long> studentsId;
    private Long earningRequirementId;
    private Long standardId;
    private String issuedBy;
    private String description;
    private Long additionalDetails;
    private Long evidence;
    private List<Long> competencies;
    private String badgeName;
    private String earningRequirement;
    private String standard;
    private String additionalDetailName;
    private String evidenceName;

    public AddProgramDTO() {
    }
}
