package com.vizzibl.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdateUserProfileDTO {

    private Long id;
    private String firstName;
    private String tenantId;
    private String lastName;
    private String email;
    private String kID;
    private String UserId;
    private MultipartFile photo;
    private String mobileNumber;
    private String bio;
    private String currentEmployer;
    private String badgeCloudURL;
    private String birthYear;
    private String country;
    private String state;
    private String city;
    private String zipCode;

    public UpdateUserProfileDTO() {
    }

}
