package com.vizzibl.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EvidenceDTO {

    private String createdById;
    private String tenantId;

    private Long id;
    private String name;
    private String file1;
    private String file2;
    private String file3;
    private String description;
    private String link;

}
