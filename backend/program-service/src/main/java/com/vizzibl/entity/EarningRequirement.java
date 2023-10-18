package com.vizzibl.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EarningRequirement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String createdBy;
    private String tenantId;

    @JsonIgnore
    @OneToMany(mappedBy = "earningRequirement")
    private List<ProgramsModel> programsModel;

    public EarningRequirement(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public EarningRequirement(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public void setCreatedBy(String createdBy) {

        this.createdBy = createdBy;
    }
}
