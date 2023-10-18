package com.vizzibl.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddUserDTO {

    private Long id;
    private String firstName;
    private String tenantId;
    private String lastName;
    private String companyName;
    private String userName;
    private String password;
    private String userId;
    private String categoryName;
    private String email;
    private String kID;
    private MultipartFile photo;

    public AddUserDTO() {
    }


}
