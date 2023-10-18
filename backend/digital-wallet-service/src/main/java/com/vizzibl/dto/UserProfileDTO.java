package com.vizzibl.dto;

import lombok.Data;

@Data
public class UserProfileDTO {

    private Long id;
    private String firstName;
    private String tenantId;
    private String lastName;
    private String email;
    private String kID;
    private String UserId;
    private String photo;
    private String mobileNumber;
    private String bio;
    private String currentEmployer;
    private String currentPosition;
    private String badgeCloudURL;
    private String websiteURL;
    private String birthYear;
    private String country;
    private String state;
    private String city;
    private String zipCode;

    public UserProfileDTO() {
    }

}
