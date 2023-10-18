package com.vizzibl.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EarningRequirementDTO {
    private Long id;
    private String createdById;
    private String tenantId;
    private String name;
    private String description;
}
