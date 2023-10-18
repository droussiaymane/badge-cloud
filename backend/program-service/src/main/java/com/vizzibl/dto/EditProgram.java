package com.vizzibl.dto;

import lombok.Data;

import java.util.List;

@Data
public class EditProgram {

    private String createdById;
    private String tenantId;

    private int id;
    private String programName;
    private String startDate;
    private String endDate;
    private Long badgeId;
    private Long earningRequirementId;
    private Long standardId;

    private List<Long> instructorsId;
    private List<Long> studentsId;
    private Long evidenceId;
    private Long additionalDetailsId;

    private String issuedBy;

    public List<Long> competenciesIds;

}
