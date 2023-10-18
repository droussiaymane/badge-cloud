package com.vizzibl.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class ProgramsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String programName;
    private String startDate;
    private String endDate;
    private String tenantId;
    private String createdById;

    @OneToMany(mappedBy = "program", cascade = CascadeType.REMOVE)
    private List<StudentProgram> studentProgram;

    @OneToMany(mappedBy = "program", cascade = CascadeType.REMOVE)
    private List<InstructorProgram> instructorProgram;


    @OneToOne(mappedBy = "programsModel", cascade = CascadeType.REMOVE)
    private ProgramBadge programBadge;

    @OneToMany(mappedBy = "programsModel", cascade = CascadeType.REMOVE)
    private List<ProgramCompetency> programCompetencies;

    @ManyToOne
    @JoinColumn(name = "earning_requirement_id")
    private EarningRequirement earningRequirement;

    @ManyToOne
    @JoinColumn(name = "standard_id")
    private Standard standard;

    @ManyToOne
    @JoinColumn(name = "additional_details_id")
    private AdditionalDetails additionalDetails;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "evidence_id")
    private Evidence evidence;

    private String description;
    private String issuedBy;

    public ProgramsModel(Long programId) {

        this.id = programId;
    }

    public ProgramsModel() {

    }
}
