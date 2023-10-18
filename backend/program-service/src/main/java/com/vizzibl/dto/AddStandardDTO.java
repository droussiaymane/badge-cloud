package com.vizzibl.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddStandardDTO {
    private String createdById;
    private String tenantId;
    private Long id;
    private String name;
    private String description;
    private String url;
    private Boolean isActivated;
}
