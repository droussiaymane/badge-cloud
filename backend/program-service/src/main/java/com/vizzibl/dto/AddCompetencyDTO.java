package com.vizzibl.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddCompetencyDTO {

    private String createdById;
    private String tenantId;
    private Long id;
    private String name;
    private String description;
    private String url;
    private Boolean isActivated;
}
