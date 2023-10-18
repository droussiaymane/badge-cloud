package com.vizzibl.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdditionalDetailsDTO {

    private Long id;
    private String name;
    private String url;
    private String description;
    private String createdBy;
    private String tenantId;
}

