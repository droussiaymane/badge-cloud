package com.vizzibl.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
public class UserProfile implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mobileNumber;
    private String bio;
    private String currentEmployer;
    private String badgeCloudURL;
    private String birthYear;
    private String country;
    private String state;
    private String city;
    private String zipCode;

    @JsonIgnore
    @OneToOne(mappedBy = "userProfile")
    private UsersModel usersModel;

}
