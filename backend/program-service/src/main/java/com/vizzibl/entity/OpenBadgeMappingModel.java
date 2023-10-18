package com.vizzibl.entity;


import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class OpenBadgeMappingModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tenantId;
    private Long studentId;
    private Long programId;
    private Long badgeId;
    private String fileName;
    private String iframe;
    @OneToOne(mappedBy = "openBadgeMappingModel")
    private AssertionModel assertionModel;

}
