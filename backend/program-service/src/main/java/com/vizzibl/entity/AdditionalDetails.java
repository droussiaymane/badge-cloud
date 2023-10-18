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
@NoArgsConstructor
@AllArgsConstructor
public class AdditionalDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String url;
    private String tenantId;
    private String createdBy;
    private String file1;
    private String file2;
    private String file3;
    @JsonIgnore
    @OneToMany(mappedBy = "additionalDetails")
    private List<ProgramsModel> programsModels;

    public AdditionalDetails(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public AdditionalDetails(Long id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }
}
