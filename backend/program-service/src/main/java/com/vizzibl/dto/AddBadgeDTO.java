package com.vizzibl.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddBadgeDTO {

    private Long id;
    private String badgeName;
    private String createdById;
    private MultipartFile file;

    public AddBadgeDTO() {
    }

}
