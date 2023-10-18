package com.vizzibl.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class UsersModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String password;
    private String firstName;
    private String kID;
    private String tenantId;
    private String lastName;
    @Column(unique = true)
    private String userId;
    private String email;
    @Column(nullable = true)
    private String photo;

    @JsonBackReference
    @OneToMany(mappedBy = "student")
    private List<StudentProgram> studentProgram;

    @JsonBackReference
    @OneToMany(mappedBy = "instructor")
    private List<InstructorProgram> instructorProgram;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "user_category_model_id", nullable = false)
    private UserCategoryModel userCategoryModel;

    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_profile_id", referencedColumnName = "id")
    private UserProfile userProfile;

    public UsersModel() {
    }

}
