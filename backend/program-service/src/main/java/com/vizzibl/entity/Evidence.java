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
public class Evidence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String link;
    private String description;
    private String tenantId;

    @OneToMany(mappedBy = "evidence")
    private List<EvidenceFile> evidenceFileList;

    @JsonIgnore
    @OneToMany(mappedBy = "evidence", cascade = CascadeType.REFRESH)
    private List<ProgramsModel> programsModels;

    private String evidence1;
    private String evidence2;
    private String evidence3;
    private String createdBy;


    public Evidence(String name, String link, String description) {
        this.name = name;
        this.link = link;
        this.description = description;
    }

    public Evidence(Long id, String name, String link, String description) {
        this.name = name;
        this.link = link;
        this.description = description;
    }

    @Transient
    public String getPhotosImagePathEvidence1() {
        if (evidence1 != null) {
            return "/images/evidence/" + id + "/" + evidence1;
        } else {
            return null;
        }

    }

    @Transient
    public String getPhotosImagePathEvidence2() {
        if (evidence2 != null) {
            return "/images/evidence/" + id + "/" + evidence2;
        } else {
            return null;
        }

    }

    @Transient
    public String getPhotosImagePathEvidence3() {
        if (evidence3 != null) {
            return "/images/evidence/" + id + "/" + evidence3;
        } else {
            return null;
        }

    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

  /*public EvidenceWIthTypeDTO getEvidenceWitType() {
    return new EvidenceWIthTypeDTO(this);
  }*/
}
