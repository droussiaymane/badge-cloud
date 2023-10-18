package com.vizzibl.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddEvidenceDTO {

    private Long id;
    private String name;
    private String description;
    private String link;
    private String createdById;
    private String tenantId;
    private MultipartFile file1;
    private MultipartFile file2;
    private MultipartFile file3;

}
